package com.xs.jt.cmsvideo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.cmsvideo.entity.PdaInfo;
import com.xs.jt.cmsvideo.manager.IPdaInfoManager;

@Controller
@RequestMapping(value = "/externalInterface")
@Modular(modelCode = "externalInterface", modelName = "对外提供接口管理")
public class ExternalInterfaceController {
	
	@Autowired
	private IPdaInfoManager pdaInfoManager;

	@UserOperation(code = "getPdaInfoBySerialCode", name = "根据串码查询PDA信息", userOperationEnum = CommonUserOperationEnum.NoLogin)
	@RequestMapping(value = "getPdaInfoBySerialCode", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getPdaInfoBySerialCode(String serialCode){
		PdaInfo pdaInfo = pdaInfoManager.getPdaInfoBySerialCode(serialCode);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "根据串码查询PDA信息成功！", pdaInfo);
	}
}
