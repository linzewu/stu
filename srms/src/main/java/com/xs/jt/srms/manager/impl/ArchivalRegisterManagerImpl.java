package com.xs.jt.srms.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.srms.dao.ArchivalCaseRepository;
import com.xs.jt.srms.dao.ArchivalRegisterRepository;
import com.xs.jt.srms.dao.ArchivalWarnRepository;
import com.xs.jt.srms.dao.CustomRepository;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;
import com.xs.jt.srms.entity.ArchivalWarn;
import com.xs.jt.srms.manager.IArchivalRegisterManager;
@Service
public class ArchivalRegisterManagerImpl implements IArchivalRegisterManager {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ArchivalRegisterRepository archivalRegisterRepository;
	
	@Autowired
	private ArchivalCaseRepository archivalCaseRepository;
	
	@Autowired
	private CustomRepository customRepository;
	@Autowired
	private IBaseParamsManager baseParamsManager;
	@Autowired
	private ArchivalWarnRepository archivalWarnRepository;

	@Override
	public Map<String, Object> getArchivalRegisterList(Integer page, Integer rows, ArchivalRegister archivalRegister) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<ArchivalRegister> bookPage = archivalRegisterRepository.findAll(new Specification<ArchivalRegister>() {
			public Predicate toPredicate(Root<ArchivalRegister> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(archivalRegister.getZt())) {
					list.add(criteriaBuilder.equal(root.get("zt").as(String.class), archivalRegister.getZt()));
				}
				if(Common.isNotEmpty(archivalRegister.getHphm())) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), archivalRegister.getHphm()));
				}
				if(Common.isNotEmpty(archivalRegister.getClsbdh())) {
					list.add(criteriaBuilder.equal(root.get("clsbdh").as(String.class), archivalRegister.getClsbdh()));
				}
				if(Common.isNotEmpty(archivalRegister.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class), archivalRegister.getHpzl()));
				}
				if(Common.isNotEmpty(archivalRegister.getHandleUser())) {
					list.add(criteriaBuilder.equal(root.get("handleUser").as(String.class), archivalRegister.getHandleUser()));
				}
				if(Common.isNotEmpty(archivalRegister.getCreateTime())) {
					list.add(criteriaBuilder.like(root.get("createTime").as(String.class), archivalRegister.getCreateTime()+"%"));
				}
				
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

	@Override
	public void saveArchivalRegister(ArchivalRegister archivalRegister) {
		archivalRegisterRepository.save(archivalRegister);
		
	}

	@Override
	public void deleteArchivalRegister(ArchivalRegister archivalRegister) {
		archivalRegisterRepository.delete(archivalRegister);
		
	}

	@Override
	public boolean archivalCheckOut(ArchivalCase archivalCase) {
		addArchivalRegister(archivalCase,ArchivalCase.ZT_CK);
		
		archivalCase.setZt(ArchivalCase.ZT_CK);
		archivalCaseRepository.save(archivalCase);
		return true;
	}

	@Override
	public boolean archivalCheckIn(ArchivalCase archivalCase) {
		addArchivalRegister(archivalCase,ArchivalCase.ZT_RK);
		
		archivalCase.setZt(ArchivalCase.ZT_RK);
		archivalCaseRepository.save(archivalCase);
		return true;
	}

	private void addArchivalRegister(ArchivalCase archivalCase,String zt) {
		User user = (User) session.getAttribute("user");
		ArchivalRegister archivalRegister = new ArchivalRegister();
		archivalRegister.setArchivesNo(archivalCase.getArchivesNo());
		archivalRegister.setRackNo(archivalCase.getRackNo());
		archivalRegister.setRackCol(archivalCase.getRackCol());
		archivalRegister.setRackRow(archivalCase.getRackRow());
		archivalRegister.setFileNo(archivalCase.getFileNo());
		
		archivalRegister.setZt(zt);
		archivalRegister.setBarCode(archivalCase.getBarCode());
		archivalRegister.setClsbdh(archivalCase.getClsbdh());
		archivalRegister.setHandleUser(user.getYhm());
		archivalRegister.setHphm(archivalCase.getHphm());
		archivalRegister.setHpzl(archivalCase.getHpzl());
		archivalRegister.setReason(archivalCase.getReason());
		archivalRegister.setYwlx(archivalCase.getYwlx());
		archivalRegisterRepository.save(archivalRegister);
		
		BaseParams bp = baseParamsManager.getBaseParam("yjlx", ArchivalWarn.YJLX_DCBL);
		if(bp != null) {
			String paramValue = bp.getParamValue();
			JSONObject jo = JSONObject.parseObject(paramValue);
			int blcs = this.archivalRegisterRepository.findMultiArchivalRegister(archivalCase.getBarCode(), Integer.parseInt(jo.get("days").toString()));
			if(blcs >= Integer.parseInt(jo.get("blcs").toString())) {
				ArchivalWarn warn = new ArchivalWarn();
				warn.setArchivesNo(archivalCase.getArchivesNo());
				warn.setRackNo(archivalCase.getRackNo());
				warn.setRackCol(archivalCase.getRackCol());
				warn.setRackRow(archivalCase.getRackRow());
				warn.setFileNo(archivalCase.getFileNo());
				warn.setDescribe("条码为"+archivalCase.getBarCode()+"的档案，在"+jo.get("days").toString()+"天内办理了"+blcs+"次出入库");
				warn.setWarnType(ArchivalWarn.YJLX_DCBL);
				warn.setWarnDate(new Date());
				archivalWarnRepository.save(warn);
			}
		}
		
	}


}
