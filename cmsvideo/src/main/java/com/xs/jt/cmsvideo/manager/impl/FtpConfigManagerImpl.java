package com.xs.jt.cmsvideo.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xs.jt.cmsvideo.dao.FtpConfigRepository;
import com.xs.jt.cmsvideo.entity.FtpConfig;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.manager.IFtpConfigManager;
@Service
public class FtpConfigManagerImpl implements IFtpConfigManager {
	
	@Autowired
	private FtpConfigRepository ftpConfigRepository;

	@Override
	public Map<String, Object> getFtpConfigList(Integer page, Integer rows, FtpConfig ftpConfig) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<FtpConfig> bookPage = ftpConfigRepository.findAll(new Specification<FtpConfig>() {
			public Predicate toPredicate(Root<FtpConfig> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
//				if(Common.isNotEmpty(videoConfig.getJyjgbh())) {
//					list.add(criteriaBuilder.equal(root.get("jyjgbh").as(String.class), videoConfig.getJyjgbh()));
//				}
							
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
	public void saveFtpConfig(FtpConfig ftpConfig) {
		this.ftpConfigRepository.save(ftpConfig);

	}

	@Override
	public void deleteFtpConfig(FtpConfig ftpConfig) {
		this.ftpConfigRepository.delete(ftpConfig);

	}

}
