package com.xs.jt.veh.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.common.DocumentHelpers;
import com.xs.jt.veh.entity.RecordInfoOfCheck;
import com.xs.jt.veh.manager.IRecordInfoOfCheckManager;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.xml.XMLSerializer;

@Controller
@RequestMapping(value = "/recordInfoOfCheck")
@Modular(modelCode = "RecordInfoOfCheck", modelName = "检验机构信息", isEmpowered = false)
public class RecordInfoOfCheckController {

	@Resource(name = "recordInfoOfCheckManager")
	private IRecordInfoOfCheckManager recordInfoOfCheckManager;

	@Autowired
	private DocumentHelpers documentHelpers;

	@UserOperation(code = "getInfo", name = "检验机构信息查询")
	@RequestMapping(value = "getInfo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getSystemInfo(@RequestParam Map param) {

		RecordInfoOfCheck check = recordInfoOfCheckManager.getRecordInfoOfCheckInfo();

		Map<String, Object> rm = new HashMap<String, Object>();
		rm.put("rows", getRows(check));

		return rm;
	}

	@UserOperation(code = "downloadInfo", name = "检验机构信息下载")
	@RequestMapping(value = "downloadInfo", method = RequestMethod.POST)
	public Map<String, Object> downloadInfo() throws RemoteException, UnsupportedEncodingException, DocumentException {

		Map param = new HashMap();
		Document document = documentHelpers.queryws("18C01", param);
		JSON json = new XMLSerializer().read(document.asXML());
		JSONObject jo = (JSONObject) ((JSONObject) json).getJSONArray("body").get(0);
//		jo.put("id", jo.getString("@id"));
//		jo.discard("@id").discard("bz").discard("ztyy");
//		jo.put("bz", "");
//		jo.put("ztyy", "");
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
		jsonConfig.setRootClass(RecordInfoOfCheck.class);
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

		recordInfoOfCheckManager.deleteRecordInfo();
		RecordInfoOfCheck recordInfoOfCheck = (RecordInfoOfCheck) JSONObject.toBean(jo, jsonConfig);
		recordInfoOfCheckManager.saveRecordInfoOfCheckInfo(recordInfoOfCheck);

		return ResultHandler.toSuccessJSON("下载成功");
	}

	private List<Map<String, String>> getRows(RecordInfoOfCheck check) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();

		Map<String, String> sm1 = new HashMap<String, String>();
		// 加載配置信息
		sm1.put("name", "检验机构编号");
		sm1.put("value", check.getJczbh());
		rows.add(sm1);

		Map<String, String> sm2 = new HashMap<String, String>();
		sm2.put("name", "检验机构名称");
		sm2.put("value", check.getJczmc());
		rows.add(sm2);

		Map<String, String> sm3 = new HashMap<String, String>();
		sm3.put("name", "是否与公安网联网");
		sm3.put("value", check.getSflw());
		rows.add(sm3);

		Map<String, String> sm4 = new HashMap<String, String>();
		sm4.put("name", "资格许可证书编号");
		sm4.put("value", check.getRdsbh());
		rows.add(sm4);

		Map<String, String> sm5 = new HashMap<String, String>();
		sm5.put("name", "资格许可有效期始");
		sm5.put("value", StringUtils.isEmpty(check.getRdyxqs()) ? "" : sdf.format(check.getRdyxqs()));
		rows.add(sm5);

		Map<String, String> sm6 = new HashMap<String, String>();
		sm6.put("name", "资格许可有效期止");
		sm6.put("value", StringUtils.isEmpty(check.getRdyxqz()) ? "" : sdf.format(check.getRdyxqz()));
		rows.add(sm6);

		Map<String, String> sm7 = new HashMap<String, String>();
		sm7.put("name", "设计日检测能力(汽车辆)");
		sm7.put("value", String.valueOf(check.getShejirjcnl()));
		rows.add(sm7);

		Map<String, String> sm8 = new HashMap<String, String>();
		sm8.put("name", "实际日检测能力(汽车辆)");
		sm8.put("value", String.valueOf(check.getShijirjcnl()));
		rows.add(sm8);

		Map<String, String> sm9 = new HashMap<String, String>();
		sm9.put("name", "检测人员总数");
		sm9.put("value", String.valueOf(check.getJcryzs()));
		rows.add(sm9);

		Map<String, String> sm10 = new HashMap<String, String>();
		sm10.put("name", "外检工位人数");
		sm10.put("value", String.valueOf(check.getWjgwrs()));
		rows.add(sm10);

		Map<String, String> sm11 = new HashMap<String, String>();
		sm11.put("name", "录入工位人数");
		sm11.put("value", String.valueOf(check.getLrgwrs()));
		rows.add(sm11);

		///////////////////////////////////////////////
		Map<String, String> sm12 = new HashMap<String, String>();
		sm12.put("name", "引车员人数");
		sm12.put("value", String.valueOf(check.getYcyrs()));
		rows.add(sm12);

		Map<String, String> sm13 = new HashMap<String, String>();
		sm13.put("name", "底盘工位人数");
		sm13.put("value", String.valueOf(check.getDpgwrs()));
		rows.add(sm13);

		Map<String, String> sm14 = new HashMap<String, String>();
		sm14.put("name", "总检工位人数");
		sm14.put("value", String.valueOf(check.getZjgwrs()));
		rows.add(sm14);

