package com.xs.jt.base.module.aop;

import java.io.UnsupportedEncodingException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.annotation.CheckBit;
import com.xs.jt.base.module.common.TamperWithDataException;
import com.xs.jt.base.module.entity.BaseEntity;
@Service
@Aspect
@Order(999)
public class CheckBitAop {
	
	 @Autowired
	 private Environment environment;
	
	@Autowired
	private EntityManager entityManager;
	
	@Pointcut("execution(* com.xs.jt.*.*.dao.*.save*(..))")
	public void save() {
	}
	
	@Pointcut("execution(* com.xs.jt.*.dao.*.save*(..))")
	public void saveDao() {
	}
	
	/**
	 * 方法开始执行
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Before("save() || saveDao()")
	public void doBefore(JoinPoint joinPoint) throws UnsupportedEncodingException, TamperWithDataException {
		System.out.println("doBefore*********************************************" + joinPoint.getArgs());
		String isTamper = environment.getProperty("data.validate.tamper");
		if(StringUtils.isNotEmpty(isTamper) && "true".equals(isTamper)) {
			Object[] params = joinPoint.getArgs();
			if (params != null) {
				for (Object obj : params) {

//					if (Hibernate.isInitialized(obj)) {
//						hibernateTemplate.initialize(obj);
//						if (obj instanceof HibernateProxy) {
//							obj = (Object) ((HibernateProxy) obj).getHibernateLazyInitializer().getImplementation();
//						}
	//
//					}
	//
					System.out.println("class:" + obj.getClass());
					if (obj.getClass().isAnnotationPresent(CheckBit.class) && obj instanceof BaseEntity) {
						BaseEntity be = (BaseEntity) obj;
						String str = be.toString();
						String md5 = BaseEntity.md5(str);
						System.out.println("before:" + be.toString() + " md5:" + md5);
						be.setVehjyw(md5);
						if (be.getId() != null) {
							entityManager.clear(); 
							CriteriaBuilder cb = entityManager.getCriteriaBuilder();
							CriteriaQuery<?> cq = cb.createQuery(obj.getClass());
							Root<?> root = cq.from(obj.getClass()); 
							Predicate pre = cb.equal(root.get("id").as(Integer.class),be.getId());
							cq.where(pre);
							Query query = entityManager.createQuery(cq);
							BaseEntity base = (BaseEntity) query.getSingleResult();
							base.checkBit();
							if (!base.isCheckBitOk()) {
								throw new TamperWithDataException("数据非法篡改!");

							}
						}
					}
				}
			}
		}
		

	}

}
