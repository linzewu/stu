package com.xs.jt.veh.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.veh.entity.CheckPhoto;
import com.xs.jt.veh.entity.ExternalCheck;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.network.CurbWeightData;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.ICheckLogManager;
import com.xs.jt.veh.manager.ICheckPhotoManager;
import com.xs.jt.veh.manager.ICurbWeightDataManager;
import com.xs.jt.veh.manager.IExternalCheckManager;
import com.xs.jt.veh.manager.IVehCheckLoginManager;
import com.xs.jt.veh.manager.IVehCheckProcessManager;
import com.xs.jt.veh.network.TakePicture;

@Controller
@RequestMapping(value = "/pda")
@Modular(modelCode = "PDAService", modelName = "PDA")
public class PDAServiceController {

	@Resource(name = "externalCheckManager")
	private IExternalCheckManager externalCheckManager;

	@Resource(name = "checkEventManager")
	private ICheckEventManager checkEventManger;
	@Autowired
	private IVehCheckLoginManager vehCheckLoginManager;
	@Autowired
	private IVehCheckProcessManager vehCheckProcessManager;
	@Autowired
	private ICurbWeightDataManager curbWeightDataManager;
	@Autowired
	private ICheckLogManager checkLogManager;
	@Autowired
	private ICheckPhotoManager checkPhotoManager;

	@RequestMapping(value = "getCheckList")
	@UserOperation(code = "getCheckList", name = "查询待检列表")
	public @ResponseBody String getCheckList(HttpServletRequest request, @RequestParam Integer status)
			throws JsonProcessingException {
		List<VehCheckLogin> data = vehCheckLoginManager.getVehCheckLoginOfSXZT(status);
		ObjectMapper om = new ObjectMapper();
		String jsonp = ResultHandler.parserJSONP(request, om.writeValueAsString(data));
		return jsonp;
	}

	@UserOperation(code = "pushVehOnLine", name = "引车上线")
	@RequestMapping(value = "pushVehOnLine")
	public @ResponseBody Map pushVehOnLine(@RequestParam Integer id) {
		Message message = this.vehCheckLoginManager.upLine(id);
		return ResultHandler.toMessage(message);
	}

	@UserOperation(code = "external", name = "车辆外检")
	@RequestMapping(value = "external", method = RequestMethod.POST)
	public @ResponseBody Map externalUpload(@Valid ExternalCheck externalCheck, BindingResult result)
			throws InterruptedException {
		if (!result.hasErrors()) {
			Message message = externalCheckManager.saveExternalCheck(externalCheck);
			return ResultHandler.toMessage(message);
		} else {
			return ResultHandler.resultHandle(result, null, "校验出错");
		}
	}

	@UserOperation(code = "externalDC", name = "动态底盘检测")
	@RequestMapping(value = "externalDC", method = RequestMethod.POST)
	public @ResponseBody Map externalDCUpload(@Valid ExternalCheck externalCheck, BindingResult result)
			throws InterruptedException {
		if (!result.hasErrors()) {
			Message message = externalCheckManager.saveExternalCheckDC(externalCheck);
			return ResultHandler.toMessage(message);
		} else {
			return ResultHandler.resultHandle(result, null, "校验出错");
		}
	}

	@UserOperation(code = "externalC1", name = "底盘检测")
	@RequestMapping(value = "externalC1", method = RequestMethod.POST)
	public @ResponseBody Map externalC1Upload(@Valid ExternalCheck externalCheck, BindingResult result)
			throws InterruptedException {
		if (!result.hasErrors()) {
			Message message = externalCheckManager.saveExternalCheckC1(externalCheck);
			return ResultHandler.toMessage(message);
		} else {
			return ResultHandler.resultHandle(result, null, "校验出错");
		}
	}

	@UserOperation(code = "getExternal", name = "车辆外观待检列表")
	@RequestMapping(value = "getExternal")
	public @ResponseBody List getExternal(String hphm) {
		List<VehCheckLogin> data = vehCheckLoginManager.getExternalCheckVhe(hphm);
		return data;
	}

	@UserOperation(code = "getExternalDC", name = "车辆动态底盘待检列表")
	@RequestMapping(value = "getExternalDC")
	public @ResponseBody List getExternalDC(String hphm) {
		List<VehCheckLogin> data = vehCheckLoginManager.getExternalDC(hphm);
		return data;
	}

	@UserOperation(code = "getExternalC1", name = "车辆底盘待检列表")
	@RequestMapping(value = "getExternalC1")
	public @ResponseBody List getExternalC1(String hphm) {
		List<VehCheckLogin> data = vehCheckLoginManager.getExternalC1(hphm);
		return data;
	}

	@UserOperation(code = "getExternalR", name = "路试待检列表")
	@RequestMapping(value = "getExternalR")
	public @ResponseBody List getExternalR(String hphm) {
		List<VehCheckLogin> data = vehCheckLoginManager.getExternalR(hphm);
		return data;
	}

	@UserOperation(code = "getRoadProcess", name = "查询路试过程", isMain = false)
	@RequestMapping(value = "getRoadProcess")
	public @ResponseBody List getRoadProcess(String jylsh) {
		List<VehCheckProcess> data = vehCheckProcessManager.getRoadProcess(jylsh);
		return data;
	}

