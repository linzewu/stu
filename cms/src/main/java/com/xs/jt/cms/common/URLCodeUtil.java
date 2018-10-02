package com.xs.jt.cms.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCodeUtil {
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

}
