package com.xs.jt.vehvideo.manager;

import java.util.List;

import com.xs.jt.vehvideo.entity.VideoInfo;

public interface IVideoInfoManager {
	
	public List<VideoInfo> getVideoInfosByZt(Integer zt, Integer taskCount);
	public VideoInfo save(VideoInfo videoInfo);

}
