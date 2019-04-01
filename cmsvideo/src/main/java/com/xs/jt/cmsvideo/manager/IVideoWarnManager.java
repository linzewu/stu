package com.xs.jt.cmsvideo.manager;

import java.util.Map;

import com.xs.jt.cmsvideo.entity.VideoWarn;

public interface IVideoWarnManager {
	
	public VideoWarn save(VideoWarn videoWarn); 
	
	public Map<String,Object> getVideoWarnList(Integer page, Integer rows,VideoWarn videoWarn);

}
