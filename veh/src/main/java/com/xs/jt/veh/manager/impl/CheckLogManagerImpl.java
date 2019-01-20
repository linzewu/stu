package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.CheckLogRepository;
import com.xs.jt.veh.entity.CheckLog;
import com.xs.jt.veh.manager.ICheckLogManager;

@Service("checkLogManager")
public class CheckLogManagerImpl implements ICheckLogManager {

	@Autowired
	private CheckLogRepository checkLogRepository;

	@Override
	public String getCheckItem(String jylsh, String type) throws DocumentException {
		List<CheckLog> checkLogs = (List<CheckLog>) this.checkLogRepository.findByJylshAndJkbmc(jylsh, "18C51");

		if (checkLogs != null && !checkLogs.isEmpty()) {

			CheckLog checkLog = checkLogs.get(0);

			Document doc = DocumentHelper.parseText(checkLog.getXml());

			String item = doc.getRootElement().element("head").element(type).getText();

			return item;

		}
		return "";
	}

	@Override
	public CheckLog getLoginCheckLog(String jylsh) {
		List<CheckLog> checkLogs = checkLogRepository.findByJylshAndJkbmc(jylsh, "18C51");
		if (checkLogs != null && !checkLogs.isEmpty()) {
			return checkLogs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void saveCheckLog(CheckLog checkLog) {
		checkLogRepository.save(checkLog);
		
	}

}
