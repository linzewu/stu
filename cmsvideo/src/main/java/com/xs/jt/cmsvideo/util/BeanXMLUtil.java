package com.xs.jt.cmsvideo.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class BeanXMLUtil {
	
	protected static Log logger = LogFactory.getLog(BeanXMLUtil.class);

	public static Document bean2xml(Object bean, String element)
			throws 	NoSuchMethodException{

		Field[] fields = bean.getClass().getDeclaredFields();
		
		Document document=DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("root");
		Element subelement = root.addElement(element);
		logger.debug("fields size"+fields.length);
		for (Field field : fields) {
			String fname = field.getName();
			String getMehod = "get" + fname.substring(0, 1).toUpperCase()
					+ fname.substring(1, fname.length());
			Method method = bean.getClass().getMethod(getMehod);
			Object value;
			try {
				value = method.invoke(bean);
				Element felement = subelement.addElement(fname);
				if(value!=null&&!"".equals(value)){
					felement.setText( URLEncoder.encode(value.toString(),"UTF-8"));
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | UnsupportedEncodingException e) {
				logger.error("bean2xml执行异常",e);
			}
		}
		logger.debug("subelement"+subelement.asXML());
		logger.debug("document"+document.asXML());
		return document;
	}
	
	
	public static Document map2xml(Map map ,String element){
		logger.debug("map:+"+map);
		Document document=DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement("root");
		Element subelement = root.addElement(element);
		Set<String> set = map.keySet();
		for(String key:set){
			Element felement = subelement.addElement(key);
			try {
				if(map.get(key)!=null){
					String val=map.get(key).toString().trim();
					if("rgjyjgs".equals(key)||"yqsbjyjgs".equals(key)){
						if(val!=null&&!"".equals(val.trim())){
							Document sd = DocumentHelper.parseText(val);
							List<Element> list = sd.getRootElement().elements();
							for(Element e:list){
								felement.add(e.detach());
							}
						}
					}else{
						felement.setText(URLEncoder.encode(val,"UTF-8"));
					}
				}
			} catch (Exception e) {
				logger.error("map2xml执行异常",e);
			}
		}
		logger.debug("document:"+document.asXML());
		return document;
	} 
	
public static Document list2xml(List<Map> data,String element){
		
		logger.debug("list:+"+data);
		Document document=DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element  root =  document.addElement("root");
		for(Map map:data){
			Element  subElement = root.addElement(element);
			
			Set<String> keys = map.keySet();
			
			for(String key:keys){
				Element e = subElement.addElement(key);
				Object o= map.get(key);
				String val=null;
				if(o!=null){
					val=o.toString();
				}
				val=val==null?"":val;
				try {
					e.setText(URLEncoder.encode(val,"UTF-8"));
				} catch (UnsupportedEncodingException e1) {
					logger.error("map2xml执行异常",e1);
				}
			}
		}
		
		return document;
	}

	public static void main(String[] arg) {
//		JYDLXX jydxx = new JYDLXX();
//		jydxx.setId(111);
//		try {
//		System.out.println(	BeanXMLUtil.bean2xml(jydxx, "vehispara"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
