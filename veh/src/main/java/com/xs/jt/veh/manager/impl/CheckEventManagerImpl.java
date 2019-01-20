package com.xs.jt.veh.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.xs.jt.veh.dao.CheckEventRepository;
import com.xs.jt.veh.entity.CheckEvents;
import com.xs.jt.veh.manager.ICheckEventManager;

@Service("checkEventManager")
public class CheckEventManagerImpl implements ICheckEventManager {
	@Autowired
	private CheckEventRepository checkEventRepository;
	
	@Override
	public List<?> getEvents() {
		Pageable pageable = new QPageRequest(0, 100);

		@SuppressWarnings("serial")
		Page<CheckEvents> bookPage = checkEventRepository.findAll(new Specification<CheckEvents>() {
			public Predicate toPredicate(Root<CheckEvents> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();				
				list.add(criteriaBuilder.equal(root.get("state").as(String.class), "0"));
				
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.asc(root.get("id")));
				query.orderBy(orders);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);

		return bookPage.getContent();
	}

	@Override
	public void createEvent(String jylsh, Integer jycs, String event, String jyxm, String hphm, String hpzl,
			String clsbdh, Integer csbj) {
		CheckEvents e = new CheckEvents();
		e.setJylsh(jylsh);
		e.setJycs(jycs);
		e.setEvent(event);
		e.setJyxm(jyxm);
		e.setHphm(hphm);
		e.setHpzl(hpzl);
		e.setClsbdh(clsbdh);
		e.setState(csbj);
		e.setCreateDate(new Date());
		this.checkEventRepository.save(e);
		
	}

	@Override
	public void createEvent(String jylsh, Integer jycs, String event, String jyxm, String hphm, String hpzl,
			String clsbdh, String zpzl, Integer csbj) {
		CheckEvents e = new CheckEvents();
		e.setJylsh(jylsh);
		e.setJycs(jycs);
		e.setEvent(event);
		e.setJyxm(jyxm);
		e.setHphm(hphm);
		e.setHpzl(hpzl);
		e.setClsbdh(clsbdh);
		e.setState(0);
		e.setCreateDate(new Date());
		e.setZpzl(zpzl);
		this.checkEventRepository.save(e);
		
	}

	@Override
	public void resetEventState(String jylsh) {
		checkEventRepository.updateCheckEventsByJylsh(jylsh);
		
	}

	@Override
	public void deleteCheckEvents(CheckEvents checkEvent) {
		checkEventRepository.delete(checkEvent);
		
	}

	@Override
	public void saveCheckEvents(CheckEvents checkEvent) {
		checkEventRepository.save(checkEvent);
		
	}
	
	

}
