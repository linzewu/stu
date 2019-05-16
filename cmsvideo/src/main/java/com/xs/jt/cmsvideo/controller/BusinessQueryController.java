package com.xs.jt.cmsvideo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;

@Controller
@RequestMapping(value = "/business")
@Modular(modelCode = "business", modelName = "业务查询")
public class BusinessQueryController {

	// 18CH3
	@UserOperation(code = "getCheckNotPassInfo", name = "获取机动车查验复核不通过原因")
	@RequestMapping(value = "getCheckNotPassInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCheckNotPassInfo(@RequestBody JSONObject param) {

		return null;
	}

	// 18CH7
	@UserOperation(code = "getGonggaoInfo", name = "获取机动车公告技术参数文本信息")
	@RequestMapping(value = "getGonggaoInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGonggaoInfo(@RequestBody JSONObject param) {

		return null;
	}
	
	// 18CH7
	@UserOperation(code = "getGonggaoImageInfo", name = "获取机动车公告技术参数图片信息")
	@RequestMapping(value = "getGonggaoImageInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGonggaoImageInfo(@RequestBody JSONObject param) {

		return null;
	}
	
	
	// 18CH8
	@UserOperation(code = "getRecheckInfo", name = "获取机动车查验抽查复核结果")
	@RequestMapping(value = "getRecheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getRecheckInfo(@RequestBody JSONObject param) {

		return null;
	}

	// 18CH9
	@UserOperation(code = "getViolationInfo", name = "获取机动车违规产品信息")
	@RequestMapping(value = "getViolationInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getViolationInfo(@RequestBody JSONObject param) {

		return null;
	}
	
	// 18CHA
	@UserOperation(code = "getVehImage", name = "获取机动车标准照片等信息")
	@RequestMapping(value = "getVehImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getVehImage(@RequestBody JSONObject param) {

		return null;
	}
	
	// 18CF1
	@UserOperation(code = "getInspectorInfo", name = "获取查验员备案信息")
	@RequestMapping(value = "getInspectorInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getInspectorInfo(@RequestBody JSONObject param) {

		return null;
	}
	
	
	// 18CF2
	@UserOperation(code = "getCheckAreaInfo", name = "获取查验区备案信息")
	@RequestMapping(value = "getCheckAreaInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCheckAreaInfo(@RequestBody JSONObject param) {

		return null;
	}
	
	/*
	 * // 18CF3
	 * 
	 * @UserOperation(code = "getPDAInfo", name = "获取查验智能终端系统备案信息")
	 * 
	 * @RequestMapping(value = "getPDAInfo", method = RequestMethod.POST)
	 * public @ResponseBody Map<String, Object> getPDAInfo(@RequestBody JSONObject
	 * param) {
	 * 
	 * return null; }
	 */
	

}
