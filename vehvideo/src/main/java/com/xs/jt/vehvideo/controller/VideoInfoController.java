package com.xs.jt.vehvideo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.manager.IVideoInfoManager;

@Controller
@RequestMapping(value = "/videoInfo")
@Modular(modelCode = "videoInfo", modelName = "视频管理")
public class VideoInfoController {
	
	@Autowired
	private IVideoInfoManager videoInfoManager;
	
	@UserOperation(code = "getVideoInfoList", name = "查询视频列表")
	@RequestMapping(value = "getVideoInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoInfoList(Integer page, Integer rows, VideoInfo videoInfo) {
		
		Pageable pageable =new QPageRequest(page - 1, rows);
		
		Page<VideoInfo> bookPage = videoInfoManager.getVideoInfos(pageable, videoInfo);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());
		return data;
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

}
