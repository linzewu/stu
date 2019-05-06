package com.xs.jt.srms.manager.impl;

import java.util.ArrayList;
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
import org.springframework.util.CollectionUtils;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.srms.dao.ArchivalCaseRepository;
import com.xs.jt.srms.dao.ArchivalRegisterRepository;
import com.xs.jt.srms.dao.CustomRepository;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.util.NumberFormatUtil;
@Service
public class ArchivalCaseManagerImpl implements IArchivalCaseManager {
	
	@Autowired
	private ArchivalCaseRepository archivalCaseRepository;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ArchivalRegisterRepository archivalRegisterRepository;
	@Autowired
	private CustomRepository customRepository;

	@Override
	public Map<String, Object> getArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<ArchivalCase> archivalCasePage = archivalCaseRepository.findAll(new Specification<ArchivalCase>() {
			public Predicate toPredicate(Root<ArchivalCase> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(archivalCase.getHphm())) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), archivalCase.getHphm()));
				}
				if(Common.isNotEmpty(archivalCase.getClsbdh())) {
					list.add(criteriaBuilder.equal(root.get("clsbdh").as(String.class), archivalCase.getClsbdh()));
				}
				if(Common.isNotEmpty(archivalCase.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class), archivalCase.getHpzl()));
				}
				if(Common.isNotEmpty(archivalCase.getZt())) {
					list.add(criteriaBuilder.equal(root.get("zt").as(String.class), archivalCase.getZt()));
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
	
	@Override
	public Map<String, Object> getUsedArchivalCaseList(Integer page, Integer rows, ArchivalCase archivalCase) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<ArchivalCase> archivalCasePage = archivalCaseRepository.findAll(new Specification<ArchivalCase>() {
			public Predicate toPredicate(Root<ArchivalCase> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(archivalCase.getHphm())) {
					list.add(criteriaBuilder.equal(root.get("hphm").as(String.class), archivalCase.getHphm()));
				}
				if(Common.isNotEmpty(archivalCase.getClsbdh())) {
					list.add(criteriaBuilder.equal(root.get("clsbdh").as(String.class), archivalCase.getClsbdh()));
				}
				if(Common.isNotEmpty(archivalCase.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class), archivalCase.getHpzl()));
				}
				if(Common.isNotEmpty(archivalCase.getZt())) {
					list.add(criteriaBuilder.notEqual(root.get("zt").as(String.class), archivalCase.getZt()));
				}
				if(Common.isNotEmpty(archivalCase.getRackNo())) {
					list.add(criteriaBuilder.equal(root.get("rackNo").as(String.class), archivalCase.getRackNo()));
				}
				
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.asc(root.get("rackRow")));
				orders.add(criteriaBuilder.asc(root.get("rackCol")));
				orders.add(criteriaBuilder.asc(root.get("fileNo")));
				query.orderBy(orders);
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", archivalCasePage.getContent());
		data.put("total", archivalCasePage.getTotalElements());
		return data;
	}

	@Override
	public void saveArchivalCase(ArchivalCase archivalCase) {
		archivalCaseRepository.save(archivalCase);
		
	}

	@Override
	public void deleteArchivalCase(ArchivalCase archivalCase) {
		archivalCaseRepository.delete(archivalCase);
		
	}

	@Override
	public boolean newCarArchivalCheckIn(ArchivalCase archivalCase) {
		ArchivalCase noUseCase = archivalCaseRepository.findNoUseArchivalCase(StoreRoom.CFLB_XC, ArchivalCase.ZT_WSY);
		noUseCase.setClsbdh(archivalCase.getClsbdh());
		noUseCase.setHphm(archivalCase.getHphm());
		noUseCase.setHpzl(archivalCase.getHpzl());
		noUseCase.setYwlx(archivalCase.getYwlx());
		noUseCase.setBarCode(noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
				+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo());
		noUseCase.setZt(ArchivalCase.ZT_RK);
		archivalCaseRepository.save(noUseCase);	
		saveArchivalRegister(noUseCase);
		return true;
	}
	
	private void saveArchivalRegister(ArchivalCase archivalCase) {
		User user = (User) session.getAttribute("user");
		ArchivalRegister archivalRegister = new ArchivalRegister();
		archivalRegister.setArchivesNo(archivalCase.getArchivesNo());
		archivalRegister.setRackNo(archivalCase.getRackNo());
		archivalRegister.setRackCol(archivalCase.getRackCol());
		archivalRegister.setRackRow(archivalCase.getRackRow());
		archivalRegister.setFileNo(archivalCase.getFileNo());
		archivalRegister.setZt(ArchivalCase.ZT_RK);
		archivalRegister.setBarCode(archivalCase.getBarCode());
		archivalRegister.setClsbdh(archivalCase.getClsbdh());
		archivalRegister.setHandleUser(user.getYhm());
		archivalRegister.setHphm(archivalCase.getHphm());
		archivalRegister.setHpzl(archivalCase.getHpzl());
		archivalRegister.setYwlx(archivalCase.getYwlx());
		archivalRegisterRepository.save(archivalRegister);
	}
	
	private void saveArchivalRegister(ArchivalCase archivalCase,String fileNoStr) {
		User user = (User) session.getAttribute("user");
		ArchivalRegister archivalRegister = new ArchivalRegister();
		archivalRegister.setArchivesNo(archivalCase.getArchivesNo());
		archivalRegister.setRackNo(archivalCase.getRackNo());
		archivalRegister.setRackCol(archivalCase.getRackCol());
		archivalRegister.setRackRow(archivalCase.getRackRow());
		archivalRegister.setFileNo(fileNoStr);
		archivalRegister.setZt(ArchivalCase.ZT_RK);
		archivalRegister.setBarCode(archivalCase.getBarCode());
		archivalRegister.setClsbdh(archivalCase.getClsbdh());
		archivalRegister.setHandleUser(user.getYhm());
		archivalRegister.setHphm(archivalCase.getHphm());
		archivalRegister.setHpzl(archivalCase.getHpzl());
		archivalRegister.setYwlx(archivalCase.getYwlx());
		archivalRegisterRepository.save(archivalRegister);
	}

	@Override
	public boolean UsedCarArchivalCheckIn(ArchivalCase archivalCase) {
		List<ArchivalCase> oldCaseList = archivalCaseRepository.getArchivalCaseByClsbdh(archivalCase.getClsbdh());
		if(!CollectionUtils.isEmpty(oldCaseList)) {
			String fileNoStr = "";
			for(ArchivalCase oldCase:oldCaseList) {
				oldCase.setYwlx(archivalCase.getYwlx());
				oldCase.setZt(ArchivalCase.ZT_RK);
				archivalCaseRepository.save(oldCase);				
				fileNoStr = "".equals(fileNoStr) ? oldCase.getFileNo():(fileNoStr+","+oldCase.getFileNo());
			}
			
			saveArchivalRegister(oldCaseList.get(0),fileNoStr);
		}else {
			ArchivalCase noUseCase = archivalCaseRepository.findNoUseArchivalCase(StoreRoom.CFLB_XC, ArchivalCase.ZT_WSY);
			noUseCase.setClsbdh(archivalCase.getClsbdh());
			noUseCase.setHphm(archivalCase.getHphm());
			noUseCase.setHpzl(archivalCase.getHpzl());
			noUseCase.setYwlx(archivalCase.getYwlx());
			noUseCase.setBarCode(noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
					+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo());
			noUseCase.setZt(ArchivalCase.ZT_RK);
			archivalCaseRepository.save(noUseCase);	
			saveArchivalRegister(noUseCase);
		}	
		
		return true;
	}

	@Override
	public List<ArchivalCase> findUseArchivalCase(String zt, String rackNo) {
		return this.archivalCaseRepository.findUseArchivalCase(zt, rackNo);
	}

	@Override
	public List<Map<String, Object>> findCheckOutLong() {
		return this.customRepository.findCheckOutLong();
	}

	@Override
	public boolean archivalCaseAdjust(ArchivalCase archivalCase) {
		boolean flag = false;
		if(archivalCase.getCaseNumber() == 1) {
			ArchivalCase noUseCase = archivalCaseRepository.findNoUseArchivalCase(StoreRoom.CFLB_XC, ArchivalCase.ZT_WSY);
			noUseCase.setClsbdh(archivalCase.getClsbdh());
			noUseCase.setHphm(archivalCase.getHphm());
			noUseCase.setHpzl(archivalCase.getHpzl());
			noUseCase.setYwlx(archivalCase.getYwlx());
			noUseCase.setBarCode(noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
					+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo());
			noUseCase.setZt(ArchivalCase.ZT_RK);
			archivalCaseRepository.save(noUseCase);	
			saveArchivalRegister(noUseCase);
			flag = true;
		}else if(archivalCase.getCaseNumber() > 1) {
			ArchivalCase caseOne = this.customRepository.findNoUseMultiArchivalCase(archivalCase);
			if(caseOne != null) {
				List<String> fileNoList = new ArrayList<String>();
				String fileNo = caseOne.getFileNo();
				fileNoList.add(caseOne.getFileNo());
				for (int i = 1; i < archivalCase.getCaseNumber(); i++) {
					fileNo = NumberFormatUtil.autoGenericCode(fileNo, 3);
					fileNoList.add(fileNo);
				}
				List<ArchivalCase> caseList = this.archivalCaseRepository.findMultiNoUseArchivalCase(caseOne.getArchivesNo(), caseOne.getRackNo(), caseOne.getRackRow(), caseOne.getRackCol(), fileNoList);
				String barCode = "";
				String fileNoStr = "";
				ArchivalCase registerCase = null;
				int cou = 0;
				for(ArchivalCase noUseCase:caseList) {
					noUseCase.setClsbdh(archivalCase.getClsbdh());
					noUseCase.setHphm(archivalCase.getHphm());
					noUseCase.setHpzl(archivalCase.getHpzl());
					noUseCase.setYwlx(archivalCase.getYwlx());
					if("".equals(barCode)) {
						barCode = noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
								+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo();
					}
					noUseCase.setBarCode(barCode);
					noUseCase.setZt(ArchivalCase.ZT_RK);
					archivalCaseRepository.save(noUseCase);	
					fileNoStr = "".equals(fileNoStr) ? noUseCase.getFileNo():(fileNoStr+","+noUseCase.getFileNo());
					if(cou == 0) {
						registerCase = noUseCase;
					}
					cou++;
					
				}
				saveArchivalRegister(registerCase,fileNoStr);
				flag = true;
			}
		}
		if(flag) {
			//调整后原来的格子置为未使用
			List<ArchivalCase> originCaseList = this.archivalCaseRepository.getArchivalCaseByBarCode(archivalCase.getBarCode());
			for(ArchivalCase originCase:originCaseList) {
				originCase.setZt(ArchivalCase.ZT_WSY);
				originCase.setClsbdh("");
				originCase.setHphm("");
				originCase.setHpzl("");
				originCase.setYwlx("");
				originCase.setBarCode("");
				archivalCaseRepository.save(originCase);
			}
			return true;
		}else {
			return false;
		}
	}

}
