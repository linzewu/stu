package com.xs.jt.veh.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping(value = "/common")
@Modular(modelCode="Common",modelName="公共服务")
public class CommonController {

	private static Log logger = LogFactory.getLog(CommonController.class);

	@UserOperation(code="uploadImage",name="图片上传")
	@RequestMapping(value = "uploadImage")
	public @ResponseBody String uploadImage(HttpServletRequest req,@RequestParam String imageData) throws IOException {

		String basePath = "cache/temp/";
		String filePath = req.getSession().getServletContext().getRealPath("/") + basePath;
		String fileName = UUID.randomUUID() + ".jpg";

		if (null != imageData) {
			generateImage(imageData,filePath,fileName);
			return basePath + fileName;
		}else{
			return "";
		}
	}

	public boolean generateImage(String imgStr, String filePath, String fileName) {
		try {
			if (imgStr == null) {
				return false;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			// 如果目录不存在，则创建
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 生成图片
			OutputStream out = new FileOutputStream(filePath + fileName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			logger.error("生成图片异常：{}", e);
			return false;
		}
	}


}
