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

import com.xs.jt.base.module.common.Common;
import com.xs.jt.base.module.dao.RoleRepository;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.manager.IRoleManager;



@Service("roleManager")
public class RoleManagerImpl implements IRoleManager {

	//@Resource(name = "sysHibernateTemplate")
//	private HibernateTemplate hibernateTemplate;
	@Autowired
	private RoleRepository roleRepository;

	/**
	public List<Role> getRoleList(final Role role, final Integer page, final Integer rows) {

		return hibernateTemplate.execute(new HibernateCallback<List<Role>>() {

			
			public List<Role> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From Role where 1=1");

				List params = new ArrayList();

				if (role != null) {
					if (role.getJsmc() != null && !"".equals(role.getJsmc())) {
						sql.append(" and  jsmc like ?");
						params.add("%" + role.getJsmc() + "%");
					}

					if (role.getJslx() != null) {
						sql.append(" and  jslx = ?");
						params.add(role.getJslx());
					}

					if (role.getJsjb() != null) {
						sql.append(" and jsjb = ?");
						params.add(role.getJsjb());
					}
				}
				Query query = session.createQuery(sql.toString());

				if (page != null && rows != null) {
					query.setFirstResult((page - 1) * rows);
					query.setMaxResults(rows);
				}

				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				List<Role> roles = (List<Role>) query.list();
				return roles;
			}
		});
	}

	
	public Integer getRoleCount(final Role role) {

		Integer count = hibernateTemplate.execute(new HibernateCallback<Integer>() {

			
			public Integer doInHibernate(Session session) throws HibernateException {

				StringBuffer sql = new StringBuffer("Select count(id) From Role  where 1=1");

				List params = new ArrayList();

				if (role != null) {
					if (role.getJsmc() != null && !"".equals(role.getJsmc())) {
						sql.append(" and  jsmc like ?");
						params.add("%" + role.getJsmc() + "%");
					}

					if (role.getJslx() != null) {
						sql.append(" and  jslx = ?");
						params.add(role.getJslx());
					}

					if (role.getJsjb() != null) {
						sql.append(" and jsjb = ?");
						params.add(role.getJsjb());
					}
				}

				int i = 0;
				Query query = session.createQuery(sql.toString());
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}
				Long count = (Long) query.uniqueResult();
				return count.intValue();
			}

		});

		return count;
	}
	**/
	
	public Role getRoleById(Integer id) {

//		StringBuffer sql = new StringBuffer("From Role where id=?");
//
//		List<Role> roles = (List<Role>) this.hibernateTemplate.find(sql.toString(), id);
//
//		if (roles != null && !roles.isEmpty()) {
//			return roles.get(0);
//		} else {
//			return null;
//		}
		
		Role role = this.roleRepository.findById(id).get();
		return role;
	}

	
	public Role getRole(String roleName) {

//		StringBuffer sql = new StringBuffer("From role where jsmc=?");
//
//		List<Role> roles = (List<Role>) this.hibernateTemplate.find(sql.toString(), roleName);
//
//		if (roles != null && !roles.isEmpty()) {
//			return roles.get(0);
//		} else {
//			return null;
//		}
		Role role = this.roleRepository.findByJsmc(roleName);
		return role;
	}

	
	public Role add(Role role) {
		role = this.roleRepository.save(role);
//		Serializable id = hibernateTemplate.save(role);
//		role.setId(Integer.parseInt(id.toString()));
		return role;
	}

	
	public Role save(Role role) throws Exception {
		if (role.getJslx() == 0) {
			throw new Exception("系统超级管理员无法修改！");
		}
		return this.roleRepository.save(role);
//		return hibernateTemplate.merge(role);
	}

	
	public void delete(Integer id) throws Exception {
		Role role = this.roleRepository.findById(id).get();//this.hibernateTemplate.load(Role.class, id);

		if (role.getJslx() == 0) {
			throw new Exception("系统超级管理员无法删除");
		} else {
			this.roleRepository.delete(role);
//			hibernateTemplate.delete(role);
		}

	}

	
	public Role getSystemRole() {

//		DetachedCriteria dc = DetachedCriteria.forClass(Role.class);
//
//		dc.add(Restrictions.eq("jsjb", 0));
//		dc.add(Restrictions.eq("jslx", 0));
//
//		List<Role> roles = (List<Role>) this.hibernateTemplate.findByCriteria(dc);
		
		List<Role> resultList = null;
        Specification querySpecifi = new Specification<Role>() {
           
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("jsjb"), 0));
                predicates.add(criteriaBuilder.equal(root.get("jslx"), 0));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        resultList =  this.roleRepository.findAll(querySpecifi);

		return resultList.get(0);
	}

	/**public void addMenuToMap(Map<String, String> menus, String ids) {
		if (ids != null) {
			JSONArray jss = JSONArray.fromObject(ids);
			for (int i = 0; i < jss.size(); i++) {
				String id = jss.getString(i);
				Role role = this.getRoleById(Integer.valueOf(id));
				if (role != null && role.getJsqx() != null) {
					addMenuToMap(menus, role.getJsqx().split(","));
				}
			}
		}
	}
	
	public void addMenuToPower(Map<String, String> menus, List<Power> power){
		for (Power p : power) {
			menus.put(p.getKey(),p.getKey());	
		}
	}

	public void addMenuToMap(Map<String, String> menus, Object[] ms) {
		for (Object m : ms) {
			menus.put(m.toString(), m.toString());	
		}
	}

	public List<Power> transformationPowers(Map<String, String> menus, List<Power> powers){
		List<Power> ps=new ArrayList<Power>();
		for(Power p:powers){
			if(menus.containsKey(p.getKey())){
				ps.add(p);
			}
		}
		return ps;
	}
	
	public  Map<String,ModulePower> transformationModules(Map<String, String> menus, List<ModulePower> modules) {
		 Map<String,ModulePower>  temp = new HashMap<String,ModulePower>();
		for (String mune : menus.values()) {
			for (ModulePower p : modules) {
				if (p.isExitMenu(mune)) {
					temp.put(p.getModule()+"_"+p.getApp(), p);
					break;
				}
			}
		}
		return temp;
	}**/

	
	public List<Role> getAllRoles() {
		return this.roleRepository.findAll();
//		return (List<Role>)this.hibernateTemplate.find(" from Role", null);
	}


	public Map<String, Object> getRoles(Integer page, Integer rows, final Role role) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<Role> bookPage = roleRepository.findAll(new Specification<Role>() {
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (Common.isNotEmpty(role.getJsmc())) {
					list.add(criteriaBuilder.like(root.get("jsmc").as(String.class), "%" + role.getJsmc() + "%"));
				}

				if (role.getJslx() != null) {
					list.add(criteriaBuilder.equal(root.get("jslx").as(String.class), role.getJslx()));
				}

				if (role.getJsjb() != null) {
					list.add(criteriaBuilder.equal(root.get("jsjb").as(String.class), role.getJsjb()));
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


	public int getRoleCount() {
		return (int)this.roleRepository.count();
	}

}
