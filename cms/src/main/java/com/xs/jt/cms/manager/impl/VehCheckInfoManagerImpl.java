package com.xs.jt.cms.manager.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.aspose.words.Document;
import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.cms.dao.VehCheckInfoRepository;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.manager.IVehCheckInfoManager;

import net.sf.json.JSONObject;

@Service("policeCheckInfoManager")
public class VehCheckInfoManagerImpl implements IVehCheckInfoManager {

	@Autowired
	private VehCheckInfoRepository vehCheckInfoRepository;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private ServletContext servletContext;
	
	public VehCheckInfo save(VehCheckInfo vehCheckInfo) {
		return vehCheckInfoRepository.save(vehCheckInfo);
	}

	public List<VehCheckInfo> findPoliceCheckInfoByLsh(String lsh) {
		return vehCheckInfoRepository.findVehCheckInfoByLsh(lsh);
	}

	public Map<String, Object> getVehCheckInfoList(Integer page, Integer rows,final VehCheckInfo vehCheckInfo) {
		
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VehCheckInfo> bookPage = vehCheckInfoRepository.findAll(new Specification<VehCheckInfo>() {
			public Predicate toPredicate(Root<VehCheckInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(vehCheckInfo.getClsbdh())) {
					list.add(criteriaBuilder.like(root.get("clsbdh").as(String.class), "%"+ vehCheckInfo.getClsbdh()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getCyr())) {
					list.add(criteriaBuilder.equal(root.get("cyr").as(String.class), vehCheckInfo.getCyr()));
				}
				if(vehCheckInfo.getCysj() != null && vehCheckInfo.getCysjEnd() != null) {
					list.add(criteriaBuilder.between(root.get("cysj").as(Date.class), vehCheckInfo.getCysj(),vehCheckInfo.getCysjEnd()));
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

	public String getVehCheckReport(Integer id) throws Exception {
		
		Optional<VehCheckInfo> vehCheckInfo = this.vehCheckInfoRepository.findById(id);
		
		JSONObject data = JSONObject.fromObject(vehCheckInfo.get());
		
		Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
		
		Document doc = Sql2WordUtil.map2WordUtil("template_ptc.doc", data,bpsMap);
		
		return Sql2WordUtil.toCase(doc, cacheDir, "\\report\\template_ptc_01"+id+".jpg");
	}

	public VehCheckInfo findBhgVehCheckInfoByLsh(String lsh) {
		return this.vehCheckInfoRepository.findBhgVehCheckInfoByLsh(lsh);
	}

	public VehCheckInfo findBhgVehCheckInfoByHphmHpzl(String hphm, String hpzl) {
		
		return this.vehCheckInfoRepository.findBhgVehCheckInfoByHphmHpzl(hphm, hpzl);
	}

	public Integer findMaxCsByLsh(String lsh) {
		return vehCheckInfoRepository.findMaxCsByLsh(lsh);
	}

	public VehCheckInfo findVehCheckInfoByLshAndCycs(String lsh, int cycs) {
		return vehCheckInfoRepository.findVehCheckInfoByLshAndCycs(lsh, cycs);
	}

}
