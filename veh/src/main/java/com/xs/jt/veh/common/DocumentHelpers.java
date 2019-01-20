package com.xs.jt.veh.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNewResponse;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutService;
import com.xs.jt.veh.util.BeanXMLUtil;
import com.xs.jt.veh.util.RCAConstant;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

@Component("documentHelpers")
public class DocumentHelpers {

	@Value("${jyjgbh}")
	private String jyjgbh;

	@Value("${jkxlh}")
	private String jkxlh;

	@Autowired
	private TmriJaxRpcOutService tmriJaxRpcOutService;

	public Document queryws(String jkid, Map param)
			throws RemoteException, UnsupportedEncodingException, DocumentException {

		TmriJaxRpcOutNewAccessServiceStub trias = tmriJaxRpcOutService.createTmriJaxRpcOutNewAccessServiceStub();
		TmriJaxRpcOutNewAccessServiceStub.QueryObjectOutNew qoo = tmriJaxRpcOutService.createQueryObjectOut();
		// TmriJaxRpcOutAccessServiceStub.QueryObjectOut qoo = new
		// TmriJaxRpcOutAccessServiceStub.QueryObjectOut();
		if (jkid.equals("18C01") || jkid.equals("18C02")) {
			param.put("jczbh", jyjgbh);
		} else {
			param.put("jyjgbh", jyjgbh);
		}

		qoo.setJkid(jkid);
		qoo.setXtlb(RCAConstant.XTLB);
		qoo.setJkxlh(jkxlh);
		Document xml = BeanXMLUtil.map2xml(param, "QueryCondition");
		qoo.setUTF8XmlDoc(xml.asXML());
		QueryObjectOutNewResponse qoor = trias.queryObjectOutNew(qoo);

		String response = qoor.getQueryObjectOutNewReturn();
		response = URLDecoder.decode(response, "utf-8");
		Document document = DocumentHelper.parseText(response);
		return document;
	}

	public JSON getVehInfoOfws(Map param) throws RemoteException, UnsupportedEncodingException, DocumentException {

		Document document = this.queryws(RCAConstant.V18C49, param);

		return new XMLSerializer().read(document.asXML());
	}

	public JSON getVehCheckItem(Map param) throws RemoteException, UnsupportedEncodingException, DocumentException {
		Document document = this.queryws(RCAConstant.V18C46, param);

		return new XMLSerializer().read(document.asXML());
	}

}
