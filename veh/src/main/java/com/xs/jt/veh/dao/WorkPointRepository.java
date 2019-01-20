package com.xs.jt.veh.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.WorkPoint;

@Repository
public interface WorkPointRepository extends JpaRepository<WorkPoint, Integer> {

	@Query(value = "from WorkPoint where jcxdh = :jcxdh order by sort asc")
	public List<WorkPoint> findWorkPointByJcxdh(@Param("jcxdh") Integer jcxdh);

	@Query(value = "from WorkPoint order by jcxdh asc , sort asc")
	public List<WorkPoint> getWorkPoints();

}
