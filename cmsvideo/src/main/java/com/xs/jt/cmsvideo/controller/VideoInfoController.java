package com.xs.jt.cmsvideo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@Value("${video.ftppath}")
	private String ftpPath;
	
	@Autowired
	private IVideoInfoManager videoInfoManager;
	
	@UserOperation(code = "getVideoInfoList", name = "查询视频列表")
	@RequestMapping(value = "getVideoInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoInfoList(Integer page, Integer rows, VideoInfo videoInfo) {
		return videoInfoManager.getVideoInfoList(page - 1, rows, videoInfo);
	}
	
	@UserOperation(code = "retry", name = "批量重试")
	@RequestMapping(value = "retry", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> retry(@RequestBody List<VideoInfo> infoList){
		for(VideoInfo info:infoList) {
			info.setTaskCount(0);
			if(VideoInfo.ZT_XZSB.equals(info.getZt())) {
				info.setZt(VideoInfo.ZT_WXZ);
			}else if(VideoInfo.ZT_ZMSCSB.equals(info.getZt())) {
				info.setZt(VideoInfo.ZT_YXZ);
			}else {
				info.setZt(VideoInfo.ZT_WXZ);
			}
			videoInfoManager.save(info);
		}
		return ResultHandler.toSuccessJSON("批量重试成功");
	}
	
	
	
	@RequestMapping(value = "getVideoInfoByLsh")
	public String getVideoInfoByLsh(@RequestParam("lsh") String jylsh,Model model){
		List<VideoInfo> list = videoInfoManager.getVideoInfoByJylsh(jylsh);
		model.addAttribute("videoInfoList", list);
		
		return "playVideo";
	}

}
