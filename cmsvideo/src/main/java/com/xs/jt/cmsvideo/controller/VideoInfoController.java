package com.xs.jt.cmsvideo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;

@Controller
@RequestMapping(value = "/videoInfo")
@Modular(modelCode = "videoInfo", modelName = "视频管理")
public class VideoInfoController {
	
	@Autowired
	private IVideoInfoManager videoInfoManager;
	
	@UserOperation(code = "getVideoInfoList", name = "查询视频列表")
	@RequestMapping(value = "getVideoInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoInfoList(Integer page, Integer rows, VideoInfo videoInfo) {
		return videoInfoManager.getVideoInfoList(page - 1, rows, videoInfo);
	}
	
	@UserOperation(code = "retry", name = "批量重试")
	@RequestMapping(value = "retry", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> retry(List<VideoInfo> infoList){
		for(VideoInfo info:infoList) {
			info.setTaskCount(0);
			videoInfoManager.save(info);
		}
		return ResultHandler.toSuccessJSON("批量重试成功");
	}

}
