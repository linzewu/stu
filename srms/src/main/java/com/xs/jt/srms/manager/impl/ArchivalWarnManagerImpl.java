package com.xs.jt.srms.manager.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.xs.jt.srms.dao.ArchivalWarnRepository;
import com.xs.jt.srms.entity.ArchivalWarn;
import com.xs.jt.srms.manager.IArchivalWarnManager;
@Service
public class ArchivalWarnManagerImpl implements IArchivalWarnManager {
	
	@Autowired
	private ArchivalWarnRepository archivalWarnRepository;

	@Override
	public Map<String, Object> getArchivalWarnList(Integer page, Integer rows, ArchivalWarn archivalWarn) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<ArchivalWarn> archivalCasePage = archivalWarnRepository.findAll(new Specification<ArchivalWarn>() {
			public Predicate toPredicate(Root<ArchivalWarn> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(archivalWarn.getBegin() != null) {
					list.add(criteriaBuilder.between(root.get("warnDate").as(Date.class), archivalWarn.getBegin(),archivalWarn.getEnd()));
				}
						
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", archivalCasePage.getContent());
		data.put("total", archivalCasePage.getTotalElements());
		return data;

	}

}
