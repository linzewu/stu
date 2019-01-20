package com.xs.jt.veh.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.common.IamgeBase64Cash;
import com.xs.jt.veh.common.ImageChange;
import com.xs.jt.veh.dao.CheckEventRepository;
import com.xs.jt.veh.dao.CheckLogRepository;
import com.xs.jt.veh.dao.CheckPhotoRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.entity.CheckPhoto;
import com.xs.jt.veh.entity.Insurance;
import com.xs.jt.veh.entity.RoadCheck;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.manager.ICheckEventManager;
import com.xs.jt.veh.manager.ICheckPhotoManager;
import com.xs.jt.veh.manager.IDeviceCheckJudegManager;
import com.xs.jt.veh.manager.IExternalCheckJudgeManager;
import com.xs.jt.veh.manager.IInsuranceManager;
import com.xs.jt.veh.manager.IReportManager;
import com.xs.jt.veh.manager.IRoadCheckManager;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping(value = "/report")
@Modular(modelCode = "Report", modelName = "检验报告查询")
public class ReportController {

	@Resource(name = "roadCheckManager")
	private IRoadCheckManager roadCheackManager;

//	@Resource(name = "checkDataManager")
//	private CheckDataManager checkDataManager;
	@Autowired
	private IReportManager reportManager;

	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private IDeviceCheckJudegManager deviceCheckJudegManager;
	@Autowired
	private IExternalCheckJudgeManager externalCheckJudgeManager;
	@Autowired
	private CheckPhotoRepository checkPhotoRepository;
	@Autowired
	private ICheckPhotoManager checkPhotoManager;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;
	@Autowired
	private CheckLogRepository checkLogRepository;
	@Autowired
	private CheckEventRepository checkEventRepository;
	@Autowired
	private IInsuranceManager insuranceManager;
	@Autowired
	private ICheckEventManager checkEventManager;

	@UserOperation(code = "getReport1", name = "仪器设备检验报告")
	@RequestMapping(value = "getReport1", method = RequestMethod.POST)
	public @ResponseBody Map getReport1(@RequestParam String jylsh, int jycs) {
		Map data = reportManager.getReport1(jylsh, jycs);
		return data;
	}

	@UserOperation(code = "getReport4", name = "检验报告")
	@RequestMapping(value = "getReport4", method = RequestMethod.POST)
	public @ResponseBody String getReport4(@RequestParam String jylsh) {
		String datas = reportManager.getReport4(jylsh);

		return datas;
	}

	@UserOperation(code = "getReport2", name = "外检报告")
	@RequestMapping(value = "getReport2", method = RequestMethod.POST)
	public @ResponseBody Map getReport2(@RequestParam String jylsh) {
		Map<String, List> data = reportManager.getReport2(jylsh);
		return data;
	}

	@RequestMapping(value = "test")
	public void saveTT(@RequestParam String jylsh) {

		VehCheckLogin vehCheckLogin = null;
		List<VehCheckLogin> vclList = vehCheckLoginRepository.findByJylsh(jylsh);
		if (vclList != null && !vclList.isEmpty()) {
			vehCheckLogin = vclList.get(0);
		}
		deviceCheckJudegManager.createDeviceCheckJudeg(vehCheckLogin);
		System.out.println("更新完成");
	}

	@RequestMapping(value = "test2")
	public void saveET(@RequestParam String jylsh) {
		VehCheckLogin vehCheckLogin = null;
		List<VehCheckLogin> vclList = vehCheckLoginRepository.findByJylsh(jylsh);
		if (vclList != null && !vclList.isEmpty()) {
			vehCheckLogin = vclList.get(0);
		}
		externalCheckJudgeManager.createExternalCheckJudge(vehCheckLogin);
		System.out.println("更新完成");
	}

	@UserOperation(code = "getCheckPhotos", name = "查询检测照片")
	@RequestMapping(value = "getCheckPhotos")
	public @ResponseBody List getCheckPhotos(@RequestParam String jylsh) {
		return this.checkPhotoRepository.findCheckPhotoByJylsh(jylsh);
	}

