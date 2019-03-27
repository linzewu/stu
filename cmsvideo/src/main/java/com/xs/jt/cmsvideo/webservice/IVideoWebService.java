package com.xs.jt.cmsvideo.webservice;

import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.xs.jt.cmsvideo.entity.VideoPara;
import com.xs.jt.cmsvideo.util.XmlMapAdapter;

@WebService
public interface IVideoWebService {
	 @WebMethod
	 public 
	 Map<String,String> setVideoPara(VideoPara videopara);

}
