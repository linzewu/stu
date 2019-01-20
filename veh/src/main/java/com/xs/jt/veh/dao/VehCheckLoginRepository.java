package com.xs.jt.veh.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xs.jt.veh.entity.VehCheckLogin;

@Repository
public interface VehCheckLoginRepository
		extends JpaRepository<VehCheckLogin, Integer>, JpaSpecificationExecutor<VehCheckLogin> {

	@Query(value = "from VehCheckLogin where jyjgbh=:jyjgbh and jylsh=:jylsh")
	public VehCheckLogin getVehCheckLoginByJylsh(@Param("jyjgbh") String jyjgbh, @Param("jylsh") String jylsh);

	@Query(value = "from VehCheckLogin where jylsh=:jylsh")
	public List<VehCheckLogin> findByJylsh(@Param("jylsh") String jylsh);

	@Query(value = "from VehCheckLogin where vehsxzt = :vehsxzt and vehjczt!=:vehjczt")
	public List<VehCheckLogin> findByVehsxztAndVehjczt(@Param("vehsxzt") Integer vehsxzt,
			@Param("vehjczt") Integer vehjczt);

	@Query(value = "from VehCheckLogin where hphm like '%:hphm%' and dlsj>=:dlsj")
	public List<VehCheckLogin> findByHphmAndDlsj(@Param("hphm") String hphm, @Param("dlsj") Date dlsj);

	@Query(value = "from VehCheckLogin where jylsh=:jylsh and jycs =:jycs")
	public List<VehCheckLogin> findByJylshAndJycs(@Param("jylsh") String jylsh, @Param("jycs") Integer jycs);

	@Query(value = "from VehCheckLogin where hphm=:hphm and hpzl=:hpzl and ( vehjczt=:vehjczt1 or vehjczt=:vehjczt2 )")
	public List<VehCheckLogin> findByHphmAndHpzlAndVehjczt(@Param("hphm") String hphm, @Param("hpzl") String hpzl,
			@Param("vehjczt1") Integer vehjczt1, @Param("vehjczt2") Integer vehjczt2);
}
