package com.xs.jt.cmsvideo.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.cmsvideo.dao.VideoInfoRepository;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;
@Service
public class VideoInfoManagerImpl implements IVideoInfoManager {
	
	@Autowired
	private VideoInfoRepository videoInfoRepository;

	@Override
	public Map<String, Object> getVideoInfoList(Integer page, Integer rows, VideoInfo videoInfo) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VideoInfo> bookPage = videoInfoRepository.findAll(new Specification<VideoInfo>() {
			public Predicate toPredicate(Root<VideoInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(videoInfo.getClsbdh())) {
					list.add(criteriaBuilder.like(root.get("clsbdh").as(String.class), "%"+ videoInfo.getClsbdh()+"%"));
				}
				if(Common.isNotEmpty(videoInfo.getLsh())) {
					list.add(criteriaBuilder.like(root.get("lsh").as(String.class), "%"+ videoInfo.getLsh()+"%"));
				}				
				
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("createTime")));
				query.orderBy(orders);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());
		return data;
	}

	@Override
	public List<VideoInfo> getVideoInfoByZt(Integer zt,Integer taskCount) {
		return this.videoInfoRepository.getVideoInfoByZt(zt,taskCount);
	}

	@Override
	public VideoInfo save(VideoInfo videoInfo) {
		return this.videoInfoRepository.save(videoInfo);
	}

	@Override
	public VideoInfo getVideoInfoById(Integer id) {
		Optional<VideoInfo> opt = videoInfoRepository.findById(id);
		if ((!opt.isPresent())) {
			return null;
		}
		return opt.get();
	}

	@Override
	public List<VideoInfo> getVideoInfoByLshAndJycs(String lsh, Integer jycs) {
		return videoInfoRepository.getVideoInfoByLshAndJycs(lsh, jycs);
	}

	@Override
	public List<VideoInfo> getVideoInfosNoDownLoad(Integer zt, Integer taskCount) {
		return videoInfoRepository.getVideoInfosNoDownLoad(zt, taskCount);
	}


}
