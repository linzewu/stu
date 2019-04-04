package com.xs.jt.vehvideo.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.vehvideo.dao.VideoInfoRepository;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.manager.IVideoInfoManager;

@Service
public class VideoInfoManagerImp implements IVideoInfoManager {
	
	@Autowired
	private VideoInfoRepository videoInfoRepository;

	@Override
	public List<VideoInfo> getVideoInfosByZt(Integer zt, Integer taskCount) {
		
		Pageable pageable =new QPageRequest(0, 100);
		Page<VideoInfo> videos = videoInfoRepository.getVideoInfoByZt(zt, taskCount,pageable);
		return videos.getContent();
	}

	@Override
	public VideoInfo save(VideoInfo videoInfo) {
		return videoInfoRepository.save(videoInfo);
	}

}
