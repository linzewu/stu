package com.xs.jt.cms.manager;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.xs.jt.cms.entity.VehiclePhotos;

public interface IVehiclePhotosManager {
	
	public VehiclePhotos save(VehiclePhotos motorVehiclePhotos);
	
	public VehiclePhotos findLast45degPhotosByLsh(String lsh);
	
	public VehiclePhotos findPhotosByLshAndZpzlAndJccs(String lsh,String  zpzl,int jccs);
	
	public List<VehiclePhotos> findPhotosByLshAndJccs(String lsh,int jccs);
	
	public List<VehiclePhotos> findPhotosByLsh(String lsh);
	
	public VehiclePhotos findVehPhotoById(Integer id);
	
	public VehiclePhotos findPhotosByLshAndZpzlAndJccsAndClsbdh(String lsh,String  zpzl,int jccs,String  clsbdh);

	public void deleteVehiclePhoto(VehiclePhotos photo);
}
