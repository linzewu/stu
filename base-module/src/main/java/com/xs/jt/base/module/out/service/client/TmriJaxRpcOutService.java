package com.xs.jt.base.module.out.service.client;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;

import net.sf.json.JSONObject;

@Service
public class TmriJaxRpcOutService {

	@Autowired
	private IBaseParamsManager baseParamsManager;

	@Autowired
	private HttpSession session;

	@Value("${stu.properties.jkxlh}")
	private String jkxlh;

	@Value("${stu.properties.xtlb}")
	private String xtlb;

	@Value("${stu.properties.dwmc}")
	private String dwmc;

	@Value("${stu.properties.dwjgdm}")
	private String dwjgdm;

	@Value("${stu.properties.zdbs}")
	private String zdbs;
	
	@Value("${stu.properties.cjsqbh}")
	private String cjsqbh;

	public TmriJaxRpcOutNewAccessServiceStub createTmriJaxRpcOutNewAccessServiceStub() throws AxisFault {
		List<BaseParams> baseParams = baseParamsManager.getBaseParamsByType("ptip");
		if (!CollectionUtils.isEmpty(baseParams)) {
			TmriJaxRpcOutNewAccessServiceStub.IP = baseParams.get(0).getParamValue();
		} else {
			throw new ApplicationException("从数据字典中获取不到平台IP地址！");
		}
		return new TmriJaxRpcOutNewAccessServiceStub();
	}

	public TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew createQueryObjectOut() {
		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew();
		
		qo.setJkxlh(jkxlh);
		qo.setXtlb(xtlb);
		qo.setDwmc(dwmc);
		qo.setDwjgdm(dwjgdm);
		qo.setYhbz(yhbz);
		qo.setYhxm(yhxm);
		qo.setZdbs(zdbs);
		//qo.setCjsqbh(cjsqbh);      
		return qo;
	}

	public TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNew createWriteObjectOut() {

		String yhbz = "";
		String yhxm = "";
		if (session != null) {
			User user = (User) session.getAttribute("user");
			if (user != null) {
				yhbz = user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNew wo = new TmriJaxRpcOutNewAccessServiceStub.WriteObjectOutNew();
		wo.setJkxlh(jkxlh);
		wo.setXtlb(xtlb);
		wo.setDwmc(dwmc);
		wo.setDwjgdm(dwjgdm);
		wo.setYhbz(yhbz);
		wo.setYhxm(yhxm);
		wo.setZdbs(zdbs);
		//wo.setCjsqbh(cjsqbh);
		return wo;
	}

	public static String urlDecode(String str) {
		String xml = "";
		try {
			xml = URLDecoder.decode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	public static String urlEncode(String str) {
		String newStr = "";
		try {
			newStr = URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newStr;
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

}
