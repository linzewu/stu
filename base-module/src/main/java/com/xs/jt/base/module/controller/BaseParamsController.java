package com.xs.jt.base.module.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ComputerInfoUtil;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.util.PageInfo;

@Controller
@RequestMapping(value = "/bps")
@Modular(modelCode="BaseParams",modelName="系统参数",isEmpowered=false)
//@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_SYS,icoUrl="/dzyxh/images/system.png",href="/dzyxh/page/system/systemInfo.html",modeIndex=4,appIndex=2)
public class BaseParamsController {

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;
	
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "all.js", produces = "application/javascript; charset=utf-8",method=RequestMethod.GET)
	@UserOperation(code="all.js",name="数据字典",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	public @ResponseBody String getBaseParamsOfJS() throws JsonProcessingException {
		ServletContext servletContext = request.getSession().getServletContext();
		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		ObjectMapper objectMapper = new ObjectMapper();
		String js = " var bps=" + objectMapper.writeValueAsString(bps);
		return js;
	}

	@RequestMapping(value = "all.json",method=RequestMethod.GET)
	@UserOperation(code="all.js",name="数据字典",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	public @ResponseBody Map getBaseParams() {

		RequestContext requestContext = new RequestContext(request);

		ServletContext servletContext = request.getSession().getServletContext();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");

		return ResultHandler.toMyJSON(1, requestContext.getMessage(Constant.ConstantKey.SUCCESS), bps);
	}
	
	@UserOperation(code="getComputerInfo",name="系统参数查询")
	@RequestMapping(value = "getComputerInfo",method=RequestMethod.POST)
	public @ResponseBody Map getComputer() {
		Map map = ComputerInfoUtil.getComputerInfo();
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, map);
	}

	
	@RequestMapping(value = "getLicenseInfo",method=RequestMethod.GET)
	public @ResponseBody Map getLicenseInfo() {
		Map map = ComputerInfoUtil.getComputerInfo();
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, map);
	}
	
	@UserOperation(code="save",name="保存系统参数")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map save(BaseParams baseParams) {
		baseParams = this.baseParamsManager.save(baseParams);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功",baseParams);
	}
	
	@UserOperation(code="delete",name="删除系统参数")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map delete(@RequestParam Integer id) {
		this.baseParamsManager.delete(id);
		return ResultHandler.toSuccessJSON("删除成功！");
	}

	@UserOperation(code="getBaseParamsOfPage",name="查询数据字典")
	@RequestMapping(value = "getBaseParamsOfPage",method = RequestMethod.POST)
	public @ResponseBody Map getBaseParamsOfPage(Integer page, Integer rows,BaseParams param) {
		Map data = this.baseParamsManager.getBaseParams(page-1,rows,param);
		return data;
	}
	
	@UserOperation(code="refresh",name="刷新系统参数")
	@RequestMapping(value = "refresh",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> refresh() {
		ServletContext servletContext = request.getSession().getServletContext();
		List<BaseParams> list = baseParamsManager.getBaseParams();
		servletContext.setAttribute("bps", list);
		Map<String, List<BaseParams>>  bpsMap = baseParamsManager.convertBaseParam2Map();
		servletContext.setAttribute("bpsMap", bpsMap);
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "刷新成功", list);
	}
	
	@UserOperation(code="getBaseParamsByType",name="根据字典类型查询数据字典",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getBaseParamsByType",method = RequestMethod.POST)
	public @ResponseBody List<BaseParams> getBaseParamsByType(String type) {
		List<BaseParams> list = this.baseParamsManager.getBaseParamsByType(type);
		return list;
	}
	
	@UserOperation(code="save",name="保存系统参数平台IP",isMain=false)
	@RequestMapping(value = "savePtip", method = RequestMethod.POST)
	public @ResponseBody Map savePtip(BaseParams baseParams) {
		List<BaseParams> list = this.baseParamsManager.getBaseParamsByType("ptip");
		if(list != null) {
			BaseParams bp = list.get(0);
			bp.setParamValue(baseParams.getParamValue());
			this.baseParamsManager.save(bp);
		}
		return this.refresh();
	}

}
