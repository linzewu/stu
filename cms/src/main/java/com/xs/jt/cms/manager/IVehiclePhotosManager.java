package com.xs.jt.cms.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.cms.entity.VehiclePhotos;

public interface IVehiclePhotosManager {
	
	public VehiclePhotos save(VehiclePhotos motorVehiclePhotos);
	
	public VehiclePhotos findLast45degPhotosByLsh(String lsh);
	
	public VehiclePhotos findPhotosByLshAndZpzlAndJccs(String lsh,String  zpzl,int jccs);
	
	public List<Map> findPhotosByLshAndJccs(String lsh,int jccs);
	
	public List<Map> findPhotosByLsh(String lsh);
	
	public String findVehPhotoById(Integer id);
	
	public VehiclePhotos findPhotosByLshAndZpzlAndJccsAndClsbdh(String lsh,String  zpzl,int jccs,String  clsbdh);

	public void deleteVehiclePhoto(VehiclePhotos photo);
}
