package com.xs.jt.veh.manager;

import java.io.Serializable;
import java.util.List;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.entity.Flow;

public interface IFlowManager {

	public List<Flow> getFlows();

	public Serializable save(Flow flow);

	public void delete(Flow flow);

	public Message update(Flow flow);

	public Flow getFlow(Integer jcxdh, Integer jclclx);

}
