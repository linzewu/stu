package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.network.OutlineRepository;
import com.xs.jt.veh.entity.network.Outline;
import com.xs.jt.veh.manager.IOutlineManager;
@Service("outlineManager")
public class OutlineManagerImpl implements IOutlineManager {
	@Autowired
	public OutlineRepository outlineRepository;

	@Override
	public List<Outline> getOutlineOfReportFlag() {
		return outlineRepository.findOutlineByReportFlag(Outline.FLAG_Y);
	}

	@Override
	public void updateOutline(Outline outline) {
		outlineRepository.save(outline);
		
	}

}
