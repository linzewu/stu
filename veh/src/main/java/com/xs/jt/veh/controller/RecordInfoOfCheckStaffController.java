package com.xs.jt.veh.controller;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.common.DocumentHelpers;
import com.xs.jt.veh.entity.RecordInfoOfCheckStaff;
import com.xs.jt.veh.manager.IRecordInfoOfCheckStaffManager;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;

@Controller
@RequestMapping(value = "/recordInfoOfCheckStaff")
@Modular(modelCode = "RecordInfoOfCheck", modelName = "检验机构信息", isEmpowered = false)
public class RecordInfoOfCheckStaffController {

	@Resource(name = "recordInfoOfCheckStaffManager")
	private IRecordInfoOfCheckStaffManager recordInfoOfCheckStaffManager;

	@Autowired
	private DocumentHelpers documentHelpers;

	@UserOperation(code = "getRecordInfoOfCheckStaffList", name = "检验机构人员信息查询")
	@RequestMapping(value = "getRecordInfoOfCheckStaffList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRecordInfoOfCheckStaff(Integer page, Integer rows,
			RecordInfoOfCheckStaff recordInfoOfCheckStaff) {
//		List<RecordInfoOfCheckStaff> vcps = recordInfoOfCheckStaffManager.getRecordInfoOfCheckStaff(page, rows, recordInfoOfCheckStaff);
//		
//		Integer total = recordInfoOfCheckStaffManager.getRecordInfoOfCheckStaffCount(page, rows, recordInfoOfCheckStaff);
//		
		Map<String, Object> data = recordInfoOfCheckStaffManager.getRecordInfoOfCheckStaff(page, rows,
				recordInfoOfCheckStaff);

//		data.put("rows", vcps);
//		data.put("total", total);		

		return data;
	}

	@UserOperation(code = "downloadRecords", name = "检验机构人员信息下载")
	@RequestMapping(value = "downloadRecords", method = RequestMethod.POST)
	public Map<String, Object> downloadRecords()
			throws RemoteException, UnsupportedEncodingException, DocumentException {

		Map param = new HashMap();
		Document document = documentHelpers.queryws("18C05", param);
		JSON json = new XMLSerializer().read(document.asXML());

		recordInfoOfCheckStaffManager.deleteRecordInfo();
		// RecordInfoOfCheckStaff recordInfoOfCheck = (RecordInfoOfCheckStaff)
		// JSONObject.toBean(JSONObject.fromObject(json.toString()),
		// RecordInfoOfCheckStaff.class);

		List<RecordInfoOfCheckStaff> dataList = (List<RecordInfoOfCheckStaff>) JSONArray
				.toArray(JSONArray.fromObject(json), RecordInfoOfCheckStaff.class);

		recordInfoOfCheckStaffManager.saveRecordInfoOfCheckStaff(dataList);

		return ResultHandler.toSuccessJSON("下载成功");
	}

}
