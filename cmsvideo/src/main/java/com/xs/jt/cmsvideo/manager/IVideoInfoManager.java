package com.xs.jt.cmsvideo.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.cmsvideo.entity.VideoInfo;


public interface IVideoInfoManager {
	
	public Map<String,Object> getVideoInfoList(Integer page, Integer rows,VideoInfo videoInfo);
	
	public List<VideoInfo> getVideoInfoByZt(Integer zt,Integer taskCount);
	
	public VideoInfo save(VideoInfo videoInfo); 

}
