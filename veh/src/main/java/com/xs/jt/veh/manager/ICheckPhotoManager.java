package com.xs.jt.veh.manager;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.CheckPhoto;

public interface ICheckPhotoManager {

	public void saveCheckPhoto(CheckPhoto checkPhoto);

	public Message savePhoto(CheckPhoto checkPhoto);
	
	public CheckPhoto getCheckPhoto(String jylsh, String zpzl, Integer jycs);

}
