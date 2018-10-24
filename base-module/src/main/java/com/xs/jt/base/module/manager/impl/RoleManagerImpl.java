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

	@Autowired
	private RoleRepository roleRepository;
	
	public Role getRoleById(Integer id) {
		
		Role role = this.roleRepository.findById(id).get();
		return role;
	}

	
	public Role getRole(String roleName) {
		Role role = this.roleRepository.findByJsmc(roleName);
		return role;
	}

	
	public Role add(Role role) {
		role = this.roleRepository.save(role);
		return role;
	}

	
	public Role save(Role role) throws Exception {
		if (role.getJslx() == 0) {
			throw new Exception("系统超级管理员无法修改！");
		}
		return this.roleRepository.save(role);
	}

	
	public void delete(Integer id) throws Exception {
		Role role = this.roleRepository.findById(id).get();//this.hibernateTemplate.load(Role.class, id);

		if (role.getJslx() == 0) {
			throw new Exception("系统超级管理员无法删除");
		} else {
			this.roleRepository.delete(role);
		}

	}

	
	public Role getSystemRole() {
		
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

	
	public List<Role> getAllRoles() {
		return this.roleRepository.findAll();
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
