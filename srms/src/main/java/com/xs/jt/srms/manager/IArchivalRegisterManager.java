package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;

public interface IArchivalRegisterManager {
	
	public Map<String, Object> getArchivalRegisterList(Integer page, Integer rows, ArchivalRegister archivalRegister);

	public void saveArchivalRegister(ArchivalRegister archivalRegister);

	public void deleteArchivalRegister(ArchivalRegister archivalRegister);
	
	public boolean archivalCheckOut(ArchivalCase archivalCase);
	
	public boolean archivalCheckIn(ArchivalCase archivalCase);
	
	public List<ArchivalRegister> findArchivalRegisterCheckIn(String handleUser,String zt,String rksj);
	

}
