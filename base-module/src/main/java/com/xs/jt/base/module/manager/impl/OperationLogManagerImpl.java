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
	
	//@Resource(name = "sysHibernateTemplate")
//	private HibernateTemplate hibernateTemplate;
	@Autowired
	private OperationLogRepository operationLogRepository;

	
	/**public List<OperationLog> getOperationLog(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		Integer firstResult = (page - 1) * rows;
		setCondition(operationLog, query);
		query.addOrder(Order.desc("id"));
		List<OperationLog> vcps = (List<OperationLog>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	
	public Integer getOperationLogCount(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		query.setProjection(Projections.rowCount());
		setCondition(operationLog, query);
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}

	private void setCondition(OperationLog operationLog, DetachedCriteria query) {
		if(operationLog.getOperationUser()!=null&&!"".equals(operationLog.getOperationUser().trim())){
			query.add(Restrictions.eq("operationUser", operationLog.getOperationUser()));
		}
		if(operationLog.getModule()!=null&&!"".equals(operationLog.getModule().trim())){
			query.add(Restrictions.like("module", "%"+operationLog.getModule()+"%"));
		}
		if(operationLog.getOperationDate() != null) {
			query.add(Restrictions.ge("operationDate", operationLog.getOperationDate()));
		}
		if(operationLog.getOperationDateEnd() != null) {
			query.add(Restrictions.le("operationDate", operationLog.getOperationDateEnd()));
		}
		if(operationLog.getCoreFunction() != null) {
			if("Y".equals(operationLog.getCoreFunction())) {
				query.add(Restrictions.eq("coreFunction", operationLog.getCoreFunction()));
			}else {
				query.add(Restrictions.isNull("coreFunction"));
			}
		}
	}**/

	
	public void saveOperationLog(OperationLog operationLog) {
//		this.hibernateTemplate.save(operationLog);
		this.operationLogRepository.save(operationLog);
	}

	
	/**public List<OperationLog> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		Integer firstResult = (page - 1) * rows;
		setCondition(operationLog, query);
		query.add(Restrictions.eq("operationType", "登录"));
		query.addOrder(Order.desc("id"));
		List<OperationLog> vcps = (List<OperationLog>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	
	public Integer getLoginOperationLogCount(Integer page, Integer rows, OperationLog operationLog) {
		DetachedCriteria query = DetachedCriteria.forClass(OperationLog.class);

		query.setProjection(Projections.rowCount());
		setCondition(operationLog, query);
		query.add(Restrictions.eq("operationType", "登录"));
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}**/


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
