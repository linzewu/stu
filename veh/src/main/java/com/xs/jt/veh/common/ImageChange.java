package com.xs.jt.veh.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

public class ImageChange {

	public static byte[] rotateImg(BufferedImage image, int degree, Color bgcolor) throws IOException {

		int iw = image.getWidth();// 原始图象的宽度
		int ih = image.getHeight();// 原始图象的高度
		int w = 0;
		int h = 0;
		int x = 0;
		int y = 0;
		degree = degree % 360;
		if (degree < 0)
			degree = 360 + degree;// 将角度转换到0-360度之间
		double ang = Math.toRadians(degree);// 将角度转为弧度

		/**
		 * 确定旋转后的图象的高度和宽度
		 */

		if (degree == 180 || degree == 0 || degree == 360) {
			w = iw;
			h = ih;
		} else if (degree == 90 || degree == 270) {
			w = ih;
			h = iw;
		} else {
			int d = iw + ih;
			w = (int) (d * Math.abs(Math.cos(ang)));
			h = (int) (d * Math.abs(Math.sin(ang)));
		}

		x = (w / 2) - (iw / 2);// 确定原点坐标
		y = (h / 2) - (ih / 2);
		BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
		if (bgcolor == null) {
			rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		} else {
			gs.setColor(bgcolor);
			gs.fillRect(0, 0, w, h);// 以给定颜色绘制旋转后图片的背景
		}

		AffineTransform at = new AffineTransform();
		at.rotate(ang, w / 2, h / 2);// 旋转图象
		at.translate(x, y);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		op.filter(image, rotatedImage);
		image = rotatedImage;

		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ImageOutputStream iamgeOut = ImageIO.createImageOutputStream(byteOut);

		ImageIO.write(image, "png", iamgeOut);
		return byteOut.toByteArray();

	}

	public static BufferedImage Rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// calculate the new image size
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = res.createGraphics();
		// transform
		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

		g2.drawImage(src, null, null);
		return res;
	}

	public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversion
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

//	public static void main(String[] args) throws IOException {
//		BufferedImage src = ImageIO.read(new File("d:/2016121016488817.jpg"));
//		BufferedImage des = ImageChange.Rotate(src, 90);
//		ImageIO.write(des, "jpg", new File("d:/222.jpg"));
//	}
}
