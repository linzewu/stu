package com.xs.jt.vehvideo.manager;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xs.jt.vehvideo.entity.VideoInfo;

public interface IVideoInfoManager {
	
	public Page<VideoInfo> getVideoInfos(Pageable pageable, VideoInfo videoInfo);
	
	public VideoInfo save(VideoInfo videoInfo);
	
	public void saveAll(List<VideoInfo> videoInfo);

}
