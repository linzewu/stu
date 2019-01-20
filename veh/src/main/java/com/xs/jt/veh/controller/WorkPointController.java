package com.xs.jt.veh.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.entity.WorkPoint;
import com.xs.jt.veh.manager.IWorkPointManager;

@Controller
@RequestMapping(value = "/workpoint")
@Modular(modelCode = "workpoint", modelName = "检测工位管理")
public class WorkPointController {

	@Resource(name = "workPointManager")
	private IWorkPointManager workPointManager;

	@UserOperation(code = "getWorkPoints", name = "查询工位")
	@RequestMapping(value = "getWorkPoints", method = RequestMethod.POST)
	public @ResponseBody Map getWorkPoints() {
		List<WorkPoint> wps = this.workPointManager.getWorkPoints();
		return ResultHandler.toMyJSON(wps, wps.size());
	}

	@UserOperation(code = "save", name = "新增工位")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map saveWorkPoint(WorkPoint workPoint, BindingResult result) {

		// 如果为空则不添加
		if (workPoint.getGwzt() == null || "".equals(workPoint.getGwzt())) {
			workPoint.setGwzt(0);
		}
		WorkPoint wp = this.workPointManager.saveWorkPoint(workPoint);
		return ResultHandler.resultHandle(result, wp, Constant.ConstantMessage.SAVE_SUCCESS);
	}

	@UserOperation(code = "manage", name = "启动工位", isMain = false)
	@RequestMapping(value = "start", method = RequestMethod.POST)
	public @ResponseBody Map start(@RequestParam Integer id) {
		Message message = workPointManager.startWorkpoint(id);
		return ResultHandler.toMessage(message);
	}

	@UserOperation(code = "manage", name = "停止工位", isMain = false)
	@RequestMapping(value = "stop", method = RequestMethod.POST)
	public @ResponseBody Map stop(@RequestParam Integer id) throws InterruptedException {
		Message message = workPointManager.stopWorkpoint(id);
		return ResultHandler.toMessage(message);
	}

	@UserOperation(code = "manage", name = "重启工位")
	@RequestMapping(value = "reStart", method = RequestMethod.POST)
	public @ResponseBody Map reStart(@RequestParam Integer id) throws InterruptedException {
		Message message = workPointManager.reStartWorkpoint(id);
		return ResultHandler.toMessage(message);
	}

}
