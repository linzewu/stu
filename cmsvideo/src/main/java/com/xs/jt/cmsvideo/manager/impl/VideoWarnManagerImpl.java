package com.xs.jt.cmsvideo.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.cmsvideo.dao.VideoWarnRepository;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.entity.VideoWarn;
import com.xs.jt.cmsvideo.manager.IVideoWarnManager;
@Service
public class VideoWarnManagerImpl implements IVideoWarnManager {
	@Autowired
	private VideoWarnRepository VideoWarnRepository;

	@Override
	public VideoWarn save(VideoWarn videoWarn) {
		return VideoWarnRepository.save(videoWarn);
	}

	@Override
	public Map<String, Object> getVideoWarnList(Integer page, Integer rows, VideoWarn videoWarn) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VideoWarn> bookPage = VideoWarnRepository.findAll(new Specification<VideoWarn>() {
			public Predicate toPredicate(Root<VideoWarn> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(videoWarn.getType() != null) {
					list.add(criteriaBuilder.equal(root.get("type").as(Integer.class), videoWarn.getType()));
				}
				
//				Join<VideoWarn, VideoInfo> join = root.join("vid", JoinType.LEFT);
				
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("lsh")));
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

}
