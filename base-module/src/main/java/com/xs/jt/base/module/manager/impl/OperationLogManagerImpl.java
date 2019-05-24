package com.xs.jt.base.module.manager.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.dao.OperationLogRepository;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IOperationLogManager;

@Service("operationLogManager")
public class OperationLogManagerImpl implements IOperationLogManager {
	
	public static String[] loginTypes = new String[]{"登录","登出","登录失败"}; 
	@Autowired
	private OperationLogRepository operationLogRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
    @PersistenceContext
    private EntityManager entityManager;
	
	@Async 
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
				
				In<String> in = criteriaBuilder.in(root.get("operationType").as(String.class));
				for (String type : loginTypes) {
					in.value(type);
				}
				list.add(in);
				//list.add(criteriaBuilder.equal(root.get("operationType").as(String.class), "登录"));
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
	
	public void addExceLog(String resultStr,String condition,String operationType,String actionUrl,String jkid,String module) throws DocumentException  {
		Document resultDocument = DocumentHelper.parseText(resultStr);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Element root = resultDocument.getRootElement();
		Element head = root.element("head");
		Element code = head.element("code");
		Element message = head.element("message");
		//失败
		if("0".equals(code.getText())) {
			User loginUser = (User) session.getAttribute("user");
			OperationLog operationLog =new OperationLog();
			operationLog.setOperationResult(OperationLog.OPERATION_RESULT_ERROR);
			operationLog.setStatus(1);
			operationLog.setFailMsg(message.getText());
			operationLog.setOperationUser(loginUser.getYhm());
			operationLog.setOperationCondition(condition);
			operationLog.setOperationDate(new java.util.Date());
			operationLog.setOperationType(operationType);
			operationLog.setIpAddr(Common.getIpAdrress(request));
			operationLog.setActionUrl(actionUrl);
			operationLog.setModule(module);
			operationLog.setContent("用户"+operationLog.getOperationUser()+"在"+sdf.format(new java.util.Date())+"时间,IP为"+operationLog.getIpAddr()+"操作了"+operationLog.getOperationType()+",接口标识："+jkid);
			this.saveOperationLog(operationLog);
		}
	}


	@Override
	public List<OperationLog> getExportList(OperationLog operationLog) {
		List<OperationLog> logList = this.operationLogRepository.findAll(new Specification<OperationLog>() {

			@Override
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
			
		});
		return logList;
	}


	@Override
	public List<OperationLog> getLoginExportList(OperationLog operationLog) {
		List<OperationLog> logList = this.operationLogRepository.findAll(new Specification<OperationLog>() {

			@Override
			public Predicate toPredicate(Root<OperationLog> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				
				In<String> in = criteriaBuilder.in(root.get("operationType").as(String.class));
				for (String type : loginTypes) {
					in.value(type);
				}
				list.add(in);
				//list.add(criteriaBuilder.equal(root.get("operationType").as(String.class), "登录"));
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
			
		});
		return logList;
	}


	@Override
	public List getStatisticsLoginLog() {
		Query query = entityManager.createNativeQuery("SELECT operation_type operationType,COUNT(operation_type) cou,(select count(operation_type) from tc_operation_logs where operation_type in ('登录','登出','登录失败')) allCount FROM tc_operation_logs where operation_type in ('登录','登出','登录失败') group by operation_type");
		//"select c.aqsjcllxmc,sum(c.cou) cou,c.allCount from ( select clbm ,count(clbm) cou,sap.aqsjcllxmc ,(select count(clbm) from tc_security_logs) allCount from tc_security_logs left join tm_saps sap on clbm = sap.aqsjclbm group by clbm,sap.aqsjcllxmc) c group  by c.aqsjcllxmc,c.allCount");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.getResultList();
		
		return list;
	}


	@Override
	public List getStatisticsOperationLog() {
		Query query = entityManager.createNativeQuery("SELECT module,COUNT(module) cou,(select count(module) from tc_operation_logs ) allCount FROM tc_operation_logs  group by module");
		//"select c.aqsjcllxmc,sum(c.cou) cou,c.allCount from ( select clbm ,count(clbm) cou,sap.aqsjcllxmc ,(select count(clbm) from tc_security_logs) allCount from tc_security_logs left join tm_saps sap on clbm = sap.aqsjclbm group by clbm,sap.aqsjcllxmc) c group  by c.aqsjcllxmc,c.allCount");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.getResultList();
		
		return list;
	}

}
