package com.xs.jt.cms.manager;

import java.util.Map;

import com.xs.jt.cms.entity.PreCarRegister;

public interface IPreCarRegisterManager {
	
	public PreCarRegister save(PreCarRegister preCarRegister);
	
	public Map<String,Object> getPreCarRegisters(Integer page, Integer rows,PreCarRegister preCarRegister);

}
