package com.xs.jt.veh.manager.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.dao.FlowRepository;
import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.manager.IFlowManager;

@Service("flowManager")
public class FlowManagerImpl implements IFlowManager {

	@Autowired
	private FlowRepository flowRepository;

	@Override
	public List<Flow> getFlows() {

		return flowRepository.findAll();
	}

	@Override
	public Serializable save(Flow flow) {

		return flowRepository.save(flow);
	}

	@Override
	public void delete(Flow flow) {
		flowRepository.delete(flow);

	}

	@Override
	public Message update(Flow flow) {
		Message message = new Message();
		flowRepository.save(flow);
		message.setState(Message.STATE_SUCCESS);
		message.setMessage("更新成功");
		return message;
	}

	@Override
	public Flow getFlow(Integer jcxdh, Integer jclclx) {

		return flowRepository.getFlowByJcxdhAndJclclx(jcxdh, jclclx);
	}

}
