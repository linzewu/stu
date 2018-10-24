package com.xs.jt.base.module.manager.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.xs.jt.base.module.dao.OperationLogRepository;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.manager.IOperationLogManager;

@Service("operationLogManager")
public class OperationLogManagerImpl implements IOperationLogManager {
	@Autowired
	private OperationLogRepository operationLogRepository;
	
	public void saveOperationLog(OperationLog operationLog) {
		this.operationLogRepository.save(operationLog);
	}


	public Map<String, Object> getOperationLogs(Integer page, Integer rows, final OperationLog operationLog) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<OperationLog> bookPage = this.operationLogRepository.findAll(new Specification<OperationLog>() {
			public Predicate toPredicate(Root<OperationLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {				
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != operationLog.getOperationUser() && !"".equals(operationLog.getOperationUser())) {
					list.add(criteriaBuilder.equal(root.get("operationUser").as(String.class), operationLog.getOperationUser()));
				}
				if (null != operationLog.getModule() && !"".equals(operationLog.getModule())) {
					list.add(criteriaBuilder.like(root.get("module").as(String.class), "%"+operationLog.getModule()+"%"));
				}
				if(operationLog.getOperationDate() != null && operationLog.getOperationDateEnd() != null) {
					list.add(criteriaBuilder.between(root.get("operationDate").as(Date.class), operationLog.getOperationDate(),operationLog.getOperationDateEnd()));
				}
//				if(operationLog.getOperationDateEnd() != null) {
//					list.add(Restrictions.le("operationDate", operationLog.getOperationDateEnd()));
//				}
				if(operationLog.getCoreFunction() != null) {
					if("Y".equals(operationLog.getCoreFunction())) {
						//list.add(Restrictions.eq("coreFunction", operationLog.getCoreFunction()));
						list.add(criteriaBuilder.equal(root.get("coreFunction").as(String.class), operationLog.getCoreFunction()));
					}else {
						list.add(criteriaBuilder.isNull(root.get("coreFunction").as(String.class)));
					}
				}

				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("id")));
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


	public Map<String, Object> getLoginOperationLogs(Integer page, Integer rows, final OperationLog operationLog) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<OperationLog> bookPage = this.operationLogRepository.findAll(new Specification<OperationLog>() {
			public Predicate toPredicate(Root<OperationLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {				
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(criteriaBuilder.equal(root.get("operationType").as(String.class), "登录"));
				if (null != operationLog.getOperationUser() && !"".equals(operationLog.getOperationUser())) {
					list.add(criteriaBuilder.equal(root.get("operationUser").as(String.class), operationLog.getOperationUser()));
				}
				if (null != operationLog.getModule() && !"".equals(operationLog.getModule())) {
					list.add(criteriaBuilder.like(root.get("module").as(String.class), "%"+operationLog.getModule()+"%"));
				}
				if(operationLog.getOperationDate() != null && operationLog.getOperationDateEnd() != null) {
					list.add(criteriaBuilder.between(root.get("operationDate").as(Date.class), operationLog.getOperationDate(),operationLog.getOperationDateEnd()));
				}
//				if(operationLog.getOperationDateEnd() != null) {
//					list.add(Restrictions.le("operationDate", operationLog.getOperationDateEnd()));
//				}
				if(operationLog.getCoreFunction() != null) {
					if("Y".equals(operationLog.getCoreFunction())) {
						//list.add(Restrictions.eq("coreFunction", operationLog.getCoreFunction()));
						list.add(criteriaBuilder.equal(root.get("coreFunction").as(String.class), operationLog.getCoreFunction()));
					}else {
						list.add(criteriaBuilder.isNull(root.get("coreFunction").as(String.class)));
					}
				}

				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("id")));
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
