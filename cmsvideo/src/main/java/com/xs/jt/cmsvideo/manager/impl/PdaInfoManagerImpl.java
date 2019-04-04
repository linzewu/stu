package com.xs.jt.cmsvideo.manager.impl;

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
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.cmsvideo.dao.PdaInfoRepository;
import com.xs.jt.cmsvideo.entity.PdaInfo;
import com.xs.jt.cmsvideo.manager.IPdaInfoManager;
@Service
public class PdaInfoManagerImpl implements IPdaInfoManager {
	
	@Autowired
	private PdaInfoRepository pdaInfoRepository;

	@Override
	public Map<String, Object> getPdaInfoList(Integer page, Integer rows, PdaInfo pdaInfo) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<PdaInfo> bookPage = pdaInfoRepository.findAll(new Specification<PdaInfo>() {
			public Predicate toPredicate(Root<PdaInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(pdaInfo.getUseDept())) {
					list.add(criteriaBuilder.equal(root.get("useDept").as(String.class), pdaInfo.getUseDept()));
				}
							
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
	public void savePdaInfo(PdaInfo pdaInfo) {
		
		if(pdaInfo.getId() != null) {
			PdaInfo info = this.pdaInfoRepository.findById(pdaInfo.getId()).get();
			pdaInfo.setSerialCode(info.getSerialCode());
		}
		pdaInfoRepository.save(pdaInfo);
		
	}

	@Override
	public void deletePdaInfo(PdaInfo pdaInfo) {
		pdaInfoRepository.delete(pdaInfo);
		
	}

	@Override
	public PdaInfo getPdaInfoBySerialCode(String serialCode) {
		PdaInfo pdaInfo = pdaInfoRepository.getPdaInfoBySerialCode(serialCode);		
		return pdaInfo;
	}

}
