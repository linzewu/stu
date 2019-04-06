package com.xs.jt.vehvideo.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.vehvideo.dao.VideoInfoRepository;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.manager.IVideoInfoManager;

@Service
public class VideoInfoManagerImp implements IVideoInfoManager {

	@Autowired
	private VideoInfoRepository videoInfoRepository;

	@Autowired
	EntityManager entityManager;

	@Override
	public Page<VideoInfo> getVideoInfos(Pageable pageable , VideoInfo videoInfo) {
		//Page<VideoInfo> videos = videoInfoRepository.getVideoInfoByZt(videoInfo.getZt(), videoInfo.getTaskCount(),pageable);
		StringBuilder sql =new StringBuilder(" from VideoInfo where ip is not null and port is not null  ");
		StringBuilder countSql=new  StringBuilder("select count(*) ");
		
		Map<String,Object> param =new HashMap<String,Object>();
		
		if(Common.isNotEmpty(videoInfo.getJyjgbh())) {
			sql.append(" and jyjgbh=:jyjgbh");
			param.put("jyjgbh", videoInfo.getJyjgbh());
		}
		if(Common.isNotEmpty(videoInfo.getClsbdh())) {
			sql.append(" and clsbdh like :clsbdh");
			param.put("clsbdh", "%"+ videoInfo.getClsbdh()+"%");
		}
		if(Common.isNotEmpty(videoInfo.getJylsh())) {
			sql.append(" and jylsh like :jylsh");
			param.put("jylsh", "%"+ videoInfo.getJylsh()+"%");
		}
		if(Common.isNotEmpty(videoInfo.getHphm())) {
			sql.append(" and hphm =:hphm");
			param.put("hphm", videoInfo.getHphm());
		}
		if(Common.isNotEmpty(videoInfo.getHpzl())) {
			sql.append(" and hpzl =:hpzl");
			param.put("hpzl", videoInfo.getHpzl());
		}
		if(videoInfo.getZt() != null) {
			sql.append(" and zt = :zt");
			param.put("zt", videoInfo.getZt());
		}
		
		if(videoInfo.getTaskCount() != null) {
			sql.append(" and taskCount < :taskCount and jssj<dateadd(minute,-10,GETDATE())");
			param.put("taskCount", videoInfo.getTaskCount());
		}
		
		TypedQuery<VideoInfo> query = entityManager.createQuery(sql.toString(), VideoInfo.class);
		setParameters(query,param);
		
		Page<VideoInfo> pageInfo=null;
		
		if(pageable!=null) {
			query.setFirstResult((int) pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			TypedQuery<Long> countQuery = entityManager.createQuery(countSql.append(sql).toString(), Long.class);
			setParameters(countQuery,param);
			Long count = countQuery.getSingleResult();
			pageInfo =new PageImpl<VideoInfo>(query.getResultList(), pageable, count);
		}else {
			pageInfo = new PageImpl<VideoInfo>(query.getResultList());
		}
		
		return pageInfo;
	}

	@Override
	public VideoInfo save(VideoInfo videoInfo) {
		return videoInfoRepository.save(videoInfo);
	}

	private void setParameters(TypedQuery query, Map<String, Object> params) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

}
