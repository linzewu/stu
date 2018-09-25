package com.xs.jt.cms.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.dao.PreCarRegisterRepository;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.manager.IPreCarRegisterManager;
@Service("preCarRegisterManager")
public class PreCarRegisterManagerImpl implements IPreCarRegisterManager {
	
	@Autowired
	private PreCarRegisterRepository preCarRegisterRepository;

	public PreCarRegister save(PreCarRegister preCarRegister) {
		return this.preCarRegisterRepository.save(preCarRegister);
	}

}
