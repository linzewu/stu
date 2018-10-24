package com.xs.jt.base.module.manager.impl;

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

import com.xs.jt.base.module.dao.SecurityLogRepository;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.manager.ISecurityLogManager;
@Service("securityLogManager")
public class SecurityLogManagerImpl implements ISecurityLogManager {
	
	@Autowired
	private SecurityLogRepository securityLogRepository;

	
	public void saveSecurityLog(SecurityLog securityLog) {
		securityLogRepository.save(securityLog);
	}


	public Map<String, Object> getSecurityLogs(Integer page, Integer rows, SecurityLog securityLog) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<SecurityLog> bookPage = securityLogRepository.findAll(new Specification<SecurityLog>() {
			public Predicate toPredicate(Root<SecurityLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();				
//				list.add(criteriaBuilder.equal(root.get("enableFlag").as(String.class), "Y"));
				
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
