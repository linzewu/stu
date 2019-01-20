package com.xs.jt.base.module.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ResultHandler {

	public static Map<String,Object> toMyJSON(Integer state, String message, Object object) {
		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.DATA, object);
		myJson.put(Constant.ConstantKey.MESSAGE, message);
		myJson.put(Constant.ConstantKey.STATE, state);
		return myJson;
	}

	public static Map<String,Object> toMyJSON(Integer state, String message) {

		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.MESSAGE, message);
		myJson.put(Constant.ConstantKey.STATE, state);
		return myJson;
	}

	public static Map<String,Object> toMyJSON(List rows, Integer count) {

		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.TOTAL, count);
		myJson.put(Constant.ConstantKey.ROWS, rows);
		return myJson;
	}

	public static Map<String,Object> toSuccessJSON(String message) {
		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.MESSAGE, message);
		myJson.put(Constant.ConstantKey.STATE, Constant.ConstantState.STATE_SUCCESS);
		return myJson;
	}
	
	public static Map<String,Object> toErrorJSON(String message) {
		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.MESSAGE, message);
		myJson.put(Constant.ConstantKey.STATE, Constant.ConstantState.STATE_ERROR);
		return myJson;
	}

	public static Map<String,Object> resultHandle(BindingResult result,Object data,String message) {
		Map<String,Object> myJson = new HashMap<String,Object>();
		
		
		if (result.hasErrors()) {
			List<ObjectError> list = result.getAllErrors();
			myJson.put(Constant.ConstantKey.MESSAGE, Constant.ConstantMessage.VALIDATE_ERROR);
			myJson.put(Constant.ConstantKey.STATE, Constant.ConstantState.STATE_VALIDATE_ERROR);
			myJson.put(Constant.ConstantKey.ERRORS, list);
		} else {
			myJson.put(Constant.ConstantKey.DATA, data);
			myJson.put(Constant.ConstantKey.MESSAGE, message);
			myJson.put(Constant.ConstantKey.STATE, Constant.ConstantState.STATE_SUCCESS);
		}
		return myJson;
	}
	
	public static Map<String,Object> toMessage(Message message) {
		Map<String,Object> myJson = new HashMap<String,Object>();
		myJson.put(Constant.ConstantKey.MESSAGE, message.getMessage());
		if(message.getState().equals(Message.STATE_ERROR)){
			myJson.put(Constant.ConstantKey.STATE,Constant.ConstantState.STATE_ERROR);
		}
		if(message.getState().equals(Message.STATE_SUCCESS)){
			myJson.put(Constant.ConstantKey.STATE,Constant.ConstantState.STATE_SUCCESS);
		}
		return myJson;
	}
	
	public static String parserJSONP(HttpServletRequest request,String data){
		String param = request.getParameter("callback");
		if(param!=null&&!"".equals(param)){
			return param+"("+data+")";
		}else{
			return data;
		}
		
	}

}
