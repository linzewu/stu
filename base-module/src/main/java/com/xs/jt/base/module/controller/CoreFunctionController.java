package com.xs.jt.base.module.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.RecordLog;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.CoreFunction;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.manager.ICoreFunctionManager;

@Controller
@RequestMapping(value = "/coreFunction",produces="application/json")
@Modular(modelCode="coreFunction",modelName="核心功能管理",isEmpowered=false,jsjb= {Role.JSJB_STGL,Role.JSJB_AQGL})
public class CoreFunctionController {
	
	@Resource(name = "coreFunctionManager")
	private ICoreFunctionManager coreFunctionManager;
	
	@Autowired
	private ServletContext servletContext;
	
	@RecordLog
	@UserOperation(code="save",name="保存核心功能")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Transactional 
	public @ResponseBody Map<String,Object> saveCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(0);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(0);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(0);
			servletContext.setAttribute("coreFunctionList", coreList);
			return  ResultHandler.toSuccessJSON("保存核心功能成功！");		
	}
	
	@RecordLog
	@UserOperation(code="getAllCoreFunction",name="获取核心功能")
	@RequestMapping(value = "getAllCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllCoreFunction() {
		List<CoreFunction> list = (List<CoreFunction>)servletContext.getAttribute("coreFunctionList");
		return list;
	}
	
	@RecordLog
	@UserOperation(code="getAllSpecialCoreFunction",name="获取非常规功能")
	@RequestMapping(value = "getAllSpecialCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllSpecialCoreFunction() {
		List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(1);
		return coreList;
	}
	
	@RecordLog
	@UserOperation(code="getAllPoliceCoreFunction",name="获取警员功能")
	@RequestMapping(value = "getAllPoliceCoreFunction", method = RequestMethod.POST)
	public @ResponseBody List<CoreFunction> getAllPoliceCoreFunction() {
		List<CoreFunction> coreList = this.coreFunctionManager.getAllCoreFunction(2);
		return coreList;
	}
	
	@RecordLog
	@UserOperation(code="saveSpecialCoreFunction",name="保存非常规功能")
	@RequestMapping(value = "saveSpecialCoreFunction", method = RequestMethod.POST)
	@Transactional 
	public @ResponseBody Map<String,Object> saveSpecialCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(1);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(1);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			return  ResultHandler.toSuccessJSON("保存非常规功能成功！");		
	}
	
	@RecordLog
	@UserOperation(code="savePoliceCoreFunction",name="保存警员功能")
	@RequestMapping(value = "savePoliceCoreFunction", method = RequestMethod.POST)
	@Transactional 
	public @ResponseBody Map<String,Object> savePoliceCoreFunction(@RequestParam("functionPoint") String functionPoint) {
			this.coreFunctionManager.deleteAllCoreFunction(2);
			String[] functionPo = functionPoint.split(",");
			List<CoreFunction> funs =new ArrayList<CoreFunction>();
			for(String str:functionPo) {
				CoreFunction core = new CoreFunction();
				core.setFunctionPoint(str);
				//0:核心功能  1：非常规功能  2：警员功能
				core.setStatus(2);
				funs.add(core);
			}
			this.coreFunctionManager.save(funs);
			return  ResultHandler.toSuccessJSON("保存警员功能成功！");		
	}

}