	@UserOperation(code = "uploadPhoto", name = "上传检测照片")
	@RequestMapping(value = "uploadPhoto")
	public @ResponseBody Map uploadPhoto(CheckPhoto checkPhoto, @RequestParam String imageData) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] zp = decoder.decodeBuffer(imageData);

		if (checkPhoto.getZpzl().indexOf("02") == 0) {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zp);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			bufferedImage = ImageChange.Rotate(bufferedImage, 90);
			// 创建字节输入流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "JPEG", byteArrayOutputStream);
			zp = byteArrayOutputStream.toByteArray();
		}

		checkPhoto.setZp(zp);
		checkPhoto.setPssj(new Date());
		this.checkPhotoManager.saveCheckPhoto(checkPhoto);

		BASE64Encoder encoder = new BASE64Encoder();
		imageData = encoder.encode(checkPhoto.getZp());
		IamgeBase64Cash.getInstance().cashBase64Iamge(imageData, checkPhoto.getId().toString());
		Map resulMap = ResultHandler.toSuccessJSON("保存成功！");
		resulMap.put("id", checkPhoto.getId());
		resulMap.put("img", imageData);

		return resulMap;
	}

	@UserOperation(code = "uploadPhoto", name = "上传检测照片")
	@RequestMapping(value = "uploadFileImag")
	public @ResponseBody String uploadFileImag(CheckPhoto checkPhoto, MultipartFile imgFile) throws IOException {
		byte[] zp = imgFile.getBytes();
		if (checkPhoto.getZpzl().indexOf("02") == 0) {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(zp);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			bufferedImage = ImageChange.Rotate(bufferedImage, 90);
			// 创建字节输入流
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "JPEG", byteArrayOutputStream);
			zp = byteArrayOutputStream.toByteArray();
		}

		checkPhoto.setZp(zp);
		checkPhoto.setPssj(new Date());
		this.checkPhotoManager.saveCheckPhoto(checkPhoto);

		BASE64Encoder encoder = new BASE64Encoder();
		String imageData = encoder.encode(checkPhoto.getZp());
		IamgeBase64Cash.getInstance().cashBase64Iamge(imageData, checkPhoto.getId().toString());
		Map resulMap = ResultHandler.toSuccessJSON("保存成功！");
		resulMap.put("id", checkPhoto.getId());
		resulMap.put("img", imageData);

		ObjectMapper om = new ObjectMapper();

		return om.writeValueAsString(resulMap);
	}

	@UserOperation(code = "getCheckPhotos", name = "查询检测照片")
	@RequestMapping(value = "getCheckPhoto")
	public @ResponseBody String getCheckPhoto(String id) throws Exception {

		String img = IamgeBase64Cash.getInstance().getCashBase64Iamge(id);

		if (img == null) {
			CheckPhoto cp = this.checkPhotoRepository.findById(Integer.parseInt(id)).get();
			BASE64Encoder encoder = new BASE64Encoder();
			img = encoder.encode(cp.getZp());
			IamgeBase64Cash.getInstance().cashBase64Iamge(img, cp.getId().toString());
		}

		return img;

	}

	@UserOperation(code = "deleteImage", name = "删除检测照片")
	@RequestMapping(value = "deleteImage")
	public @ResponseBody Map deleteImage(Integer id) throws Exception {
		this.checkPhotoRepository.deleteById(id);
		return ResultHandler.toSuccessJSON("删除成功");

	}

	@UserOperation(code = "getProcess", name = "查询检测过程")
	@RequestMapping(value = "getProcess")
	public @ResponseBody List getProcess(@RequestParam String jylsh) {
		return this.vehCheckProcessRepository.getVehCheckProcesByJylsh(jylsh);
	}

	@UserOperation(code = "getCheckLogs", name = "查询检测日志")
	@RequestMapping(value = "getCheckLogs")
	public @ResponseBody List getCheckLogs(@RequestParam String jylsh) {
		return this.checkLogRepository.findCheckLogByJylsh(jylsh);
	}

	@UserOperation(code = "getCheckEvents", name = "查询检测事件")
	@RequestMapping(value = "getCheckEvents")
	public @ResponseBody List getCheckEvents(@RequestParam String jylsh) {
		return this.checkEventRepository.findCheckEventsByJylsh(jylsh);
	}

	@UserOperation(code = "getInsurance", name = "查询保险信息")
	@RequestMapping(value = "getInsurance")
	public @ResponseBody Insurance getInsurance(@RequestParam String jylsh) {

		return insuranceManager.getInsurance(jylsh);
	}

	@UserOperation(code = "getRoadCheck", name = "查询路试信息")
	@RequestMapping(value = "getRoadCheck")
	public @ResponseBody RoadCheck getRoadCheck(@RequestParam String jylsh) {

		return roadCheackManager.getRoadCheck(jylsh);
	}

	@UserOperation(code = "saveInsurance", name = "上传保险信息")
	@RequestMapping(value = "saveInsurance")
	public @ResponseBody Map saveInsurance(Insurance insurance) {
		insuranceManager.saveInsurance(insurance);
		Map resulMap = ResultHandler.toSuccessJSON("保存成功！");
		resulMap.put("id", insurance.getId());
		return resulMap;
	}

	@UserOperation(code = "saveRoadCheck", name = "上传路试信息")
	@RequestMapping(value = "saveRoadCheck")
	public @ResponseBody String saveRoadCheck(RoadCheck roadCheck)
			throws JsonProcessingException, InterruptedException {
		Message message = roadCheackManager.saveRoadCheck(roadCheck);
		Map map = ResultHandler.toMessage(message);
		map.put("data", roadCheck);

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(map);

		return jsonString;
	}

	@UserOperation(code = "updateEventState", name = "重置检测事件")
	@RequestMapping(value = "updateEventState")
	public @ResponseBody Map updateEventState(@RequestParam String jylsh)
			throws JsonProcessingException, InterruptedException {

		this.checkEventManager.resetEventState(jylsh);

		return ResultHandler.toSuccessJSON("更新成功！");

	}

}
