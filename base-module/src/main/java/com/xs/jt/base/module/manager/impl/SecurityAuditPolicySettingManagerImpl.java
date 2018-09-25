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
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.SecurityAuditPolicySettingRepository;
import com.xs.jt.base.module.entity.SecurityAuditPolicySetting;
import com.xs.jt.base.module.manager.ISecurityAuditPolicySettingManager;

@Service("securityAuditPolicySettingManager")
public class SecurityAuditPolicySettingManagerImpl implements ISecurityAuditPolicySettingManager {

	//@Resource(name = "sysHibernateTemplate")
//	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private SecurityAuditPolicySettingRepository securityAuditPolicySettingRepository;
	
	/**public List<SecurityAuditPolicySetting> getList(Integer page, Integer rows,
			SecurityAuditPolicySetting securityAuditPolicySetting) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityAuditPolicySetting.class);

		Integer firstResult = (page - 1) * rows;
		if(securityAuditPolicySetting.getAqsjcllxmc()!=null&&!"".equals(securityAuditPolicySetting.getAqsjcllxmc().trim())){
			query.add(Restrictions.eq("aqsjcllxmc", securityAuditPolicySetting.getAqsjcllxmc()));
		}
		
		List<SecurityAuditPolicySetting> vcps = (List<SecurityAuditPolicySetting>) this.hibernateTemplate.findByCriteria(query, firstResult,
				rows);

		return vcps;
	}

	
	public Integer getListCount(Integer page, Integer rows, SecurityAuditPolicySetting securityAuditPolicySetting) {
		DetachedCriteria query = DetachedCriteria.forClass(SecurityAuditPolicySetting.class);

		query.setProjection(Projections.rowCount());
		if(securityAuditPolicySetting.getAqsjcllxmc()!=null&&!"".equals(securityAuditPolicySetting.getAqsjcllxmc().trim())){
			query.add(Restrictions.eq("aqsjcllxmc", securityAuditPolicySetting.getAqsjcllxmc()));
		}
		List<Long> count = (List<Long>) hibernateTemplate.findByCriteria(query);

		return count.get(0).intValue();
	}**/

	
	public void updateSecurityAuditPolicySetting(List<SecurityAuditPolicySetting> list) {
		for(SecurityAuditPolicySetting vo:list) {
			//this.hibernateTemplate.update(vo);
			securityAuditPolicySettingRepository.save(vo);
		}
	}

	
	public SecurityAuditPolicySetting getPolicyByCode(String aqsjclbm) {
//		StringBuffer sb=new StringBuffer("from SecurityAuditPolicySetting where aqsjclbm=? AND sfkq = '0'");
//		
//		List<SecurityAuditPolicySetting> sets=(List<SecurityAuditPolicySetting> )this.hibernateTemplate.find(sb.toString(), aqsjclbm);
//		
//		return sets==null||sets.size()==0?null:sets.get(0);
		SecurityAuditPolicySetting setting = this.securityAuditPolicySettingRepository.findByAqsjclbm(aqsjclbm);
		return setting;
	}


	public Map<String, Object> getSecurityAuditPolicySettings(Integer page, Integer rows,
			final SecurityAuditPolicySetting setting) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<SecurityAuditPolicySetting> bookPage = securityAuditPolicySettingRepository.findAll(new Specification<SecurityAuditPolicySetting>() {
			public Predicate toPredicate(Root<SecurityAuditPolicySetting> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(setting.getAqsjcllxmc()!=null&&!"".equals(setting.getAqsjcllxmc().trim())){
					list.add(criteriaBuilder.equal(root.get("aqsjcllxmc").as(String.class), setting.getAqsjcllxmc()));
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

}
