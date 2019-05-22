package com.xs.jt.cmsvideo.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.cmsvideo.entity.PdaInfo;
import com.xs.jt.cmsvideo.manager.IPdaInfoManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/pdaInfo")
@Modular(modelCode = "pdaInfo", modelName = "PDA管理",jsjb= {Role.JSJB_YWBL})
public class PdaInfoController {
	
	@Autowired
	private IPdaInfoManager pdaInfoManager;
	
	@UserOperation(code = "getPdaInfoList", name = "查询PDA配置")
	@RequestMapping(value = "getPdaInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getPdaInfoList(Integer page, Integer rows, PdaInfo pdaInfo) {
		return pdaInfoManager.getPdaInfoList(page - 1, rows, pdaInfo);
	}
	
	
	@UserOperation(code="save",name="保存PDA配置")
	@RequestMapping(value = "save", method = RequestMethod.POST)//,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"
	public @ResponseBody Map<String, Object> savePdaInfo(@Valid PdaInfo pdaInfo, BindingResult result) {
		if (!result.hasErrors()) {
			pdaInfoManager.savePdaInfo(pdaInfo);
			return  ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result,null ,null);
		}
	}
	
	@UserOperation(code="delete",name="删除PDA配置")
	@RequestMapping(value = "delete", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(PdaInfo pdaInfo){
		this.pdaInfoManager.deletePdaInfo(pdaInfo);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@UserOperation(code="save",name="校验串码唯一性",isMain=false)
	@RequestMapping(value = "validateSerialCode")
	public @ResponseBody boolean validateSerialCode(PdaInfo pdaInfo) {
		if(pdaInfo.getId() != null) {
			return true;
		}
		PdaInfo pda = this.pdaInfoManager.getPdaInfoBySerialCode(pdaInfo.getSerialCode());
		if(pda==null){
			return true;
		}else{
			return false;
		}

	}

}
