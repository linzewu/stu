package com.xs.jt.cms.manager;

import com.xs.jt.cms.entity.VehiclePhotos;

public interface IVehiclePhotosManager {
	
	public VehiclePhotos save(VehiclePhotos motorVehiclePhotos);
	
	public VehiclePhotos findLast45degPhotosByLsh(String lsh);

}
