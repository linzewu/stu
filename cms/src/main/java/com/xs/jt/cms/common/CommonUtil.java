package com.xs.jt.cms.common;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xs.jt.cms.entity.PreCarRegister;

import net.sf.json.JSONObject;

public class CommonUtil {

	public static String convertCode(String key, String value,HttpServletRequest request) {
		Map codeMap = (Map) request.getSession().getServletContext()
				.getAttribute("CardCodes");
		if (codeMap == null) {
			return value;
		}
		// System.out.println("key  "+codeMap);
		List<Map> codeList = (List) codeMap.get(key);

		if (codeList == null) {
			return value;
		}
		String[] vs = value.split(",");
		StringBuffer ss = new StringBuffer();
		for (String s : vs) {
			for (Map map : codeList) {
				if (map.get("DMZ").equals(s)) {
					ss.append((String) map.get("DMSM1"));
					ss.append(",");
				}
			}
		}
		if(ss.length()>0){
			return ss.substring(0,ss.length()-1);
		}else{
			return value;
		}
		
	}
	
	public static void setXczl(PreCarRegister bcr, Map<String, Object> data) {
		if (bcr != null) {
			if ("K18".equals(bcr.getCllx()) ||  "K28".equals(bcr.getCllx())|| "K38".equals(bcr.getCllx())) {
				data.put("zyxc", "☑");
				data.put("fzyxc", "□");
			}else {
				data.put("zyxc", "□");
				data.put("fzyxc", "☑");
			}
		}
	}
	
	/**
	 * 图片旋转， 传进来图片的路径，和旋转的角度，好像只能是90度90度这样旋转
	 * @param src
	 * @param angel
	 * @return
	 */
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
    

}
