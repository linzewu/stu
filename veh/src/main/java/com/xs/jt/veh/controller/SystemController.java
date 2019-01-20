package com.xs.jt.veh.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.veh.entity.SystemInfo;
import com.xs.jt.veh.util.WindowsInfoUtil;

@Controller
@RequestMapping(value = "/sys")
@Modular(modelCode = "System", modelName = "系统管理", isEmpowered = false)
public class SystemController {

	@Resource(name = "systemInfo")
	private SystemInfo systemInfo;

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	@Autowired
	private ServletContext servletContext;

	public static final String SETTING = "配置信息";

	public static final String SYSTEM = "系统信息";

	@UserOperation(code = "getSystemInfo", name = "查询系统信息")
	@RequestMapping(value = "getInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSystemInfo(@RequestParam Map param) {

		String uploadSwitch = "";
		List<BaseParams> bps = baseParamsManager.getBaseParamsByType("uploadSwitch");
		if (bps.size() > 0) {
			uploadSwitch = bps.get(0).getParamName();
		}

		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();

		Map<String, String> sm1 = new HashMap<String, String>();
		// 加載配置信息
		sm1.put("name", "检验机构编号");
		sm1.put("value", systemInfo.getJyjgbh());
		sm1.put("group", SETTING);
		rows.add(sm1);

		Map<String, String> sm2 = new HashMap<String, String>();
		sm2.put("name", "检验机构名称");
		sm2.put("value", systemInfo.getJyjgmc());
		sm2.put("group", SETTING);
		rows.add(sm2);

		Map<String, String> sm3 = new HashMap<String, String>();
		sm3.put("name", "监管平台IP");
		sm3.put("value", systemInfo.getJgxtip());
		sm3.put("group", SETTING);
		rows.add(sm3);

		Map<String, String> sm4 = new HashMap<String, String>();
		sm4.put("name", "监管平台端口");
		sm4.put("value", systemInfo.getJgxtdk());
		sm4.put("group", SETTING);
		rows.add(sm4);

		Map<String, String> sm5 = new HashMap<String, String>();
		sm5.put("name", "接口序列号");
		sm5.put("value", systemInfo.getJkxlh());
		sm5.put("group", SETTING);
		rows.add(sm5);

		Map<String, String> sm6 = new HashMap<String, String>();
		sm6.put("name", "数据库连接信息");
		sm6.put("value", systemInfo.getDbInfo());
		sm6.put("group", SETTING);
		rows.add(sm6);

		Map<String, String> sm7 = new HashMap<String, String>();
		sm7.put("name", "联网标记");
		sm7.put("value", systemInfo.getIsNetwork());
		sm7.put("group", SETTING);
		rows.add(sm7);

		Map<String, String> sm8 = new HashMap<String, String>();
		sm8.put("name", "加载制动标记");
		sm8.put("value", systemInfo.getPlusLoadFlag());
		sm8.put("group", SETTING);
		rows.add(sm8);

		Map<String, String> sm9 = new HashMap<String, String>();
		sm9.put("name", "垂直偏移标记");
		sm9.put("value", systemInfo.getCzpypdFlag());
		sm9.put("group", SETTING);
		rows.add(sm9);

		Map<String, String> sm10 = new HashMap<String, String>();
		sm10.put("name", "系统有效期");
		sm10.put("value", systemInfo.getStartData() + "至" + systemInfo.getEndData());
		sm10.put("group", SETTING);
		rows.add(sm10);

		Map<String, String> sm11 = new HashMap<String, String>();
		sm11.put("name", "联网开关");
		sm11.put("value", uploadSwitch);
		sm11.put("group", SETTING);
		rows.add(sm11);

		getComputerInfo(rows);

		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put("rows", rows);

		return rm;
	}

	private void getComputerInfo(List<Map<String, String>> rows) {

		Properties p = System.getProperties();

		Map<String, String> sm1 = new HashMap<String, String>();
		sm1.put("name", "系统名称");
		sm1.put("value", p.getProperty("os.name"));
		sm1.put("group", SYSTEM);
		rows.add(sm1);

		Map<String, String> sm2 = new HashMap<String, String>();
		sm2.put("name", "系统架构");
		sm2.put("value", p.getProperty("os.arch"));
		sm2.put("group", SYSTEM);
		rows.add(sm2);

		Map<String, String> sm3 = new HashMap<String, String>();
		sm3.put("name", "系统版本");
		sm3.put("value", p.getProperty("os.version"));
		sm3.put("group", SYSTEM);
		rows.add(sm3);

		Map<String, String> sm4 = new HashMap<String, String>();
		sm4.put("name", "CPU个数");
		sm4.put("value", String.valueOf(Runtime.getRuntime().availableProcessors()));
		sm4.put("group", SYSTEM);
		rows.add(sm4);

		Map<String, String> sm5 = new HashMap<String, String>();
		sm5.put("name", "虚拟机内存");
		sm5.put("value",
				"总量：" + String.valueOf(Runtime.getRuntime().totalMemory()) + " -- 空闲："
						+ String.valueOf(Runtime.getRuntime().freeMemory()) + " -- 使用最大内存量："
						+ Runtime.getRuntime().maxMemory());
		sm5.put("group", SYSTEM);

		Map<String, String> sm6 = new HashMap<String, String>();
		sm6.put("name", "磁盤使用情況");
		sm6.put("value", WindowsInfoUtil.getDisk().toString());
		sm6.put("group", SYSTEM);

		rows.add(sm6);

	}

	@UserOperation(code = "sysParamReload", name = "系统参数刷新")
	@RequestMapping(value = "sysParamReload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sysParamReload() {
		List<BaseParams> bps = baseParamsManager.getBaseParams();
		servletContext.setAttribute("bps", bps);
		return ResultHandler.toSuccessJSON("系统参数刷新成功");
	}

	@UserOperation(code = "uploadSwitch", name = "联网开关")
	@RequestMapping(value = "uploadSwitch", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadSwitch(String state) {

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		BaseParams uploadSwitch = null;
		for (BaseParams param : bps) {
			if (param.getType().equals("uploadSwitch")) {
				uploadSwitch = param;
			}
		}
		if (uploadSwitch == null) {
			uploadSwitch = new BaseParams();
			uploadSwitch.setCreateTime(new Date());
			uploadSwitch.setParamName("1".equals(state) ? "关" : "开");
			uploadSwitch.setParamValue(state);
			uploadSwitch.setType("uploadSwitch");
			uploadSwitch.setSeq(1);
			this.baseParamsManager.save(uploadSwitch);
			bps.add(uploadSwitch);
			servletContext.setAttribute("bps", bps);
			servletContext.setAttribute("uploadSwitch", state);
		} else {
			uploadSwitch.setParamValue(state);
			this.baseParamsManager.save(uploadSwitch);
			uploadSwitch.setParamName("1".equals(state) ? "关" : "开");
			servletContext.setAttribute("uploadSwitch", state);
		}
		return ResultHandler.toSuccessJSON("操作成功");
	}

}
