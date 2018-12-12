package com.xs.jt.cms.manager.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.cms.dao.VehiclePhotosRepository;
import com.xs.jt.cms.entity.VehiclePhotos;
import com.xs.jt.cms.manager.IVehiclePhotosManager;
@Service("vehiclePhotosManager")
public class VehiclePhotosManagerImpl implements IVehiclePhotosManager {
	@Autowired
	public VehiclePhotosRepository vehiclePhotosRepository;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;

	public VehiclePhotos save(VehiclePhotos motorVehiclePhotos) {
		return vehiclePhotosRepository.save(motorVehiclePhotos);
	}
	
	public VehiclePhotos findLast45degPhotosByLsh(String lsh) {
		VehiclePhotos photos = vehiclePhotosRepository.findLastPhotosByLshAndZpzl(lsh,"11");
		
		return photos;
	}

	public VehiclePhotos findPhotosByLshAndZpzlAndJccs(String lsh, String zpzl, int jccs) {
		VehiclePhotos photos = vehiclePhotosRepository.findPhotosByLshAndZpzlAndJccs(lsh, zpzl, jccs);
		if(photos==null&&jccs>1) {
			photos = findPhotosByLshAndZpzlAndJccs(lsh,zpzl,jccs-1);
		}
		return photos;
	}

	@Override
	public List<Map> findPhotosByLshAndJccs(String lsh, int jccs) {
		return vehiclePhotosRepository.findPhotosByLshAndJccs(lsh, jccs);
	}

	@Override
	public List<Map> findPhotosByLsh(String lsh) {
		return vehiclePhotosRepository.findPhotosByLsh(lsh);
	}

	@Override
	public String findVehPhotoById(Integer id) {
		VehiclePhotos photo = this.vehiclePhotosRepository.findById(id).get();
		String imagePath = "\\vehimage\\" + id + ".jpg";
		try {
			//判断是否存在目录，不存在就创建
			File pathFile = new File(cacheDir+"\\vehimage");
			if(!pathFile.exists()) {
				pathFile.mkdir();
			}
			//生成图片
			File imgFile = new File(cacheDir + imagePath);
			if (!imgFile.exists()) {

				ByteArrayInputStream bais = new ByteArrayInputStream(photo.getPhoto());
				BufferedImage bi1 = ImageIO.read(bais);
				File w2 = new File(cacheDir + imagePath);// 可以是jpg,png格式

				ImageIO.write(bi1, "jpg", w2);

			}
		} catch (IOException e) {
			throw new ApplicationException("生成图片异常", e);
		}
		return imagePath;
	}

	@Override
	public VehiclePhotos findPhotosByLshAndZpzlAndJccsAndClsbdh(String lsh, String zpzl, int jccs, String clsbdh) {
		return this.vehiclePhotosRepository.findPhotosByLshAndZpzlAndJccsAndClsbdh(lsh, zpzl, jccs, clsbdh);
	}

	@Override
	public void deleteVehiclePhoto(VehiclePhotos photo) {
		vehiclePhotosRepository.delete(photo);
		
	}

}
