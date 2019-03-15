package com.xs.jt.cmsvideo.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.cmsvideo.dao.VideoConfigRepository;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;
@Service
public class VideoConfigManagerImpl implements IVideoConfigManager {
	@Autowired
	private VideoConfigRepository videoConfigRepository;

	@Override
	public Map<String, Object> getVideoConfigList(Integer page, Integer rows, VideoConfig videoConfig) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VideoConfig> bookPage = videoConfigRepository.findAll(new Specification<VideoConfig>() {
			public Predicate toPredicate(Root<VideoConfig> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(videoConfig.getJyjgbh())) {
					list.add(criteriaBuilder.equal(root.get("jyjgbh").as(String.class), videoConfig.getJyjgbh()));
				}
							
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
	public void saveVideoConfig(VideoConfig videoConfig) {
		videoConfigRepository.save(videoConfig);
		
	}

	@Override
	public void deleteVideoConfig(VideoConfig videoConfig) {
		videoConfigRepository.delete(videoConfig);
		
	}

	@Override
	public List<VideoConfig> getVideoConfigByCyqxhAndCyqtd(String cyqxh, String cyqtd) {
		return videoConfigRepository.getVideoConfigByCyqxhAndCyqtd(cyqxh, cyqtd);
	}

	@Override
	public VideoConfig getVideoConfigById(Integer id) {
		Optional<VideoConfig> opt = videoConfigRepository.findById(id);
		if ((!opt.isPresent())) {
			return null;
		}
		return opt.get();
	}

}
