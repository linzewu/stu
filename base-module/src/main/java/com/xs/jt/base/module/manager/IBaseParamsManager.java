package com.xs.jt.base.module.manager;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xs.jt.base.module.entity.BaseParams;


public interface IBaseParamsManager {
	
	public List<BaseParams> getBaseParams();
	
	public BaseParams save(BaseParams baseParams);
	
	public void delete(Integer id);
	
	public Map<String,Object> getBaseParams(Integer page, Integer rows,BaseParams param);
	
	public List<BaseParams> getBaseParamsByType(String type);
	
	public BaseParams getBaseParam(String type, String paramName);
	
	public Map<String,List<BaseParams>> convertBaseParam2Map();
	
	public BaseParams getBaseParamByValue(String type, String paramValue);
	
	public static void convertData(Map<String, Object> data) {
		
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();

		Map<String, List<BaseParams>> mapParam = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");

		if (!CollectionUtils.isEmpty(mapParam)) {
			for (String key : data.keySet()) {
				Object value = data.get(key);
				if (value != null) {
					List<BaseParams> paramList = mapParam.get(key);
					if (!CollectionUtils.isEmpty(paramList)) {
						for (BaseParams param : paramList) {
							if (param.getParamValue().equals(data.get(key).toString())) {
								data.put(key, param.getParamName());
							}
						}
					}
				}

			}
		}

	}

}
