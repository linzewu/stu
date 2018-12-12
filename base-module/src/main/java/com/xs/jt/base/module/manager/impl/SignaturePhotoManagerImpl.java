package com.xs.jt.base.module.manager.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.dao.SignaturePhotoRepository;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.SignaturePhoto;
import com.xs.jt.base.module.manager.ISignaturePhotoManager;

@Service("signaturePhotoManager")
public class SignaturePhotoManagerImpl implements ISignaturePhotoManager {

	@Autowired
	private SignaturePhotoRepository signaturePhotoRepository;

	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public SignaturePhoto findByYhm(String yhm) {
		return signaturePhotoRepository.findByYhm(yhm);
	}

	@Override
	public void saveSignaturePhoto(SignaturePhoto signaturePhoto) {
		int qmzq = 7;
		ServletContext servletContext = request.getSession().getServletContext();
		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		for(BaseParams bp:bps) {
			if("qmzq".equals(bp.getType())) {
				qmzq = Integer.parseInt(bp.getParamValue());
				break;
			}
		}
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		
		SignaturePhoto photo = findByYhm(signaturePhoto.getYhm());
		if (photo != null) {
			
			date = photo.getYxq() == null ? date : photo.getYxq();
			signaturePhotoRepository.delete(photo);
		}
		calendar.setTime(date);
		calendar.add(Calendar.DATE, qmzq);
		signaturePhoto.setYxq(calendar.getTime());
		signaturePhotoRepository.save(signaturePhoto);
	}

	@Override
	public String findSignaturePhotoByYhm(String yhm) {
		SignaturePhoto photo = findByYhm(yhm);
		String imagePath = "\\signatureimage\\" + yhm + ".jpg";
		try {
			// 判断是否存在目录，不存在就创建
			File pathFile = new File(cacheDir + "\\signatureimage");
			if (!pathFile.exists()) {
				pathFile.mkdir();
			}
			if (photo == null) {
				return "";
			} else {
				// 生成图片
				File imgFile = new File(cacheDir + imagePath);
				//if (!imgFile.exists()) {

					ByteArrayInputStream bais = new ByteArrayInputStream(photo.getPhoto());
					BufferedImage bi1 = ImageIO.read(bais);
					File w2 = new File(cacheDir + imagePath);// 可以是jpg,png格式

					ImageIO.write(bi1, "jpg", w2);

				//}
			}
		} catch (IOException e) {
			throw new ApplicationException("生成图片异常", e);
		}
		return imagePath;
	}

}
