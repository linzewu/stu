package com.xs.jt.cms.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xs.jt.cms.entity.PreCarRegister;

public class CommonUtil {

	public static String convertCode(String key, String value,HttpServletRequest request) {
		Map codeMap = (Map) request.getSession().getServletContext()
				.getAttribute("CardCodes");
		if (codeMap == null) {
			return value;
		}
		// System.out.println("key  "+codeMap);
		List<Map> codeList = (List) codeMap.get(key);

		if (codeList == null) {
			return value;
		}
		String[] vs = value.split(",");
		StringBuffer ss = new StringBuffer();
		for (String s : vs) {
			for (Map map : codeList) {
				if (map.get("DMZ").equals(s)) {
					ss.append((String) map.get("DMSM1"));
					ss.append(",");
				}
			}
		}
		if(ss.length()>0){
			return ss.substring(0,ss.length()-1);
		}else{
			return value;
		}
		
	}
	
	public static void setXczl(PreCarRegister bcr, Map<String, Object> data) {
		if ("K18".equals(bcr.getCllx()) ||  "K28".equals(bcr.getCllx())|| "K38".equals(bcr.getCllx())) {
			data.put("zyxc", "☑");
			data.put("fzyxc", "□");
		}else {
			data.put("zyxc", "□");
			data.put("fzyxc", "☑");
		}
	}

}
