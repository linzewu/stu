package com.xs.jt.base.module.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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
import com.xs.jt.base.module.dao.UserRepository;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IDepartmentManager;
import com.xs.jt.base.module.manager.IUserManager;

@Service("userManager")
public class UserManagerImpl implements IUserManager {

	@Resource(name = "departmentManager")
	private IDepartmentManager deptManager;
	
	@Autowired
	private UserRepository userRepository;

	
	public User getUser(String userName) {
		User user = this.userRepository.findByYhm(userName);
		return user;
	}

	
	public User getUser(Integer id) {
		return this.userRepository.findById(id).get();
	}

	
	public User saveUser(User user) {
		user = this.userRepository.save(user);
		return user;
	}

	
	public void deleteUser(User user) {
		this.userRepository.delete(user);
	}


	public boolean isExistUserName(final User user) {		
		
		List<User> resultList = null;
        Specification querySpecifi = new Specification<User>() {
           
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("yhm"), user.getYhm()));
                if(user.getId() != null){
                    predicates.add(criteriaBuilder.notEqual(root.get("id"), user.getId()));
                }
               
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        resultList =  this.userRepository.findAll(querySpecifi);
        return resultList == null || resultList.size() == 0 ? true : false;
	}


	public User login(String userName) {
		User user = this.userRepository.findByYhm(userName);
		return user;
	}

	
	public User queryUser(User user) {
		
		return this.userRepository.findBySfzh(user.getSfzh());
	}


	public Map<String, Object> getUsers(Integer page, Integer rows, final User user) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<User> bookPage = userRepository.findAll(new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (Common.isNotEmpty(user.getYhm())) {
					list.add(criteriaBuilder.like(root.get("yhm").as(String.class), "%" + user.getYhm() + "%"));
				}

				if (Common.isNotEmpty(user.getYhxm())) {
					list.add(criteriaBuilder.like(root.get("yhxm").as(String.class), "%" + user.getYhxm() + "%"));
				}

				if (Common.isNotEmpty(user.getSfzh())) {
					list.add(criteriaBuilder.like(root.get("sfzh").as(String.class), user.getSfzh() + "%"));
				}

				if (Common.isNotEmpty(user.getBmdm())) {

					// 使用seq代替是否查询子级部门 seq =1 查询子级部门
					if (user.getSeq() != null && user.getSeq() == 1) {

						List<String> dms = deptManager.getSubCodes(user.getBmdm());
						if (dms != null && dms.size() > 0) {
							In<String> in = criteriaBuilder.in(root.get("bmdm").as(String.class));
							for (String dm : dms) {
								in.value(dm);
							}
							list.add(in);
						}

					} else {
						list.add(criteriaBuilder.equal(root.get("bmdm").as(String.class), user.getBmdm()));
					}

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


	public int getUserCount() {
		return (int)this.userRepository.count();
	}


	@Override
	public List<User> getUsersExceptAdmin() {
		return userRepository.getUsersExceptAdmin();
	}


	@Override
	public boolean getUserByGH(String gh,Integer id) {
		List<User> resultList = null;
        Specification querySpecifi = new Specification<User>() {
           
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get("gh"), gh));
                if(id != null){
                    predicates.add(criteriaBuilder.notEqual(root.get("id"), id));
                }
               
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        resultList =  this.userRepository.findAll(querySpecifi);
        return resultList == null || resultList.size() == 0 ? true : false;
	}


	@Override
	public List<User> getUsersByZjdlsj(int days) {
		return this.userRepository.getUsersByZjdlsj(days);
	}
}