	@UserOperation(code = "roadProcess", name = "车辆路试")
	@RequestMapping(value = "roadProcess")
	public @ResponseBody Map roadProcess(@RequestParam String jylsh, @RequestParam String jyxm,
			@RequestParam Integer type) {
		List<VehCheckProcess> datas = vehCheckProcessManager.getRoadProcess(jylsh);
		VehCheckLogin vehCheckLogin = this.vehCheckLoginManager.getVehCheckLogin(jylsh);
		VehCheckProcess vehCheckProcess = null;
		for (VehCheckProcess process : datas) {
			if (jyxm.equals(process.getJyxm())) {
				vehCheckProcess = process;
				break;
			}
		}
		if (vehCheckProcess == null) {
			return ResultHandler.toMyJSON(2, "未找到检验项目");
		} else {
			if (type == 0) {

				if (jyxm.equals("R1")) {
					TakePicture.createNew(vehCheckLogin, "R1", 1000, "0341");
				}
				if (jyxm.equals("R2")) {
					TakePicture.createNew(vehCheckLogin, "R2", 1000, "0345");
				}
				vehCheckProcess.setKssj(new Date());
				this.vehCheckProcessManager.updateProcess(vehCheckProcess);
				checkEventManger.createEvent(jylsh, vehCheckLogin.getJycs(), "18C55", jyxm, vehCheckLogin.getHphm(),
						vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), vehCheckLogin.getVehcsbj());
			} else if (type == 1) {
				if (jyxm.equals("R1")) {
					TakePicture.createNew(vehCheckLogin, "R1", 1000, "0343");
				}
				if (jyxm.equals("R2")) {
					TakePicture.createNew(vehCheckLogin, "R2", 1000, "0351");
				}
				vehCheckProcess.setJssj(new Date());
				this.vehCheckProcessManager.updateProcess(vehCheckProcess);
			}
		}
		return ResultHandler.toSuccessJSON("时间更新成功");
	}

	@UserOperation(code = "uploadPhoto", name = "图片上传")
	@RequestMapping(value = "uploadPhoto")
	public @ResponseBody Map uploadPhoto(@RequestParam("photo") CommonsMultipartFile[] photo,
			@Valid CheckPhoto checkPhoto, BindingResult result) {

		if (!result.hasErrors()) {
			checkPhoto.setZp(photo[0].getBytes());
			Message message = checkPhotoManager.savePhoto(checkPhoto);
			return ResultHandler.toMessage(message);
		} else {
			Map msg = ResultHandler.resultHandle(result, null, "校验出错");
			return msg;
		}
	}

	@UserOperation(code = "processStart", name = "检测过程开始", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "processStart")
	public @ResponseBody Map processStart(@RequestParam("jyxm") String jyxm, @RequestParam("jylsh") String jylsh,
			@RequestParam("jycs") Integer jycs) {

		VehCheckProcess vehCheckProcess = vehCheckProcessManager.getVehCheckProces(jylsh, jycs, jyxm);
		vehCheckProcess.setKssj(new Date());
		this.vehCheckProcessManager.updateProcess(vehCheckProcess);

		VehCheckLogin vehCheckLogin = this.vehCheckLoginManager.getVehCheckLogin(jylsh);

		if (vehCheckProcess.getJyxm().equals("C1")) {
			TakePicture.createNew(vehCheckLogin, "C1", 5000);
		}

		checkEventManger.createEvent(jylsh, jycs, "18C55", vehCheckProcess.getJyxm(), vehCheckProcess.getHphm(),
				vehCheckProcess.getHpzl(), vehCheckProcess.getClsbdh(), vehCheckLogin.getVehcsbj());

		this.vehCheckProcessManager.updateProcess(vehCheckProcess);
		return ResultHandler.toSuccessJSON("过程开始成功");
	}

	@UserOperation(code = "processStart", name = "获取检测项目", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getChekcItem")
	public @ResponseBody String getChekcItem(@RequestParam("jylsh") String jylsh, @RequestParam("type") String type)
			throws DocumentException {
		String item = checkLogManager.getCheckItem(jylsh, type);
		return item;
	}

	@UserOperation(code = "getVehInOfHphm", name = "查询机动车详细信息")
	@RequestMapping(value = "getVehInOfHphm")
	public @ResponseBody List getVehInOfHphm(@RequestParam("hphm") String hphm) {
		List<VehCheckLogin> data = vehCheckLoginManager.getVheInfoOfHphm(hphm);
		return data;
	}

	@UserOperation(code = "zbzlUpload", name = "整备质量上传")
	@RequestMapping(value = "zbzlUpload", method = RequestMethod.POST)
	public @ResponseBody Map zbzlUpload(@Valid CurbWeightData curbWeight, BindingResult result)
			throws InterruptedException {
		if (!result.hasErrors()) {
			curbWeightDataManager.saveCurbWeight(curbWeight);

			Message message = new Message();
			message.setMessage("上传成功");
			message.setState(Message.STATE_SUCCESS);
			return ResultHandler.toMessage(message);
		} else {
			return ResultHandler.resultHandle(result, null, "校验出错");
		}
	}

	@UserOperation(code = "zbzlUpload", name = "整备质量查询", isMain = false)
	@RequestMapping(value = "getCurbWeight", method = RequestMethod.POST)
	public @ResponseBody CurbWeightData getCurbWeight(String jylsh) {

		return this.curbWeightDataManager.getLastCurbWeightDataOfJylsh(jylsh);

	}

}