		Map<String, String> sm15 = new HashMap<String, String>();
		sm15.put("name", "其他工位人数");
		sm15.put("value", String.valueOf(check.getQtgwrs()));
		rows.add(sm15);

		Map<String, String> sm16 = new HashMap<String, String>();
		sm16.put("name", "通过省级质检部门考核人数");
		sm16.put("value", String.valueOf(check.getTgszjbmkhrs()));
		rows.add(sm16);

		Map<String, String> sm17 = new HashMap<String, String>();
		sm17.put("name", "未通过省级质检部门考核人数");
		sm17.put("value", String.valueOf(check.getWtgszjbmkhrs()));
		rows.add(sm17);

		Map<String, String> sm18 = new HashMap<String, String>();
		sm18.put("name", "发证机关");
		sm18.put("value", check.getFzjg());
		rows.add(sm18);

		Map<String, String> sm19 = new HashMap<String, String>();
		sm19.put("name", "管理部门");
		sm19.put("value", check.getGlbm());
		rows.add(sm19);

		Map<String, String> sm20 = new HashMap<String, String>();
		sm20.put("name", "更新日期");
		sm20.put("value", StringUtils.isEmpty(check.getGxrq()) ? "" : sdf.format(check.getGxrq()));
		rows.add(sm20);

		Map<String, String> sm21 = new HashMap<String, String>();
		sm21.put("name", "备注");
		sm21.put("value", check.getBz());
		rows.add(sm21);

		Map<String, String> sm22 = new HashMap<String, String>();
		sm22.put("name", "设计日检测能力(摩托辆)");
		sm22.put("value", String.valueOf(check.getShejirjcmtsl()));
		rows.add(sm22);

		Map<String, String> sm23 = new HashMap<String, String>();
		sm23.put("name", "实际日检测能力(摩托辆)");
		sm23.put("value", String.valueOf(check.getShijirjcmtsl()));
		rows.add(sm23);

		Map<String, String> sm24 = new HashMap<String, String>();
		sm24.put("name", "审核标记");
		sm24.put("value", check.getShbj());
		rows.add(sm24);

		Map<String, String> sm25 = new HashMap<String, String>();
		sm25.put("name", "使用管理部门");
		sm25.put("value", check.getSyglbm());
		rows.add(sm11);

		Map<String, String> sm26 = new HashMap<String, String>();
		sm26.put("name", "审核意见");
		sm26.put("value", check.getShyj());
		rows.add(sm26);

		Map<String, String> sm27 = new HashMap<String, String>();
		sm27.put("name", "状态标记 ");
		sm27.put("value", check.getZt());
		rows.add(sm27);

		Map<String, String> sm28 = new HashMap<String, String>();
		sm28.put("name", "暂停原因");
		sm28.put("value", check.getZtyy());
		rows.add(sm28);

		Map<String, String> sm29 = new HashMap<String, String>();
		sm29.put("name", "单位地址");
		sm29.put("value", check.getDwdz());
		rows.add(sm29);

		Map<String, String> sm30 = new HashMap<String, String>();
		sm30.put("name", "邮政编码");
		sm30.put("value", check.getYzbm());
		rows.add(sm30);

		Map<String, String> sm31 = new HashMap<String, String>();
		sm31.put("name", "许可检验范围");
		sm31.put("value", check.getXkjyfw());
		rows.add(sm31);

		Map<String, String> sm32 = new HashMap<String, String>();
		sm32.put("name", "资格许可发放单位");
		sm32.put("value", check.getRdsffdw());
		rows.add(sm32);

		Map<String, String> sm33 = new HashMap<String, String>();
		sm33.put("name", "法人代表");
		sm33.put("value", check.getFrdb());
		rows.add(sm33);

		Map<String, String> sm34 = new HashMap<String, String>();
		sm34.put("name", "法人代表身份证号");
		sm34.put("value", check.getFrdbsfzh());
		rows.add(sm34);

		Map<String, String> sm35 = new HashMap<String, String>();
		sm35.put("name", "法人代表联系电话");
		sm35.put("value", check.getFrdblxdh());
		rows.add(sm35);

		Map<String, String> sm36 = new HashMap<String, String>();
		sm36.put("name", "负责人");
		sm36.put("value", check.getFzr());
		rows.add(sm36);

		Map<String, String> sm37 = new HashMap<String, String>();
		sm37.put("name", "负责人身份证号");
		sm37.put("value", check.getFzrsfzh());
		rows.add(sm37);

		Map<String, String> sm38 = new HashMap<String, String>();
		sm38.put("name", "负责人联系电话");
		sm38.put("value", check.getFzrlxdh());
		rows.add(sm38);

		Map<String, String> sm39 = new HashMap<String, String>();
		sm39.put("name", "日常联系人");
		sm39.put("value", check.getRclxr());
		rows.add(sm39);

		Map<String, String> sm40 = new HashMap<String, String>();
		sm40.put("name", "日常联系人身份证号");
		sm40.put("value", check.getRclxrsfzh());
		rows.add(sm40);

		Map<String, String> sm41 = new HashMap<String, String>();
		sm41.put("name", "日常联系人联系电话");
		sm41.put("value", check.getRclxrlxdh());
		rows.add(sm41);

		return rows;
	}

}
