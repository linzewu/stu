package com.xs.jt.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.cms.entity.PoliceCheckInfo;
import com.xs.jt.cms.manager.IPoliceCheckInfoManager;

@Controller
@RequestMapping(value = "/policeCheckInfo")
@Modular(modelCode = "policeCheckInfo", modelName = "车辆查验结果")
public class PoliceCheckInfoController {
	
	@Autowired
	private IPoliceCheckInfoManager policeCheckInfoManager;
	
	@RequestMapping(value = "addPoliceCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addPoliceCheckInfo(PoliceCheckInfo policeCheckInfo, BindingResult result) throws Exception {

		if (!result.hasErrors()) {
			policeCheckInfoManager.save(policeCheckInfo);
			return ResultHandler.resultHandle(result, policeCheckInfo, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

}
