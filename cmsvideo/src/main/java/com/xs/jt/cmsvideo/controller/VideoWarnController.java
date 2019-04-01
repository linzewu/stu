package com.xs.jt.cmsvideo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.cmsvideo.entity.VideoWarn;
import com.xs.jt.cmsvideo.manager.IVideoWarnManager;

@Controller
@RequestMapping(value = "/videoWarn")
@Modular(modelCode = "videoWarn", modelName = "视频预警")
public class VideoWarnController {
	@Autowired
	private IVideoWarnManager videoWarnManager;
	
	@UserOperation(code = "getVideoWarnList", name = "查询视频预警")
	@RequestMapping(value = "getVideoWarnList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoWarnList(Integer page, Integer rows, VideoWarn videoWarn) {
		return videoWarnManager.getVideoWarnList(page - 1, rows, videoWarn);
	}
}
