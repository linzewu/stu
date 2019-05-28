package com.xs.jt.cmsvideo.manager.impl;

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

import com.xs.jt.base.module.common.Common;
import com.xs.jt.cmsvideo.dao.PreCarRegisterRepository;
import com.xs.jt.cmsvideo.entity.PreCarRegister;
import com.xs.jt.cmsvideo.manager.IPreCarRegisterManager;
@Service("preCarRegisterManager")
public class PreCarRegisterManagerImpl implements IPreCarRegisterManager {
	
	@Autowired
	private PreCarRegisterRepository preCarRegisterRepository;

	public PreCarRegister save(PreCarRegister preCarRegister) {
		return this.preCarRegisterRepository.save(preCarRegister);
	}

	public Map<String, Object> getPreCarRegisters(Integer page, Integer rows, final PreCarRegister preCarRegister) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<PreCarRegister> bookPage = preCarRegisterRepository.findAll(new Specification<PreCarRegister>() {
			public Predicate toPredicate(Root<PreCarRegister> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(preCarRegister.getClsbdh())) {
					list.add(criteriaBuilder.like(root.get("clsbdh").as(String.class), "%"+ preCarRegister.getClsbdh()));
				}
				if(Common.isNotEmpty(preCarRegister.getSfz())) {
					list.add(criteriaBuilder.equal(root.get("sfz").as(String.class),  preCarRegister.getSfz()));
				}
				if(Common.isNotEmpty(preCarRegister.getSyr())) {
					list.add(criteriaBuilder.equal(root.get("syr").as(String.class),  preCarRegister.getSyr()));
				}
				//list.add(criteriaBuilder.equal(root.get("stationCode").as(String.class), "Y"));
				
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.desc(root.get("createTime")));
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

	public PreCarRegister findPreCarRegisterByLsh(String lsh) {
		
		return preCarRegisterRepository.findPreCarRegisterByLsh(lsh);
	}

}
