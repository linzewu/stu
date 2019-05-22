package com.xs.jt.cmsvideo.controller;

import java.util.List;
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
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/videoConfig")
@Modular(modelCode = "videoConfig", modelName = "视频配置",jsjb= {Role.JSJB_YWBL})
public class VideoConfigController {
	
	@Autowired
	private IVideoConfigManager videoConfigManager;
	
	@UserOperation(code = "getVideoConfigList", name = "查询视频配置")
	@RequestMapping(value = "getVideoConfigList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoConfigList(Integer page, Integer rows, VideoConfig videoConfig) {
		return videoConfigManager.getVideoConfigList(page - 1, rows, videoConfig);
	}
	
	
	@UserOperation(code="save",name="保存视频配置")
	@RequestMapping(value = "save", method = RequestMethod.POST)//,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"
	public @ResponseBody Map<String, Object> saveVideoConfig(@Valid VideoConfig videoConfig, BindingResult result) {
		if (!result.hasErrors()) {
			
			this.videoConfigManager.saveVideoConfig(videoConfig);
			return  ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result,null ,null);
		}
	}
	
	@UserOperation(code="delete",name="删除视频配置")
	@RequestMapping(value = "delete", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(VideoConfig videoConfig){
		this.videoConfigManager.deleteVideoConfig(videoConfig);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@UserOperation(code = "getVideoConfigByJyjgbh", name = "查询检验机构编号查询视频配置信息")
	@RequestMapping(value = "getVideoConfigByJyjgbh", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoConfigByJyjgbh(String jyjgbh) {
		List<VideoConfig> list = videoConfigManager.getVideoConfigByJyjgbh(jyjgbh);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "查询检验机构编号查询视频配置信息成功！", list);
	}

}
