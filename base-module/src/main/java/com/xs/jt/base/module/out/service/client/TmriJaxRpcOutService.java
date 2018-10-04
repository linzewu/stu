package com.xs.jt.base.module.out.service.client;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;

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
	
	
	public TmriJaxRpcOutNewAccessServiceStub createTmriJaxRpcOutNewAccessServiceStub() throws AxisFault {
		List<BaseParams> baseParams = baseParamsManager.getBaseParamsByType("ptip");
		if(!CollectionUtils.isEmpty(baseParams)) {
			TmriJaxRpcOutNewAccessServiceStub.IP=baseParams.get(0).getParamValue();
		}else {
			throw new ApplicationException("从数据字典中获取不到平台IP地址！");
		}
		return new TmriJaxRpcOutNewAccessServiceStub();
	}
	
	
	public TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut createQueryObjectOut() {
		String yhbz="";
		String yhxm="";
		if(session!=null) {
			User user = (User) session.getAttribute("user");
			if(user!=null) {
				yhbz=user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut qo = new TmriJaxRpcOutNewAccessServiceStub.QueryObjectOut();
		qo.setJkxlh(jkxlh);
		qo.setXtlb(xtlb);
		qo.setDwmc(dwmc);
		qo.setDwjgdm(dwjgdm);
		qo.setYhbz(yhbz);
		qo.setYhxm(yhxm);
		qo.setZdbs(zdbs);
		return qo;
	}
	
	
	public TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut createWriteObjectOut() {
		
		String yhbz="";
		String yhxm="";
		if(session!=null) {
			User user = (User) session.getAttribute("user");
			if(user!=null) {
				yhbz=user.getSfzh();
				yhxm = user.getYhxm();
			}
		}
		TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut wo = new TmriJaxRpcOutNewAccessServiceStub.WriteObjectOut();
		wo.setJkxlh(jkxlh);
		wo.setXtlb(xtlb);
		wo.setDwmc(dwmc);
		wo.setDwjgdm(dwjgdm);
		wo.setYhbz(yhbz);
		wo.setYhxm(yhxm);
		wo.setZdbs(zdbs);
		
		return wo;
	}
	
	
}
