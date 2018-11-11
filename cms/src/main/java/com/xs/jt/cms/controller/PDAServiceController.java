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
import org.springframework.beans.factory.annotation.Value;
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
import com.xs.jt.cms.entity.PreCarRegister;
import com.xs.jt.cms.entity.VehCheckInfo;
import com.xs.jt.cms.entity.VehicleLock;
import com.xs.jt.cms.entity.VehiclePhotos;
import com.xs.jt.cms.manager.IGongGaoImageManager;
import com.xs.jt.cms.manager.IPDAServiceManager;
import com.xs.jt.cms.manager.IPreCarRegisterManager;
import com.xs.jt.cms.manager.IVehCheckInfoManager;
import com.xs.jt.cms.manager.IVehicleLockManager;
import com.xs.jt.cms.manager.IVehiclePhotosManager;

import net.sf.json.JSONObject;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "/pdaService")
@Modular(modelCode = "pdaService", modelName = "PDA对外接口")
public class PDAServiceController {
	
	@Value("${stu.properties.glbm}")
	private String glbm;

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
	
	@Autowired
	private IGongGaoImageManager gongGaoImageManager;

	public static String YWLX_TYPE = "check.ywlx";

	@UserOperation(code = "addVehCheckInfo", name = "保存查验信息")
	@RequestMapping(value = "addVehCheckInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addVehCheckInfo(VehCheckInfo vehCheckInfo, BindingResult result)
			throws Exception {
		User user = (User) session.getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
		vehCheckInfo.setCysj(new Date());
		BaseParams baseParam = baseParamsManager.getBaseParam(YWLX_TYPE, vehCheckInfo.getYwlx());
		String ywlx = baseParam == null ? "" : baseParam.getParamValue();
		if (!result.hasErrors()) {
			// 校验车辆是否被锁定，如果被锁定则不能保存(如果当前用户是锁定人则可以保存)
			List<VehicleLock> list = vehicleLockManager.findLockVehicle(vehCheckInfo.getClsbdh());
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
					if ("1".equals(vehCheckInfo.getCyjg())) {
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
				if ("0".equals(vehCheckInfo.getCyjg())) {
					VehicleLock vehicleLock = new VehicleLock();
					vehicleLock.setSdr(user.getYhm());
					vehicleLock.setSdsj(new Date());
					vehicleLock.setSdzt("1");
					vehicleLock.setClsbdh(vehCheckInfo.getClsbdh());
					vehicleLock.setSdyy((ywlx) + sdf.format(new Date()) + "查验不合格");
					vehicleLock.setSdfs(VehicleLock.SDFS_AUTO);
					this.vehicleLockManager.save(vehicleLock);
				}
			}
			
			//获取查验次数
			Integer cycs = this.policeCheckInfoManager.findMaxCsByLsh(vehCheckInfo.getLsh());
			cycs = cycs == null?0:cycs;
			cycs++;
			vehCheckInfo.setCycs(cycs);

			policeCheckInfoManager.save(vehCheckInfo);
			// 注册登记（A） 并且 查验结果是合格，则上传预录入信息到综合平台
			if (vehCheckInfo.getLsh() != null && "A".equals(ywlx) && "1".equals(vehCheckInfo.getCyjg())) {
				// 上传预录入信息到综合平台
				uploadPlatForm(vehCheckInfo);
				// 上传图片
				VehiclePhotos photo = this.vehiclePhotosManager.findLast45degPhotosByLsh(vehCheckInfo.getLsh());
				xrzp(photo);
			}
			return ResultHandler.resultHandle(result, vehCheckInfo, Constant.ConstantMessage.SAVE_SUCCESS);
		} else {
			return ResultHandler.resultHandle(result, null, null);
		}
	}

	public JSONObject getImageMap(VehiclePhotos photo) {
		Map<String,String> imgMap = new HashMap<String,String>();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String hpzl = photo.getHpzl();

		String clsbdh = photo.getClsbdh();

		String hphm = photo.getHphm();

		String zp = org.springframework.util.Base64Utils.encodeToString(photo.getPhoto());

		String gxsj = sd.format(new Date());

		imgMap.put("hpzl", hpzl);

		imgMap.put("clsbdh", clsbdh);

		if (hphm != null) {
			imgMap.put("hphm", hphm);
		}
		imgMap.put("zp", zp);
		imgMap.put("gxsj", gxsj);
		return JSONObject.fromObject(imgMap);
	}

	public String xrzp(VehiclePhotos photo) {

		JSONObject map = getImageMap(photo);
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
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

	private void uploadPlatForm(VehCheckInfo vehCheckInfo) throws AxisFault {
		TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = tmriJaxRpcOutService.createWriteObjectOut();
		PreCarRegister carRegister = this.preCarRegisterManager.findPreCarRegisterByLsh(vehCheckInfo.getLsh());
		JSONObject jo = JSONObject.fromObject(carRegister);
		String xh = getNewCarSeq();
		jo.put("XH", xh);
		jo.put("SFZMHM", jo.remove("SFZ"));
		jo.put("GCJK", "A");
		String bh = (String) jo.remove("GGBH");
		jo.put("BH", bh);
		jo.remove("BZ");
		String cyry = (String) vehCheckInfo.getCyr();
		String zzg = (String) jo.get("ZZG");
		if ((zzg == null || "".equals(zzg)) && bh != null && !"".equals(bh)) {
			List<Map<String, Object>> gglist = pDAServiceManager.findPcbStVehicle(bh);
			if (gglist != null && gglist.size() > 0) {
				JSONObject gonggao = JSONObject.fromObject(gglist.get(0));
				jo.put("ZZG", gonggao.get("ZZG"));
			}

		}
		jo.put("SYXZ", vehCheckInfo.getSyxz());
		jo.put("CLLX", vehCheckInfo.getCllx());
		jo.put("CSYS", vehCheckInfo.getCsys());

		if (cyry != null) {
			jo.put("CYRY", cyry);
		}

		Document document = DocumentHelper.createDocument(); // 创建文档
		document.setXMLEncoding("UTF-8");
		Element veh = document.addElement("root").addElement("veh");
		veh = JSONConvertXML(veh, jo);
		wo.setUTF8XmlDoc(document.asXML());
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
			qo.setUTF8XmlDoc("<root><QueryCondition><glbm>"+glbm+"</glbm></QueryCondition></root>");

			String returnXML1 = trias.queryObjectOut(qo).getQueryObjectOutReturn();
			Document doc = DocumentHelper.parseText(returnXML1);
			Element root = doc.getRootElement();
			seq = root.element("body").element("veh").element("xh").getText();

		} catch (Exception e) {
			throw new ApplicationException("获取机动车序号异常", e);
		}
		return seq;
	}

	@UserOperation(code = "findPreCarRegisterByLsh", name = "根据流水号查询车辆登记信息")
	@RequestMapping(value = "findPreCarRegisterByLsh", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> findPreCarRegisterByLsh(String lsh) {
		Map<String,Object> map = new HashMap<String,Object>();
		PreCarRegister carInfo = preCarRegisterManager.findPreCarRegisterByLsh(lsh);
		map.put("data", carInfo);
		if (carInfo != null) {
			List<VehicleLock> list = vehicleLockManager.findLockVehicle(carInfo.getClsbdh());
			if (list != null && list.size() > 0) {
				map.put("lockData", list);				
			}
			//carInfo.setSdzt(vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(carInfo.getClsbdh()));
		}
		return map;
	}

	/**
	 * 查询本地区车辆基本信息
	 * @param hpzl
	 * @param hphm
	 * @return
	 */
	@UserOperation(code = "getCarInfoByCarNumber", name = "根据号牌号码,号牌种类获取基础信息")
	@RequestMapping(value = "getCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getCarInfoByCarNumber(String hpzl, String hphm) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null || "".equals(hphm.trim())) {
				return map;
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
						//boolean sdzt = vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(value);
						//dataMap.put("sdzt", String.valueOf(sdzt));
						List<VehicleLock> list = vehicleLockManager.findLockVehicle(value);
						if (list != null && list.size() > 0) {
							map.put("lockData", list);				
						}
					}
				}
				map.put("data", dataMap);
			}

		} catch (Exception e) {
			throw new ApplicationException("获取基础信息异常", e);
		}
		return map;
	}
	
	/**
	 * 查询全国车辆信息
	 * @param hpzl
	 * @param hphm
	 * @param sf
	 * @return
	 */
	@UserOperation(code = "getAllCarInfoByCarNumber", name = "根据号牌号码,号牌种类获取全国车辆信息")
	@RequestMapping(value = "getAllCarInfoByCarNumber", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAllCarInfoByCarNumber(String hpzl,String hphm,String sf) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
			TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();

			System.out.println("sf"+sf);
			if(sf!=null){
				sf=URLEncoder.encode(sf, "UTF-8");
				
			}
			System.out.println("sf"+sf);
			
			

			if (hpzl == null || "".equals(hpzl.trim()) || hphm == null
					|| "".equals(hphm.trim())) {
				return map;
			}

			qo.setJkid("01C49");
			qo.setJkxlh("7F1C0909010517040815E3FF83F5F3E28BCC8F9B818DE7EA88DFD19EB8C7D894B9B9BCE0BFD8D6D0D0C4A3A8D0C5CFA2BCE0B9DCCFB5CDB3A3A9");
			qo.setUTF8XmlDoc("<root><QueryCondition><hphm>" + hphm
					+ "</hphm><hpzl>" + hpzl
					+ "</hpzl><sf>"+sf+"</sf></QueryCondition></root>");
			qo.setXtlb("01");

			String returnXML = trias.queryObjectOut(qo)
					.getQueryObjectOutReturn();
			String xml = URLCodeUtil.urlDecode(returnXML);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			System.out.println(root.asXML());
			Element dataElecmet = root.element("body").element("veh");

			if (dataElecmet != null) {
				
				for (Object o : dataElecmet.elements()) {
					Element element = (Element) o;
					String key = element.getName();
					String value = element.getText();
					
					dataMap.put(key, CommonUtil.convertCode(key,value,request));
					if (key.equals("clsbdh")) {
						List<VehicleLock> list = vehicleLockManager.findLockVehicle(value);
						if (list != null && list.size() > 0) {
							map.put("lockData", list);				
						}
					}
				}
				String clxh= (String)dataMap.get("clxh");
				if(clxh!=null){
					String ggbh = this.pDAServiceManager.getFirstGGBH(clxh);
					dataMap.put("ggbh", ggbh);
				}				
				map.put("data", dataMap);
			}
			

		} catch (Exception e) {
			throw new ApplicationException("获取全国车辆信息异常", e);
		}
		return map;
	}

	@UserOperation(code = "getGongGaoInfoByGgbh", name = "获取公告信息")
	@RequestMapping(value = "getGongGaoInfoByGgbh", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getGongGaoInfoByGgbh(String ggbh) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (ggbh == null || "".equals(ggbh.trim())) {
			return map;
		}

		List<Map<String, Object>> list = pDAServiceManager.findPcbStVehicle(ggbh);

		Map<String, Object> rMap = list.get(0);

		Map<String, Object> returnMap = new HashMap<String, Object>();

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
	
	
	@UserOperation(code = "getAllGongGaoListbyCLXH", name = "获取完整公告列表")
	@RequestMapping(value = "getAllGongGaoListbyCLXH", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, Object>> getAllGongGaoListbyCLXH(String clxh) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (clxh != null) {
			list = pDAServiceManager.findAllGongGaoListbyCLXH(clxh);
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

	@UserOperation(code = "uploadImageFile", name = "上传图片")
	@RequestMapping(value = "uploadImageFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadImageFile(VehiclePhotos motorVehiclePhotos, MultipartFile file,
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
		//获取检验次数
		Integer cycs = this.policeCheckInfoManager.findMaxCsByLsh(motorVehiclePhotos.getLsh());
		cycs = cycs == null?0:cycs;
		cycs++;
		motorVehiclePhotos.setJccs(cycs);;
		motorVehiclePhotos.setPhoto(file.getBytes());
		vehiclePhotosManager.save(motorVehiclePhotos);
		return ResultHandler.toSuccessJSON("上传成功");
	}

	@RequestMapping(value = "findLockMotorVehicleByClsbdh", method = RequestMethod.POST)
	public @ResponseBody boolean findLockMotorVehicleByClsbdh(String clsbdh) throws Exception {

		return vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(clsbdh);
	}

	// 新车查验复检
	@UserOperation(code = "newCarRepeatCheck", name = "新车查验复检")
	@RequestMapping(value = "newCarRepeatCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> newCarRepeatCheck(String lsh) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag= false;
		VehCheckInfo checkInfo = this.policeCheckInfoManager.findBhgVehCheckInfoByLsh(lsh);
		//不合格
		if (checkInfo != null && "0".equals(checkInfo.getCyjg())) {
			flag = true;
		}
		if (!flag) {
			return map;
		} else {
			PreCarRegister register = this.preCarRegisterManager.findPreCarRegisterByLsh(lsh);
			map.put("data", register);
			//锁定信息
			List<VehicleLock> lockList = this.vehicleLockManager.findLockVehicle(checkInfo.getClsbdh());
			map.put("lockData", lockList);
			return map;
		}

	}

	// 在用车查验复检
	@UserOperation(code = "inUseCarRepeatCheck", name = "在用车查验复检")
	@RequestMapping(value = "inUseCarRepeatCheck", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> inUseCarRepeatCheck(String hphm, String hpzl) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> dataMap = new HashMap<String, String>();
		boolean flag = false;
		VehCheckInfo checkInfo = this.policeCheckInfoManager.findBhgVehCheckInfoByHphmHpzl(hphm, hpzl);
		//不合格
		if (checkInfo != null && "0".equals(checkInfo.getCyjg())) {
			flag = true;
		}
		if (!flag) {
			return map;
		} else {
			try {
				TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService
						.createTmriJaxRpcOutNewAccessServiceStub();
				TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = tmriJaxRpcOutService.createQueryObjectOut();
				qo.setJkid("01C21");
				qo.setUTF8XmlDoc("<root><QueryCondition><hphm>" + hphm + "</hphm><hpzl>" + hpzl
						+ "</hpzl></QueryCondition></root>");

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
//						if (key.equals("clsbdh")) {
//							boolean sdzt = vehicleLockManager.findMotorVehicleBusinessLockByClsbdh(value);
//							dataMap.put("sdzt", String.valueOf(sdzt));
//						}
					}
					dataMap.put("lsh", checkInfo.getLsh());
					dataMap.put("ywlx", checkInfo.getYwlx());
					map.put("data", dataMap);
				}
				
				List<VehicleLock> lockList = this.vehicleLockManager.findLockVehicle(checkInfo.getClsbdh());
				map.put("lockData", lockList);

			} catch (Exception e) {
				throw new ApplicationException("在用车查验复检异常", e);
			}

			return map;
		}

	}
	
	@UserOperation(code = "getGongGaoImagesByBh", name = "根据公告编号获取公告照片")
	@RequestMapping(value = "getGongGaoImagesByBh", method = RequestMethod.POST)
	public @ResponseBody List<String> getGongGaoImagesByBh(String bh) {
		List<String> imageList = new ArrayList<String>();
		try {
			imageList = gongGaoImageManager.getGongGaoImagesByBh(bh);
		} catch (Exception e) {
			throw new ApplicationException("根据公告编号获取公告照片异常", e);
		}
		return imageList;
	}

}
