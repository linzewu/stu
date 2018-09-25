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

	//@Resource(name = "sysHibernateTemplate")
//	private HibernateTemplate hibernateTemplate;

	@Resource(name = "departmentManager")
	private IDepartmentManager deptManager;
	
	@Autowired
	private UserRepository userRepository;

	
	/**public List<User> getUsers(final User user, final Integer page, final Integer rows) {

		return hibernateTemplate.execute(new HibernateCallback<List<User>>() {

			
			public List<User> doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("From User where 1=1");

				List params = new ArrayList();

				List<String> dms = null;

				if (user != null) {

					if (Common.isNotEmpty(user.getYhm())) {
						sql.append(" and  yhm like ?");
						params.add("%" + user.getYhm() + "%");
					}

					if (Common.isNotEmpty(user.getYhxm())) {
						sql.append(" and  yhxm like ?");
						params.add("%" + user.getYhxm() + "%");
					}

					if (Common.isNotEmpty(user.getSfzh())) {
						sql.append(" and  sfzh like ?");
						params.add(user.getSfzh() + "%");
					}

					if (Common.isNotEmpty(user.getBmdm())) {

						// 使用seq代替是否查询子级部门 seq =1 查询子级部门
						if (user.getSeq() != null && user.getSeq() == 1) {

							dms = deptManager.getSubCodes(user.getBmdm());
							sql.append(" and  bmdm in (:dms)");

						} else {
							sql.append(" and  bmdm =?");
							params.add(user.getBmdm());
						}

					}

				}
				Query query = session.createQuery(sql.toString());

				query.setFirstResult((page - 1) * rows);
				query.setMaxResults(rows);

				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				if (dms != null) {
					query.setParameterList("dms", dms);
				}
				List<User> users = (List<User>) query.list();
				return users;
			}
		});
	}**/

	
	public User getUser(String userName) {
//		List<User> users = (List<User>) this.hibernateTemplate.find("From User where yhm=?", userName);
//		if (users == null || users.isEmpty()) {
//			return null;
//		}
//		return users.get(0);
		User user = this.userRepository.findByYhm(userName);
		return user;
	}

	
	public User getUser(Integer id) {
		return this.userRepository.findById(id).get();
		//return this.hibernateTemplate.get(User.class, id);
	}

	
	public User saveUser(User user) {
//		user = this.hibernateTemplate.merge(user);
//		return user;
		user = this.userRepository.save(user);
		return user;
	}

	
	public void deleteUser(User user) {
		this.userRepository.delete(user);
//		this.hibernateTemplate.delete(user);
	}

	
	/**public Integer getUserCount(final User user) {

		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			public Integer doInHibernate(Session session) throws HibernateException {
				StringBuffer sql = new StringBuffer("select count(*) From User where 1=1");

				List<String> dms = null;
				List params = new ArrayList();

				if (user != null) {

					if (Common.isNotEmpty(user.getYhm())) {
						sql.append(" and  yhm like ?");
						params.add("%" + user.getYhm() + "%");
					}

					if (Common.isNotEmpty(user.getYhxm())) {
						sql.append(" and  yhxm like ?");
						params.add("%" + user.getYhxm() + "%");
					}

					if (Common.isNotEmpty(user.getSfzh())) {
						sql.append(" and  sfzh like ?");
						params.add(user.getSfzh() + "%");
					}

					if (Common.isNotEmpty(user.getBmdm())) {

						// 使用seq代替是否查询子级部门 seq =1 查询子级部门
						if (user.getSeq() != null && user.getSeq() == 1) {

							dms = deptManager.getSubCodes(user.getBmdm());
							sql.append(" and  bmdm in (:dms)");

						} else {
							sql.append(" and  bmdm =?");
							params.add(user.getBmdm());
						}

					}

				}
				Query query = session.createQuery(sql.toString());

				int i = 0;
				for (Object param : params) {
					query.setParameter(i, param);
					i++;
				}

				if (dms != null) {
					query.setParameterList("dms", dms);
				}

				Long count = (Long) query.uniqueResult();
				return count.intValue();
			}
		});
	}**/

	public boolean isExistUserName(final User user) {

//		StringBuffer sb = new StringBuffer("from User where yhm=?");
//
//		List<User> users;
//
//		if (user.getId() != null) {
//			sb.append(" and id!=?");
//			users = (List<User>) this.hibernateTemplate.find(sb.toString(), user.getYhm(), user.getId());
//		} else {
//			users = (List<User>) this.hibernateTemplate.find(sb.toString(), user.getYhm());
//		}

//		return users == null || users.size() == 0 ? true : false;
		
		
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

//	public void initAdmin() {
//
//		List<User> users = (List<User>) this.hibernateTemplate.find("from User where yhm=?", "admin");
//
//		if (users == null || users.isEmpty()) {
//			User user = new User();
//		}
//
//	}

	public User login(String userName) {
//		List<User> list =  (List<User>) hibernateTemplate.find("from User where yhm=?", userName);
//		if (list != null && list.size() > 0) {
//			return list.get(0);
//		} else {
//			return null;
//		}
		User user = this.userRepository.findByYhm(userName);
		return user;
	}

	
	public User queryUser(User user) {
//		DetachedCriteria query = DetachedCriteria.forClass(User.class);
//
//		
//		if(user.getSfzh()!=null&&!"".equals(user.getSfzh().trim())){
//			query.add(Restrictions.eq("sfzh", user.getSfzh()));
//		}
//		
//		List<User> users = (List<User>) this.hibernateTemplate.findByCriteria(query);
		
		return this.userRepository.findBySfzh(user.getSfzh());//users==null||users.size()==0?null:users.get(0);
	}


	public Map<String, Object> getUsers(Integer page, Integer rows, final User user) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<User> bookPage = userRepository.findAll(new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (Common.isNotEmpty(user.getYhm())) {
//					sql.append(" and  yhm like ?");
//					params.add("%" + user.getYhm() + "%");
					list.add(criteriaBuilder.like(root.get("yhm").as(String.class), "%" + user.getYhm() + "%"));
				}

				if (Common.isNotEmpty(user.getYhxm())) {
//					sql.append(" and  yhxm like ?");
//					params.add("%" + user.getYhxm() + "%");
					list.add(criteriaBuilder.like(root.get("yhxm").as(String.class), "%" + user.getYhxm() + "%"));
				}

				if (Common.isNotEmpty(user.getSfzh())) {
//					sql.append(" and  sfzh like ?");
//					params.add(user.getSfzh() + "%");
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
//						sql.append(" and  bmdm in (:dms)");
						}

					} else {
//						sql.append(" and  bmdm =?");
//						params.add(user.getBmdm());
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
}
