package com.xs.jt.cms.manager.impl;

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

import com.xs.jt.base.module.entity.BlackList;
import com.xs.jt.cms.dao.VehicleLockRepository;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.manager.IVehicleLockManager;
@Service("vehicleLockManager")
public class VehicleLockManagerImpl implements IVehicleLockManager {
	@Autowired
	private VehicleLockRepository vehicleLockRepository;

	public boolean findMotorVehicleBusinessLockByClsbdh(String clsbdh) {
		boolean flag = false;
		List<VehicleLock> list = vehicleLockRepository.findMotorVehicleBusinessLockByClsbdh(clsbdh);
		if (list != null && list.size() > 0){
			flag = true;
		}
		return flag;
	}
	
	public List<VehicleLock> findLockVehicle(String clsbdh){
		List<VehicleLock> list = vehicleLockRepository.findMotorVehicleBusinessLockByClsbdh(clsbdh);
		return list;
	}

	public Map<String, Object> getVehicleLocks(Integer page, Integer rows, VehicleLock vehicleLock) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<VehicleLock> bookPage = vehicleLockRepository.findAll(new Specification<VehicleLock>() {
			public Predicate toPredicate(Root<VehicleLock> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();				
				//list.add(criteriaBuilder.equal(root.get("enableFlag").as(String.class), "Y"));
				
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());

		return data;
	}

	public VehicleLock save(VehicleLock vehCheckInfo) {
		return vehicleLockRepository.save(vehCheckInfo);
	}

}
