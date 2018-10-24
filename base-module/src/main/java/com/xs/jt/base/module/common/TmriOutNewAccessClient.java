package com.xs.jt.base.module.common;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.out.service.client.TmriJaxRpcOutNewAccessServiceStub;
@Service
public class TmriOutNewAccessClient {
	
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
	
	public OMElement  writeObjectOut() {
		List<BaseParams> baseParams = baseParamsManager.getBaseParamsByType("ptip");
		String ip = "";
		if (!CollectionUtils.isEmpty(baseParams)) {
			ip = baseParams.get(0).getParamValue();
		} else {
			throw new ApplicationException("从数据字典中获取不到平台IP地址！");
		}
		 OMFactory fac = OMAbstractFactory.getOMFactory();
		 
		 /*
		      *    指定命名空间，参数：
          * uri--即为wsdl文档的targetNamespace，命名空间
          * perfix--可不填
          */
         OMNamespace omNs = fac.createOMNamespace("http://"+ip+":9080/trffweb/services/TmriOutNewAccess", "ns2");

		// 指定方法
        OMElement method = fac.createOMElement("writeObjectOut", omNs);
        // 指定方法的参数
        OMElement mobileCode = fac.createOMElement("mobileCode", omNs);
        mobileCode.setText("15932582632");
        OMElement userID = fac.createOMElement("userID", omNs);
        userID.setText("");
        method.addChild(mobileCode);
        method.addChild(userID);

        return method;
		
	}

}
