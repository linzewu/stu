package com.xs.jt.srms.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.manager.IArchivalRegisterManager;
import com.xs.jt.srms.util.BarcodeUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/archivalFiling")
@Modular(modelCode = "archivalFiling", modelName = "档案归档")
public class ArchivalFilingController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IArchivalRegisterManager archivalRegisterManager;
	
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Transactional
	@UserOperation(code="newCarArchivalCheckIn",name="新车档案入库")
	@RequestMapping(value = "newCarArchivalCheckIn", method = RequestMethod.POST)
	public @ResponseBody Map newCarArchivalCheckIn(ArchivalCase archivalCase){
		List<ArchivalCase> existList = this.archivalCaseManager.getArchivalCaseByClsbdh(archivalCase.getClsbdh());
		if(!CollectionUtils.isEmpty(existList)) {
			return  ResultHandler.toErrorJSON("库房已存在该车辆信息，不能做新车档案入库，请核对！");
		}
		ArchivalCase ac = this.archivalCaseManager.newCarArchivalCheckIn(archivalCase);
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, ac);//JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@Transactional
	@UserOperation(code="UsedCarArchivalCheckIn",name="在用车档案入库")
	@RequestMapping(value = "UsedCarArchivalCheckIn", method = RequestMethod.POST)
	public @ResponseBody Map UsedCarArchivalCheckIn(ArchivalCase archivalCase){		
		ArchivalCase ac = this.archivalCaseManager.UsedCarArchivalCheckIn(archivalCase);
		//return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, ac);
	}
	
	@Transactional
	@UserOperation(code="archivalCaseAdjust",name="档案格调整")
	@RequestMapping(value = "archivalCaseAdjust", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String archivalCaseAdjust(ArchivalCase archivalCase){		
		boolean flag = this.archivalCaseManager.archivalCaseAdjust(archivalCase);
		if(flag) {			
			return  JSONObject.fromObject(ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS)).toString();
		}else {
			return  JSONObject.fromObject(ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR,Constant.ConstantMessage.FAILURE)).toString();
		}
		
	}
	
	@UserOperation(code = "createLabel", name = "条码标签打印")
	@RequestMapping(value = "createLabel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createLabel(String barCode){
		String path = cacheDir+"/barcode/";
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		File barFile = BarcodeUtil.generateFile(barCode, path+barCode+".jpg");
		
		return data;
	}
	
	
	
	
	
	
	
	

}
