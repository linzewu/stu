package com.xs.jt.cms.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutService;
import com.xs.jt.cms.common.CommonUtil;
import com.xs.jt.cms.common.URLCodeUtil;
import com.xs.jt.cms.entity.VehiclePhotos;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.manager.IVehicleLockManager;
import com.xs.jt.cms.manager.IVehiclePhotosManager;

import net.sf.json.JSONObject;

import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.manager.IVehCheckInfoManager;
import com.xs.jt.cms.manager.IPreCarRegisterManager;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "/pdaService")
@Modular(modelCode = "pdaService", modelName = "PDA对外接口")
public class PDAServiceController {

	@Autowired
	private IVehCheckInfoManager policeCheckInfoManager;
	@Autowired
	private IPDAServiceManager pDAServiceManager;
	@Autowired
	private IVehiclePhotosManager vehiclePhotosManager;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private IVehicleLockManager vehicleLockManager;
	@Autowired
	private IPreCarRegisterManager preCarRegisterManager;

	@Autowired
	private TmriJaxRpcOutService tmriJaxRpcOutService;

	@Autowired
	private HttpSession session;

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	public static String YWLX_TYPE = "check.ywlx";

	@RequestMapping(value = "addPoliceCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addPoliceCheckInfo(VehCheckInfo policeCheckInfo, BindingResult result)
			throws Exception {
		User user = (User) session.getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		BaseParams baseParam = baseParamsManager.getBaseParam(YWLX_TYPE, policeCheckInfo.getYwlx());
		String ywlx = baseParam == null ? "" : baseParam.getParamValue();
		if (!result.hasErrors()) {
			// 校验车辆是否被锁定，如果被锁定则不能保存(如果当前用户是锁定人则可以保存)
			List<VehicleLock> list = vehicleLockManager.findLockVehicle(policeCheckInfo.getClsbdh());
			if (list != null && list.size() > 0) {
				boolean flag = false;
				for (VehicleLock lock : list) {
					if (!user.getYhm().equals(lock.getSdr())) {
						flag = true;
						break;
					}
				}
				if (flag) {
					return ResultHandler.toErrorJSON("车辆已被锁定，不能保存查验数据！");
				} else {
					// 查验合格，则解锁
					if ("1".equals(policeCheckInfo.getCyjg())) {
						for (VehicleLock lock : list) {
							lock.setJsr(user.getYhm());
							lock.setJssj(new Date());
							lock.setSdzt("0");
							lock.setJsyy((ywlx) + sdf.format(new Date()) + "查验合格");
							this.vehicleLockManager.save(lock);
						}
					}
				}
			} else {
				// 车辆没有被锁定，如果查验不合格，写入机动车业务锁定表 1：合格，0：不合格
				if ("0".equals(policeCheckInfo.getCyjg())) {

					VehicleLock vehicleLock = new VehicleLock();
					vehicleLock.setSdr(user.getYhm());
					vehicleLock.setSdsj(new Date());
					vehicleLock.setSdzt("1");
					vehicleLock.setClsbdh(policeCheckInfo.getClsbdh());
					vehicleLock.setSdyy((ywlx) + sdf.format(new Date()) + "查验不合格");
					this.vehicleLockManager.save(vehicleLock);
				}
			}

			policeCheckInfoManager.save(policeCheckInfo);
			// 注册登记（A） 并且 查验结果是合格，则上传预录入信息到综合平台
			if (policeCheckInfo.getLsh() != null && "A".equals(ywlx) && "1".equals(policeCheckInfo.getCyjg())) {
				// 上传预录入信息到综合平台
				uploadPlatForm(policeCheckInfo);

				// 上传图片
				VehiclePhotos photo = this.vehiclePhotosManager.findVehiclePhotosByLsh(policeCheckInfo.getLsh());
				xrzp(photo);
			}
			return ResultHandler.resultHandle(result, policeCheckInfo, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	public JSONObject getImageMap(VehiclePhotos photo) {
		Map imgMap = new HashMap();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String hpzl = photo.getHpzl();

		String lic = photo.getClsbdh();

		String hphm = photo.getHphm();

		String zp = photo.getPhoto().toString();

		String gxsj = sd.format(new Date());

		imgMap.put("hpzl", hpzl);

		imgMap.put("clsbdh", lic);

		if (hphm != null) {
			imgMap.put("hphm", hphm);
		}

		imgMap.put("zp", zp);
		imgMap.put("gxsj", gxsj);
		System.out.println(JSONObject.fromObject(imgMap));
		return JSONObject.fromObject(imgMap);
	}

	public String xrzp(VehiclePhotos photo) {

		JSONObject map = getImageMap(photo);
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			// TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo =
			// tmriJaxRpcOutService.createQueryObjectOut();
			TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = tmriJaxRpcOutService.createWriteObjectOut();

			Document document = DocumentHelper.createDocument(); // 创建文档

			document.setXMLEncoding("UTF-8");

			Element veh = document.addElement("root").addElement("veh");

			JSONConvertXML(veh, map);

			wo.setUTF8XmlDoc(document.asXML());
			wo.setJkid("01C80");

			return urlDecode(trias.writeObjectOut(wo).getWriteObjectOutReturn());
		} catch (Exception e) {
			throw new ApplicationException("上传图片到综合平台异常", e);
		}

	}

	private void uploadPlatForm(VehCheckInfo policeCheckInfo) throws AxisFault {
		TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
		// TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo =
		// tmriJaxRpcOutService.createQueryObjectOut();
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = tmriJaxRpcOutService.createWriteObjectOut();
		PreCarRegister carRegister = this.preCarRegisterManager.findPreCarRegisterByLsh(policeCheckInfo.getLsh());
		JSONObject jo = JSONObject.fromObject(carRegister);
		String xh = getNewCarSeq();

		jo.put("XH", xh);

		jo.remove("EWM");
		jo.remove("C_ID");
		jo.remove("C_UPDATEDUSER");
		jo.remove("C_CREATEUSER");
		jo.remove("C_UPDATEDDATE");
		jo.remove("C_CREATEDDATE");
		jo.remove("C_DATASTATE");
		jo.remove("CSYS");
		jo.put("SFZMHM", jo.remove("SFZ"));
		jo.put("GCJK", "A");
		String bh = (String) jo.remove("GGBH");
		jo.put("BH", bh);
		jo.remove("BZ");
		String cyry = (String) policeCheckInfo.getCyr();

		String zzg = (String) jo.get("ZZG");
		System.out.println("zzg " + bh);
		if ((zzg == null || "".equals(zzg)) && bh != null && !"".equals(bh)) {
			List<Map<String, Object>> gglist = pDAServiceManager.findPcbStVehicle(bh);
			if (gglist != null && gglist.size() > 0) {
				JSONObject gonggao = JSONObject.fromObject(gglist.get(0));
				System.out.println("ggzzg " + gonggao.get("ZZG"));
				jo.put("ZZG", gonggao.get("ZZG"));
			}

		}

		if (jo.get("SYXZ") == null) {
			jo.put("SYXZ", policeCheckInfo.getSyxz());
		}

		String rst6 = (String) policeCheckInfo.getJy6();

		if (rst6 != null) {
			String cllx = rst6.split(" ")[0];
			jo.put("CLLX", cllx);
		}

		// System.out.println(jo);
		String rst4 = (String) policeCheckInfo.getJy4();

		if (rst4 != null) {
			String csys = rst4.split(" ")[0];
			csys = csys.replace(",", "");
			jo.put("CSYS", csys);
		}

		if (cyry != null) {
			jo.put("CYRY", cyry);
		}

		Document document = DocumentHelper.createDocument(); // 创建文档

		document.setXMLEncoding("UTF-8");

		Element veh = document.addElement("root").addElement("veh");

		veh = JSONConvertXML(veh, jo);

		wo.setUTF8XmlDoc(document.asXML());
		// System.out.println("bo="+document.asXML());
		wo.setJkid("01C77");

		try {
			urlDecode(trias.writeObjectOut(wo).getWriteObjectOutReturn());
		} catch (Exception e) {
			throw new ApplicationException("上传预录入信息到综合平台异常", e);
		}
	}

	public static Element JSONConvertXML(Element e, JSONObject jo) {

		Set<String> keySet = jo.keySet();
		for (String key : keySet) {
			if (!"".equals(jo.getString(key))) {
				Element node = e.addElement(key.toLowerCase());
				node.setText(urlEncode(jo.getString(key)));
			}
		}
		return e;

	}

	public static String urlDecode(String str) {
		String xml = "";

		try {
			xml = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new ApplicationException(e);
		}

		return xml;
	}

	public static String urlEncode(String str) {
		String newStr = "";

		try {
			newStr = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			throw new ApplicationException(e);
		}

		return newStr;
	}

	// 获取机动车心得序号
	public String getNewCarSeq() {
		String seq = null;
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
			qo.setJkid("01C23");
			qo.setJkxlh(
					"7F1C0909010517040815E3FF83F5F3E28BCC8F9B818DE7EA88DFD19EB8C7D894B9B9BCE0BFD8D6D0D0C4A3A8D0C5CFA2BCE0B9DCCFB5CDB3A3A9");
			qo.setUTF8XmlDoc("<root><QueryCondition><glbm>320900000400</glbm></QueryCondition></root>");
			qo.setXtlb("01");
			qo.setDwmc("盐城市公安局交通警察支队车辆管理所");
			qo.setDwjgdm("320900000400");
			qo.setYhbz("");
			qo.setYhxm("");
			qo.setZdbs("10.39.147.6");

			String returnXML1 = trias.queryObjectOut(qo).getQueryObjectOutReturn();
			Document doc = DocumentHelper.parseText(returnXML1);
			Element root = doc.getRootElement();
			seq = root.element("body").element("veh").element("xh").getText();

		} catch (Exception e) {
			throw new ApplicationException("获取机动车序号异常", e);
		}
		return seq;
	}

	@RequestMapping(value = "findPreCarRegisterByLsh", method = RequestMethod.POST)
	public @ResponseBody PreCarRegister findPreCarRegisterByLsh(String lsh) {
		PreCarRegister carInfo = preCarRegisterManager.findPreCarRegisterByLsh(lsh);
		if (carInfo != null) {
			carInfo.setSdzt(vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(carInfo.getClsbdh()));
		}
		return carInfo;
	}

	@UserOperation(code = "getCarInfoByCarNumber", name = "获取基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> getCarInfoByCarNumber(String hpzl, String hphm) {
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null || "".equals(hphm.trim())) {
				return dataMap;
			}
			qo.setJkid("01C21");
			qo.setUTF8XmlDoc(
					"<root><QueryCondition><hphm>" + hphm + "</hphm><hpzl>" + hpzl + "</hpzl></QueryCondition></root>");

			String returnXML = trias.queryObjectOut(qo).getQueryObjectOutReturn();
			String xml = URLCodeUtil.urlDecode(returnXML);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			Element dataElecmet = root.element("body").element("veh");

			if (dataElecmet != null) {
				for (Object o : dataElecmet.elements()) {
					Element element = (Element) o;
					String key = element.getName();
					String value = element.getText();
					dataMap.put(key, value);
					if (key.equals("clsbdh")) {
						boolean sdzt = vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(value);
						dataMap.put("sdzt", String.valueOf(sdzt));
					}
				}
			}

		} catch (Exception e) {
			throw new ApplicationException("获取基础信息异常", e);
		}
		return dataMap;
	}

