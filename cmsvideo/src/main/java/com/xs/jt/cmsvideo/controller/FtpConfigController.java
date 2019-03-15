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
import com.xs.jt.cmsvideo.entity.FtpConfig;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.manager.IFtpConfigManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/ftpConfig")
@Modular(modelCode = "ftpConfig", modelName = "FTP配置")
public class FtpConfigController {
	@Autowired
	private IFtpConfigManager ftpConfigManager;
	
	@UserOperation(code = "getFtpConfigList", name = "查询Ftp配置列表")
	@RequestMapping(value = "getFtpConfigList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoConfigList(Integer page, Integer rows, FtpConfig ftpConfig) {
		return ftpConfigManager.getFtpConfigList(page - 1, rows, ftpConfig);
	}
	
	@UserOperation(code="save",name="保存Ftp配置")
	@RequestMapping(value = "save", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String saveFtpConfig(@Valid FtpConfig ftpConfig, BindingResult result) {
		if (!result.hasErrors()) {
			
			this.ftpConfigManager.saveFtpConfig(ftpConfig);
			return  JSONObject.fromObject(ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS)).toString();
		}else{
			return JSONObject.fromObject(ResultHandler.resultHandle(result,null ,null)).toString();
		}
	}
	
	@UserOperation(code="delete",name="删除Ftp配置")
	@RequestMapping(value = "delete", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(FtpConfig ftpConfig){
		this.ftpConfigManager.deleteFtpConfig(ftpConfig);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}

}
