package com.xs.jt.cmsvideo.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.xs.jt.cmsvideo.entity.VideoInfo;


public interface IVideoInfoManager {
	
	public Map<String,Object> getVideoInfoList(Integer page, Integer rows,VideoInfo videoInfo);
	
	public List<VideoInfo> getVideoInfoByZt(Integer zt,Integer taskCount);
	
	public VideoInfo save(VideoInfo videoInfo); 
	
	public VideoInfo getVideoInfoById(Integer id);
	
	public List<VideoInfo> getVideoInfoByLshAndJycs(String lsh,Integer jycs);
	
	public List<VideoInfo> getVideoInfosNoDownLoad(Integer zt,Integer taskCount);
	
	public List<VideoInfo> getVideoInfoByJylsh(String jylsh);
	
	public List<Map> getVideoInfoStatistics();
	

}
