package com.xs.jt.vehvideo.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.vehvideo.dao.VideoInfoRepository;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.manager.IVideoInfoManager;

@Service
public class VideoInfoManagerImp implements IVideoInfoManager {
	
	protected static Log log = LogFactory.getLog(VideoInfoManagerImp.class);

	@Autowired
	private VideoInfoRepository videoInfoRepository;

	@Autowired
	EntityManager entityManager;

	@Override
	public Page<VideoInfo> getVideoInfos(Pageable pageable , VideoInfo videoInfo) {
		
		return videoInfoRepository.findAll(new Specification<VideoInfo>() {
			@Override
			public Predicate toPredicate(Root<VideoInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				List<Order> orders = new ArrayList<Order>();
				
				list.add(criteriaBuilder.isNotNull(root.get("ip")));
				list.add(criteriaBuilder.isNotNull(root.get("port")));
				
				if(Common.isNotEmpty(videoInfo.getJyjgbh())) {
					list.add(criteriaBuilder.equal(root.get("jyjgbh").as(String.class),videoInfo.getJyjgbh()));
				}
				if(Common.isNotEmpty(videoInfo.getClsbdh())) {
					list.add(criteriaBuilder.like(root.get("clsbdh").as(String.class),"%"+ videoInfo.getClsbdh()+"%"));
				}
				if(Common.isNotEmpty(videoInfo.getJylsh())) {
					list.add(criteriaBuilder.like(root.get("jylsh").as(String.class),"%"+ videoInfo.getJylsh()+"%"));
				}
				if(Common.isNotEmpty(videoInfo.getHphm())) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class),videoInfo.getHphm()));
				}
				if(Common.isNotEmpty(videoInfo.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class),videoInfo.getHpzl()));
				}
				if(videoInfo.getZt() != null) {
					list.add(criteriaBuilder.equal(root.get("zt").as(Integer.class),videoInfo.getZt()));
				}
				
				if(videoInfo.getTaskCount() != null) {
					list.add(criteriaBuilder.lt(root.get("taskCount").as(Integer.class),videoInfo.getTaskCount()));
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MINUTE, -10);
					criteriaBuilder.lessThan(root.get("jssj").as(Date.class), calendar.getTime());
				}
				
				orders.add(criteriaBuilder.desc(root.get("jssj")));
				query.orderBy(orders);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
			
		},pageable);
	}

	@Override
	@Async
	public VideoInfo save(VideoInfo videoInfo) {
		return videoInfoRepository.save(videoInfo);
	}
	
	

	private void setParameters(TypedQuery query, Map<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	@Override
	@Async
	public void saveAll(List<VideoInfo> videoInfo) {
		videoInfoRepository.saveAll(videoInfo);
	}

}