	@UserOperation(code = "getGongGaoInfoByGgbh", name = "获取公告信息")
	@RequestMapping(value = "getGongGaoInfoByGgbh", method = RequestMethod.POST)
	public @ResponseBody Map getGongGaoInfoByGgbh(String ggbh) {
		Map map = new HashMap();
		if (ggbh == null || "".equals(ggbh.trim())) {
			return map;
		}

		List<Map<String, Object>> list = pDAServiceManager.findPcbStVehicle(ggbh);

		Map<String, Object> rMap = (Map) list.get(0);

		Map returnMap = new HashMap();

		for (String key : rMap.keySet()) {
			returnMap.put(key.toLowerCase(), rMap.get(key));
		}

		return returnMap;

	}

	@UserOperation(code = "getGongGaoListbyCLXH", name = "获取公告列表")
	@RequestMapping(value = "getGongGaoListbyCLXH", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getGongGaoListbyCLXH(String clxh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (clxh != null) {
			list = pDAServiceManager.findGongGaoListbyCLXH(clxh);

		}
		return list;
	}

	private void convertData(List list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (Object o : list) {
			Map map = (Map) o;
			Date date1 = (Date) map.get("GGRQ");
			Date date2 = (Date) map.get("GGSXRQ");
			Date date3 = (Date) map.get("TZSCRQ");
			if (date1 != null) {
				map.put("GGRQ", sdf.format(date1));
			}
			if (date2 != null) {
				map.put("GGSXRQ", sdf.format(date2));
			}
			if (date3 != null) {
				map.put("TZSCRQ", sdf.format(date3));
			}
			if (map.get("ZXXS") != null) {
				map.put("ZXXS", CommonUtil.convertCode("zxxs", map.get("ZXXS").toString(), request));
			}
			if (map.get("RLZL") != null) {
				map.put("RLZL", CommonUtil.convertCode("rlzl", map.get("RLZL").toString(), request));
			}
		}
	}

	// 获取地盘公告
	@UserOperation(code = "getListOfDPGG", name = "获取地盘公告列表")
	@RequestMapping(value = "getListOfDPGG", method = RequestMethod.POST)
	public List<Map<String, Object>> getListOfDPGG(String dpid) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(dpid)) {
			list = pDAServiceManager.getListOfDPGG(dpid);
			convertData(list);
		}
		return list;

	}

	// 获取地盘公告
	@UserOperation(code = "getDPGG", name = "获取地盘公告信息")
	@RequestMapping(value = "getDPGG", method = RequestMethod.POST)
	public List<Map<String, Object>> getDPGG(String bh) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(bh)) {
			list = pDAServiceManager.getDPGG(bh);

			convertData(list);
		}
		return list;

	}

	@UserOperation(code = "uploadFile", name = "上传图片")
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadFile(VehiclePhotos motorVehiclePhotos, MultipartFile file,
			@ApiIgnore() BindingResult result) throws Exception {
		User user = (User) session.getAttribute("user");
		// 校验车辆是否被锁定，如果被锁定则不能保存(如果当前用户是锁定人则可以保存)
		List<VehicleLock> list = vehicleLockManager.findLockVehicle(motorVehiclePhotos.getClsbdh());
		if (list != null && list.size() > 0) {
			boolean flag = false;
			for (VehicleLock lock : list) {
				if (!user.getYhm().equals(lock.getSdr())) {
					flag = true;
					break;
				}
			}
			if (flag) {
				return ResultHandler.toErrorJSON("车辆已被锁定，不能上传图片！");
			}
		}
		motorVehiclePhotos.setPhoto(file.getBytes());
		vehiclePhotosManager.save(motorVehiclePhotos);
		return ResultHandler.resultHandle(result, motorVehiclePhotos, Constant.ConstantMessage.SAVE_SUCCESS);
	}

	@RequestMapping(value = "findLockMotorVehicleByClsbdh", method = RequestMethod.POST)
	public @ResponseBody boolean findLockMotorVehicleByClsbdh(String clsbdh) throws Exception {

		return vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(clsbdh);
	}

}
