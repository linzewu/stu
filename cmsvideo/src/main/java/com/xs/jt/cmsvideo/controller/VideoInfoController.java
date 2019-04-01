package com.xs.jt.cmsvideo.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
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
	
	
	@UserOperation(code = "getVideoInfoStatistics", name = "查验视频预警")
	@RequestMapping(value = "getVideoInfoStatistics", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVideoInfoStatistics(){
		DecimalFormat   df   =new   DecimalFormat("#.00");  
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<Map> list = videoInfoManager.getVideoInfoStatistics();
		for(Map map:list) {
			Double videoSum = Double.valueOf(map.get("videoSize").toString());
			videoSum = videoSum/1024/1024/1024;
			Double capacity = Double.valueOf(map.get("capacity").toString()) * 1024;
			map.put("videoSizeT", df.format(videoSum));
			map.put("capacity", capacity);
		}
		data.put("rows", list);
		return data;
	}
	
	@UserOperation(code="saveVideoTime",name="修改视频开始结束时间")
	@RequestMapping(value = "saveVideoTime", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveVideoTime(@RequestBody VideoInfo videoInfo) {
		this.videoInfoManager.save(videoInfo);
		
		return  ResultHandler.toSuccessJSON("修改成功！");
		
	}

}
