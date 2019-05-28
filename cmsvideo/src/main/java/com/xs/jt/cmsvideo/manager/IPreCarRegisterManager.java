package com.xs.jt.cmsvideo.manager;

import java.util.Map;

import com.xs.jt.cmsvideo.entity.PreCarRegister;

public interface IPreCarRegisterManager {
	
	public PreCarRegister save(PreCarRegister preCarRegister);
	
	public Map<String,Object> getPreCarRegisters(Integer page, Integer rows,PreCarRegister preCarRegister);
	
	public PreCarRegister findPreCarRegisterByLsh(String lsh);

}
