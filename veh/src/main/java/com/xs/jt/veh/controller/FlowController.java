package com.xs.jt.veh.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.WorkPoint;
import com.xs.jt.veh.manager.IDeviceManager;
import com.xs.jt.veh.manager.IFlowManager;
import com.xs.jt.veh.manager.IWorkPointManager;

@Controller
@RequestMapping(value = "/flow")
@Modular(modelCode = "Flow", modelName = "检测流程", isEmpowered = false)
public class FlowController {

	@Resource(name = "flowManager")
	private IFlowManager flowManager;

	@Resource(name = "workPointManager")
	private IWorkPointManager workPointManager;

	@Resource(name = "deviceManager")
	private IDeviceManager deviceManager;

	@UserOperation(code = "getFlows", name = "查询检测流程")
	@RequestMapping(value = "getFlows", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDevices() {// (PageInfo pageInfo) {
		Map<String, Object> json = ResultHandler.toMyJSON(flowManager.getFlows(), 0);
		return json;
	}

	@UserOperation(code = "addFlow", name = "新增修改检测流程")
	@RequestMapping(value = "addFlow", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addFlow(Flow flow) {
		this.flowManager.save(flow);
		return ResultHandler.toSuccessJSON("新增流程成功。");
	}

	@UserOperation(code = "delete", name = "删除流程")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> delete(Flow flow) {
		this.flowManager.delete(flow);
		return ResultHandler.toSuccessJSON("删除成功。");
	}

	@UserOperation(code = "addFlow", name = "新增修改检测流程")
	@RequestMapping(value = "updateFlow", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateFlow(Flow flow) {

		System.out.println(flow.getFlow());
		Message message = this.flowManager.update(flow);
		return ResultHandler.toMessage(message);
	}

	@UserOperation(code = "getFlows", name = "查询检测流程")
	@RequestMapping(value = "getWorkPointAndDeviceByJcxxh", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getWorkPointAndDeviceByJcxxh(@RequestParam Integer jcxdh) {
		List<WorkPoint> wps = this.workPointManager.getWorkPointsByJcxdh(jcxdh);
		List<Device> devices = deviceManager.getDevices(jcxdh);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("gw", wps);
		data.put("sb", devices);
		return data;
	}

}
