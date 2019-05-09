package com.xs.jt.srms.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.util.StringUtils;

public class BarcodeUtil {

	public static File generateFile(String msg, String path) {
		File file = new File(path);
		try {
			generate(msg, new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	public static byte[] generate(String msg) {
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		generate(msg, ous);
		return ous.toByteArray();
	}

	public static InputStream generateInputStream(String msg) {
		InputStream input = new ByteArrayInputStream(generate(msg));
		return input;
	}

	public static void generate(String msg, OutputStream ous) {

		if (StringUtils.isEmpty(msg) || ous == null) {
			return;
		}
		Code128Bean bean = new Code128Bean();
		// 精细度

		final int dpi = 60;
		// module宽度
		final double moduleWidth = UnitConv.in2mm(1.0f / dpi);
		// 配置对象
		bean.setModuleWidth(moduleWidth);
		bean.setBarHeight((double) ObjectUtils.defaultIfNull(9.0D, 9.0D));
		//bean.setHeight(18);
		bean.setFontSize(5);
		//bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);

		bean.doQuietZone(false);
		String format = "image/png";
		try {
			// 输出到流
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi, BufferedImage.TYPE_BYTE_BINARY,
					false, 0);

			// 生成条形码
			bean.generateBarcode(canvas, msg);
			// 结束绘制
			canvas.finish();

		} catch (IOException e) {

			throw new RuntimeException(e);

		}

	}
	
	public static void main(String[] args) {
		List  aa = null;
		
		System.out.println(CollectionUtils.isEmpty(aa));
		aa =new ArrayList();
		aa.add("dd");
		System.out.println(CollectionUtils.isEmpty(aa));
	}

}
