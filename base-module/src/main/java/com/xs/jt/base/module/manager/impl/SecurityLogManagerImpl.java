package com.xs.jt.base.module.manager.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.SecurityLogRepository;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.manager.ISecurityLogManager;
@Service("securityLogManager")
public class SecurityLogManagerImpl implements ISecurityLogManager {
	
	@Autowired
	private SecurityLogRepository securityLogRepository;
	
	@Autowired
    @PersistenceContext
    private EntityManager entityManager;

	@Async
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
				if(securityLog.getOperationDateBegin() != null && securityLog.getOperationDateEnd() != null) {
					list.add(criteriaBuilder.between(root.get("createTime").as(Date.class), securityLog.getOperationDateBegin(),securityLog.getOperationDateEnd()));
				}
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("updateTime")));
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
	public List<SecurityLog> getSecurityLogList(String operationDateBegin,String operationDateEnd) {
		return securityLogRepository.getSecurityLogList(operationDateBegin,operationDateEnd);
	}


	@Override
	public List getStatisticsSecurityLog() {
		Query query = entityManager.createNativeQuery("select t.clbmmc aqsjcllxmc,COUNT(t.clbmmc) cou,(select count(clbm) from tc_security_logs) allCount from tc_security_logs t group  by t.clbmmc");
				//"select c.aqsjcllxmc,sum(c.cou) cou,c.allCount from ( select clbm ,count(clbm) cou,sap.aqsjcllxmc ,(select count(clbm) from tc_security_logs) allCount from tc_security_logs left join tm_saps sap on clbm = sap.aqsjclbm group by clbm,sap.aqsjcllxmc) c group  by c.aqsjcllxmc,c.allCount");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.getResultList();

		return list;
	}

}
