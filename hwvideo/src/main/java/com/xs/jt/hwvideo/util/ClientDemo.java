package com.xs.jt.hwvideo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientDemo {
	
	 public static String EXTEND_PATH;
	  static{
	    	InputStream ins = ClientDemo.class.getClassLoader().getResourceAsStream("application.properties");
	    	Properties properties=new Properties();
	    	try {
	    		properties.load(ins);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	EXTEND_PATH = (String)properties.get("cms.video.extend.path");
	    }

}
