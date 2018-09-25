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
//import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.BlackListRepository;
import com.xs.jt.base.module.entity.BlackList;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBlackListManager;

@Service("blackListManager")
public class BlackListManagerImpl implements IBlackListManager {

	//@Resource(name = "sysHibernateTemplate")
//	private HibernateTemplate hibernateTemplate;
	@Autowired
	private BlackListRepository blackListRepository;
	//@Override
	public void saveBlackList(BlackList blackList) {
		blackListRepository.save(blackList);
//		this.hibernateTemplate.saveOrUpdate(blackList);
	}

	//@Override
	public void deleteBlackList(BlackList blackList) {
		blackListRepository.delete(blackList);
//		this.hibernateTemplate.delete(blackList);

	}

	//@Override
	public void deleteSystemBlackList() {
//		List<BlackList> blackList = (List<BlackList>) hibernateTemplate.find("from BlackList where createBy=?",User.SYSTEM_USER);
//		hibernateTemplate.deleteAll(blackList);
		blackListRepository.deleteAll();
	}

	//@Override
	public BlackList getBlackListByIp(String ip) {
//		BlackList blackList = this.hibernateTemplate.get(BlackList.class, ip);
//		return blackList;
		return blackListRepository.findBlackListByIp(ip);
	}

	//@Override
	public boolean checkIpIsBan(String ip) {
		System.out.println(ip);
		BlackList blackList = blackListRepository.findBlackListByIp(ip);//this.hibernateTemplate.get(BlackList.class, ip);
		if(blackList!=null) {
			//阈值取阈值表
			if("Y".equals(blackList.getEnableFlag())||!User.SYSTEM_USER.equals(blackList.getCreateBy())) {
				System.out.println("true");
				return true;
			}
		}
		return false;
	}

	//@Override
//	public List<BlackList> getList(Integer page, Integer rows, BlackList blackList) {
//		DetachedCriteria query = DetachedCriteria.forClass(BlackList.class);
//
//		Integer firstResult = (page - 1) * rows;
//		query.add(Restrictions.eq("enableFlag", "Y"));
//		
//		List<BlackList> vcps = (List<BlackList>) this.hibernateTemplate.findByCriteria(query, firstResult,
//				rows);
//
//		return vcps;
//	}

	//@Override
//	public Integer getListCount(Integer page, Integer rows, BlackList blackList) {
//		DetachedCriteria query = DetachedCriteria.forClass(BlackList.class);
//
//		query.setProjection(Projections.rowCount());
//		
//		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);
//
//		return count.get(0).intValue();
//	}

	//@Override
	public List<BlackList> getEnableList() {
		return blackListRepository.findBlackListByEnableFlag("Y");//(List<BlackList>)this.hibernateTemplate.find(" from BlackList where enableFlag = 'Y'", null);
	}

	public Map<String, Object> getBlackLists(Integer page, Integer rows, BlackList blackList) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<BlackList> bookPage = blackListRepository.findAll(new Specification<BlackList>() {
			public Predicate toPredicate(Root<BlackList> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();				
				list.add(criteriaBuilder.equal(root.get("enableFlag").as(String.class), "Y"));
				
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
