package com.xs.jt.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.cms.entity.VehicleLock;
@Repository
public interface VehicleLockRepository extends JpaRepository<VehicleLock, Integer>,JpaSpecificationExecutor<VehicleLock>{
	
	/**
	 * 根据车辆识别代号查询被锁定的车辆 ，锁定为1
	 * @param clsbdh
	 * @return
	 */
	@Query(value = "from VehicleLock where clsbdh = :clsbdh and sdzt = '1'")
	List<VehicleLock> findMotorVehicleBusinessLockByClsbdh(@Param("clsbdh")String clsbdh) ;

}
