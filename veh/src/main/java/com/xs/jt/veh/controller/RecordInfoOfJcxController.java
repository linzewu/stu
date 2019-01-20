package com.xs.jt.veh.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.common.DocumentHelpers;
import com.xs.jt.veh.entity.RecordInfoOfJcx;
import com.xs.jt.veh.manager.IRecordInfoOfJcxManager;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.xml.XMLSerializer;

@Controller
@RequestMapping(value = "/recordInfoOfJcx")
@Modular(modelCode = "RecordInfoOfJcx", modelName = "检测线信息", isEmpowered = false)
public class RecordInfoOfJcxController {

	@Resource(name = "recordInfoOfJcxManager")
	private IRecordInfoOfJcxManager recordInfoOfJcxManager;

	@Autowired
	private DocumentHelpers documentHelpers;

	@UserOperation(code = "getInfo", name = "检测线信息查询")
	@RequestMapping(value = "getInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSystemInfo(@RequestParam Map param) {

		RecordInfoOfJcx jcx = recordInfoOfJcxManager.getRecordInfoOfJcx();

		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put("rows", getRows(jcx));

		return rm;
	}

	@UserOperation(code = "downloadInfo", name = "检验机构信息下载")
	@RequestMapping(value = "downloadInfo", method = RequestMethod.POST)
	public Map<String, Object> downloadInfo() throws RemoteException, UnsupportedEncodingException, DocumentException {

		Map param = new HashMap();
		Document document = documentHelpers.queryws("18C02", param);
		JSON json = new XMLSerializer().read(document.asXML());
		JSONObject jo = (JSONObject) ((JSONObject) json).getJSONArray("body").get(0);

//		jo.put("id", jo.getString("@id"));
//		jo.discard("@id").discard("bz").discard("ztyy").discard("gw7").discard("gw8").discard("gw9").discard("gtszdttjfs")
//		.discard("gtszdtzs").discard("qycwqsbmc");
//		jo.put("bz", "");
//		jo.put("ztyy", "");
//		jo.put("gw7", "");
//		jo.put("gw8", "");
//		jo.put("gw9", "");

		Iterator<String> it = jo.keys();
		while (it.hasNext()) {
			String key = it.next();
			String value = jo.getString(key);
			if ("[]".equals(value)) {
				jo.put(key, "");
			}
		}
		jo.put("id", jo.getString("@id"));
		jo.discard("@id");

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(RecordInfoOfJcx.class);
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				if (value instanceof Date) {
					return sdf.format((Date) value);
				}
				return value;
			}

			@Override
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return value;
			}
		});
		System.out.println(jo);
		recordInfoOfJcxManager.deleteRecordInfo();
		RecordInfoOfJcx recordInfoOfJcx = (RecordInfoOfJcx) JSONObject.toBean(jo, jsonConfig);

		recordInfoOfJcxManager.saveRecordInfoOfJcx(recordInfoOfJcx);

		return ResultHandler.toSuccessJSON("下载成功");
	}

	private List<Map<String, String>> getRows(RecordInfoOfJcx check) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		Class cls = check.getClass();
		Field[] fields = cls.getDeclaredFields();
		String[] jcxSm = { "检验机构编号", "检测线代号", "检验机构名称", "检测线名称", "检测线类别", "检测线控制方式", "制动检测设备名称", "制动检测设备型号",
				"制动检测设备生产厂家", "制动检测最少时间", "制动检测方式", "平板制式", "单平板长度", "平板间距", "滚筒式制动台制式", "滚筒式制动台停机方式", "制动检测设备启用时间",
				"制动检测设备检定有效期止", "制动检测设备状态", "灯光检测设备名称", "灯光检测设备型号", "灯光检测设备生产厂家", "灯光检测最少时间", "灯光检测方式",
				"灯光检测是否有车身偏移修正功能", "灯光检测设备启用时间", "灯光检测设备检定有效期止", "灯光检测设备状态", "速度检测设备名称", "速度检测设备型号", "速度检测设备生产厂家",
				"速度检测最少时间", "速度检测设备启用时间", "速度检测设备检定有效期止", "速度检测设备状态", "侧滑检测设备名称", "侧滑检测设备型号", "侧滑检测设备生产厂家", "侧滑检测最少时间",
				"侧滑检测设备启用时间", "侧滑检测设备检定有效期止", "侧滑检测设备状态", "称重设备名称", "称重检测设备型号", "称重检测设备生产厂家", "称重检测最少时间", "称重范围",
				"称重检测设备检定有效期止", "称重检测设备启用时间", "称重检测设备状态", "全线检测时间", "工位1", "工位2", "工位3", "工位4", "工位5", "工位6", "工位7",
				"工位8", "工位9", "备注", "发证机关", "管理部门", "更新日期", "制动检验设备编号", "制动检验设备检定/校准证书编号", "灯光检验设备编号",
				"灯光检测设备检定/校准证书标号", "速度检验设备编号", "速度检验设备检定/校准证书标号", "侧滑检验设备编号", "侧滑检验设备检定/校准证书编号", "称重检验设备编号",
				"称重检验设备检定/校准证书编号", "状态标记", "暂停原因" };
		try {
			for (int i = 1; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				Map<String, String> sm = new HashMap<String, String>();
				sm.put("name", jcxSm[i - 1]);
				if (f.get(check) instanceof String) {
					sm.put("value", (String) (f.get(check)));
				} else if (f.get(check) instanceof Integer) {
					sm.put("value", String.valueOf(f.get(check)));
				} else if (f.get(check) instanceof Date) {
					sm.put("value", sdf.format(f.get(check)));
				} else {
					sm.put("value", "");
				}
				rows.add(sm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

}
