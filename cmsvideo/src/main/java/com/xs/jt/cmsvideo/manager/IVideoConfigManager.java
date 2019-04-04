package com.xs.jt.cmsvideo.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.cmsvideo.entity.VideoConfig;

public interface IVideoConfigManager {
	public Map<String,Object> getVideoConfigList(Integer page, Integer rows,VideoConfig videoConfig);
	
	public void saveVideoConfig(VideoConfig videoConfig);
	
	public void deleteVideoConfig(VideoConfig videoConfig);
	
	public List<VideoConfig> getVideoConfigByCyqxhAndCyqtd(String cyqxh,String cyqtd);
	
	public VideoConfig getVideoConfigById(Integer id);
	
	public List<VideoConfig> getVideoConfigByJyjgbh(String jyjgbh);
}
