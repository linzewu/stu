package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	@Query(value = "select max(jcxxh) from Device")
	public Integer getMaxLine();

	@Query(value = "From Device order by type desc")
	public List<Device> getDevicesOrderByType();

	@Query(value = "From Device order by jcxxh asc")
	public List<Device> getDevicesOrderByJcxxh();

	@Query(value = "From Device where jcxxh=:jcxxh and type<90 order by jcxxh asc")
	public List<Device> getDevicesByJcxxh(@Param("jcxxh") Integer jcxxh);

	@Query(value = "From Device where jcxxh=:jcxxh and type = 91")
	public List<Device> getDevicesDisplay(@Param("jcxxh") Integer jcxxh);

}
