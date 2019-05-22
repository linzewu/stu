package com.xs.jt.vehvideo.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sun.jna.Native;

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
	  
	  
	  
	  public static void main(String[] age) {
		  HCNetSDK sd1= (HCNetSDK) Native.loadLibrary(ClientDemo.EXTEND_PATH+"\\hk\\HCNetSDK.dll",
		            HCNetSDK.class);
		  
		  System.out.println(sd1);
		  
		  for(int i=0;i<10;i++) {
			   sd1= (HCNetSDK) Native.loadLibrary(ClientDemo.EXTEND_PATH+"\\192.168.0.1\\HCNetSDK.dll",
			            HCNetSDK.class);
			  
			  System.out.println(sd1);
		  }
		  
		 
	  }

}
