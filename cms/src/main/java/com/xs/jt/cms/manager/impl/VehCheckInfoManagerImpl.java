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
		VehCheckInfo vci = this.findVehCheckInfoByLshAndCycs(vehCheckInfo.getLsh(),
				vehCheckInfo.getCycs());
		if(vci != null) {
			vehCheckInfo.setId(vci.getId());
			vehCheckInfo.setSfdy(vci.getSfdy());
		}else {
			vehCheckInfo.setSfdy("N");
		}
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
					list.add(criteriaBuilder.like(root.get("clsbdh").as(String.class), "%"+ vehCheckInfo.getClsbdh()+"%"));
				}
				if(Common.isNotEmpty(vehCheckInfo.getLsh())) {
					list.add(criteriaBuilder.like(root.get("lsh").as(String.class), "%"+ vehCheckInfo.getLsh()+"%"));
				}
				if(Common.isNotEmpty(vehCheckInfo.getCyrName())) {
					list.add(criteriaBuilder.like(root.get("cyrName").as(String.class), "%"+vehCheckInfo.getCyrName()+"%"));
				}
				if(vehCheckInfo.getCysj() != null && vehCheckInfo.getCysjEnd() != null) {
					list.add(criteriaBuilder.between(root.get("cysj").as(Date.class), vehCheckInfo.getCysj(),vehCheckInfo.getCysjEnd()));
				}
				//list.add(criteriaBuilder.equal(root.get("stationCode").as(String.class), "Y"));
				if(Common.isNotEmpty(vehCheckInfo.getCyjg())) {
					list.add(criteriaBuilder.equal(root.get("cyjg").as(String.class), vehCheckInfo.getCyjg()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getYwlx())) {
					list.add(criteriaBuilder.equal(root.get("ywlx").as(String.class), vehCheckInfo.getYwlx()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getHpzl())) {
					list.add(criteriaBuilder.equal(root.get("hpzl").as(String.class), vehCheckInfo.getHpzl()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getCllx())) {
					list.add(criteriaBuilder.equal(root.get("cllx").as(String.class), vehCheckInfo.getCllx()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getSyxz())) {
					list.add(criteriaBuilder.equal(root.get("syxz").as(String.class), vehCheckInfo.getSyxz()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getHphm())) {
					list.add(criteriaBuilder.like(root.get("hphm").as(String.class), "%"+vehCheckInfo.getHphm()+"%"));
				}
				if(Common.isNotEmpty(vehCheckInfo.getJg())) {
					list.add(criteriaBuilder.equal(root.get("jg").as(String.class), vehCheckInfo.getJg()));
				}
				if(Common.isNotEmpty(vehCheckInfo.getSfdy())) {
					/**if("Y".equals(vehCheckInfo.getSfdy())) {
						list.add(criteriaBuilder.equal(root.get("sfdy").as(String.class), vehCheckInfo.getSfdy()));
					}else {
						Predicate pre = criteriaBuilder.or(criteriaBuilder.isNull(root.get("sfdy").as(String.class)));
						pre = criteriaBuilder.or(pre, criteriaBuilder.equal(root.get("sfdy").as(String.class), vehCheckInfo.getSfdy()));
						list.add(pre);
					}**/
					list.add(criteriaBuilder.equal(root.get("sfdy").as(String.class), vehCheckInfo.getSfdy()));
				}
				//判断是否是自动出单 ,是的话查询合格的查验单
				if("1".equals(vehCheckInfo.getSfzddy())) {
					list.add(criteriaBuilder.equal(root.get("cyjg").as(String.class), "1"));
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
