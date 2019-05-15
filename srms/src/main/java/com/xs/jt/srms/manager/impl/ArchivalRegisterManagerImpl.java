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
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IArchivalRegisterManager;
import com.xs.jt.srms.util.NumberFormatUtil;
@Service
@Scope("singleton")
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
	public synchronized boolean archivalCheckOut(ArchivalCase archivalCase) {
		List<ArchivalCase> list = this.archivalCaseRepository.getArchivalCaseByBarCode(archivalCase.getBarCode());
		String fileNoStr = "";
		if("Y".equals(archivalCase.getZyOther())) {
			if(!CollectionUtils.isEmpty(list)) {
				if(list.size() == 1) {
					//一条
					ArchivalCase noUseCase = archivalCaseRepository.findNoUseArchivalCase(StoreRoom.CFLB_GH, ArchivalCase.ZT_WSY);
					noUseCase.setClsbdh(list.get(0).getClsbdh());
					noUseCase.setHphm(list.get(0).getHphm());
					noUseCase.setHpzl(list.get(0).getHpzl());
					noUseCase.setYwlx(archivalCase.getYwlx());
//					noUseCase.setBarCode(noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
//							+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo());
					noUseCase.setBarCode(noUseCase.getArchivesNo() + noUseCase.getRackNo()
							+ (noUseCase.getRackCol() < 10 ? ("0" + noUseCase.getRackCol()) : noUseCase.getRackCol())
							+ (noUseCase.getRackRow() < 10 ? ("0" + noUseCase.getRackRow()) : noUseCase.getRackRow())
							+ noUseCase.getFileNo());
					noUseCase.setZt(ArchivalCase.ZT_CK);
					archivalCaseRepository.save(noUseCase);	
					
					noUseCase.setReason(archivalCase.getReason());
					noUseCase.setRemark(archivalCase.getRemark());
					addArchivalRegister(noUseCase,ArchivalCase.ZT_CK,noUseCase.getFileNo());
					
				}else if(list.size() >1) {
					//多条转到其他档案架
					archivalCase.setCaseNumber(list.size());
					ArchivalCase caseOne = this.customRepository.findNoUseMultiOtherArchivalCase(archivalCase);
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
						
						ArchivalCase registerCase = null;
						int cou = 0;
						for(ArchivalCase noUseCase:caseList) {
							noUseCase.setClsbdh(archivalCase.getClsbdh());
							noUseCase.setHphm(archivalCase.getHphm());
							noUseCase.setHpzl(archivalCase.getHpzl());
							noUseCase.setYwlx(archivalCase.getYwlx());
							if("".equals(barCode)) {
//								barCode = noUseCase.getArchivesNo() + "-" + noUseCase.getHpzl() + "-" + noUseCase.getRackNo() + "-"
//										+ noUseCase.getRackCol() + "-" + noUseCase.getRackRow() + "-" + noUseCase.getFileNo();
								barCode = noUseCase.getArchivesNo() + noUseCase.getRackNo()
								+ (noUseCase.getRackCol() < 10 ? ("0" + noUseCase.getRackCol()) : noUseCase.getRackCol())
								+ (noUseCase.getRackRow() < 10 ? ("0" + noUseCase.getRackRow()) : noUseCase.getRackRow())
								+ noUseCase.getFileNo();
							}
							noUseCase.setBarCode(barCode);
							noUseCase.setZt(ArchivalCase.ZT_CK);
							archivalCaseRepository.save(noUseCase);	
							fileNoStr = "".equals(fileNoStr) ? noUseCase.getFileNo():(fileNoStr+","+noUseCase.getFileNo());
							if(cou == 0) {
								registerCase = noUseCase;
							}
							cou++;
							
						}
						
						registerCase.setReason(archivalCase.getReason());
						registerCase.setRemark(archivalCase.getRemark());
						addArchivalRegister(registerCase,ArchivalCase.ZT_CK,fileNoStr);
					}
				}
				//调整后原来的格子置为未使用
//				List<ArchivalCase> originCaseList = this.archivalCaseRepository.getArchivalCaseByBarCode(archivalCase.getBarCode());
				for(ArchivalCase originCase:list) {
					originCase.setZt(ArchivalCase.ZT_WSY);
					originCase.setClsbdh("");
					originCase.setHphm("");
					originCase.setHpzl("");
					originCase.setYwlx("");
					originCase.setBarCode("");
					archivalCaseRepository.save(originCase);
				}
			}
		}else {
		
			for(ArchivalCase ac:list) {
				ac.setZt(ArchivalCase.ZT_CK);
				archivalCaseRepository.save(ac);
				fileNoStr = "".equals(fileNoStr) ? ac.getFileNo():(fileNoStr+","+ac.getFileNo());
			}
			if(!CollectionUtils.isEmpty(list)) {
				addArchivalRegister(archivalCase,ArchivalCase.ZT_CK,fileNoStr);
			}
		}
		return true;
	}

	@Override
	public boolean archivalCheckIn(ArchivalCase archivalCase) {
		List<ArchivalCase> list = this.archivalCaseRepository.getArchivalCaseByBarCode(archivalCase.getBarCode());
		String fileNoStr = "";
		
		for(ArchivalCase ac:list) {
			ac.setZt(ArchivalCase.ZT_RK);
			archivalCaseRepository.save(ac);
			fileNoStr = "".equals(fileNoStr) ? ac.getFileNo():(fileNoStr+","+ac.getFileNo());
		}
		if(!CollectionUtils.isEmpty(list)) {
			addArchivalRegister(archivalCase,ArchivalCase.ZT_RK,fileNoStr);
		}
		return true;
	}

	private void addArchivalRegister(ArchivalCase archivalCase,String zt,String fileNoStr) {
		User user = (User) session.getAttribute("user");
//		for(ArchivalCase archivalCase:archivalCaseList) {
			ArchivalRegister archivalRegister = new ArchivalRegister();
			archivalRegister.setArchivesNo(archivalCase.getArchivesNo());
			archivalRegister.setRackNo(archivalCase.getRackNo());
			archivalRegister.setRackCol(archivalCase.getRackCol());
			archivalRegister.setRackRow(archivalCase.getRackRow());
			archivalRegister.setFileNo(fileNoStr);
			
			archivalRegister.setZt(zt);
			archivalRegister.setBarCode(archivalCase.getBarCode());
			archivalRegister.setClsbdh(archivalCase.getClsbdh());
			archivalRegister.setHandleUser(user.getYhm());
			archivalRegister.setHphm(archivalCase.getHphm());
			archivalRegister.setHpzl(archivalCase.getHpzl());
			archivalRegister.setReason(archivalCase.getReason());
			archivalRegister.setRemark(archivalCase.getRemark());
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
					warn.setFileNo(fileNoStr);
					warn.setDescribe("条码为"+archivalCase.getBarCode()+"的档案，在"+jo.get("days").toString()+"天内办理了"+blcs+"次出入库");
					warn.setWarnType(ArchivalWarn.YJLX_DCBL);
					warn.setWarnDate(new Date());
					archivalWarnRepository.save(warn);
				}
			}
//		}
		
	}

	@Override
	public List<ArchivalRegister> findArchivalRegisterCheckIn(String handleUser, String zt,String rksj) {
		return archivalRegisterRepository.findArchivalRegisterCheckIn(handleUser, zt,rksj);
	}


}
