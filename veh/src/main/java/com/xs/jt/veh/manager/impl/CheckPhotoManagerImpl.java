package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.dao.CheckPhotoRepository;
import com.xs.jt.veh.entity.CheckPhoto;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.ICheckPhotoManager;

@Service("checkPhotoManager")
public class CheckPhotoManagerImpl implements ICheckPhotoManager {

	private static Log logger = LogFactory.getLog(CheckPhotoManagerImpl.class);
	@Autowired
	private CheckPhotoRepository checkPhotoRepository;
	@Autowired
	private ICheckEventManager checkEventManager;

	@Override
	public void saveCheckPhoto(CheckPhoto checkPhoto) {

		checkPhotoRepository.deleteCheckPhotoByJylshAndZpzl(checkPhoto.getJylsh(), checkPhoto.getZpzl());

		this.checkPhotoRepository.save(checkPhoto);

		logger.info("检验流水号：" + checkPhoto.getJylsh() + ",检验次数：" + checkPhoto.getJycs() + "，检验项目：" + checkPhoto.getJyxm()
				+ ",照片种类：" + checkPhoto.getZpzl());

		checkEventManager.createEvent(checkPhoto.getJylsh(), checkPhoto.getJycs(), "18C63", checkPhoto.getJyxm(),
				checkPhoto.getHphm(), checkPhoto.getHpzl(), checkPhoto.getClsbdh(), checkPhoto.getZpzl(), 0);

	}

	@Override
	public Message savePhoto(CheckPhoto checkPhoto) {

		checkPhotoRepository.deleteCheckPhotoByJylshAndZpzl(checkPhoto.getJylsh(), checkPhoto.getZpzl());

		this.checkPhotoRepository.save(checkPhoto);
		checkEventManager.createEvent(checkPhoto.getJylsh(), checkPhoto.getJycs(), "18C63", checkPhoto.getJyxm(),
				checkPhoto.getHphm(), checkPhoto.getHpzl(), checkPhoto.getClsbdh(), checkPhoto.getZpzl(), 0);

		Message message = new Message();
		message.setMessage("上传成功");
		message.setState(Message.STATE_SUCCESS);

		return message;
	}

	@Override
	public CheckPhoto getCheckPhoto(String jylsh, String zpzl, Integer jycs) {
		List<CheckPhoto> datas = checkPhotoRepository.findCheckPhotoByJylshAndZpzlAndJycs(jylsh, zpzl, jycs);

		if (datas == null || datas.isEmpty()) {
			return null;
		}

		return datas.get(0);
	}

}
