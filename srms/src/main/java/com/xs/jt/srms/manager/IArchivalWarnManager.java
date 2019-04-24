package com.xs.jt.srms.manager;

import java.util.Map;

import com.xs.jt.srms.entity.ArchivalWarn;

public interface IArchivalWarnManager {
	
	public Map<String, Object> getArchivalWarnList(Integer page, Integer rows, ArchivalWarn archivalWarn);

}
