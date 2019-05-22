package com.xs.jt.base.module.aop;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.xs.jt.base.module.common.DataBaseWhiteListExctption;
import com.xs.jt.base.module.common.TamperWithDataException;

//全局异常拦截
@ControllerAdvice
public class ErrorAdvice {
	
	@ExceptionHandler(Exception.class) //拦截所有运行时的全局异常
	@ResponseStatus(HttpStatus.OK)
	 //返回 JOSN
	public @ResponseBody HashMap<String, Object> ErrorTest(Exception e){
		HashMap<String, Object> map = new HashMap<>();
		if(e instanceof TamperWithDataException) {
			map.put("state", "555");
		}
		else if(e instanceof DataBaseWhiteListExctption) {
			map.put("state", "556");
		}
		else {
			map.put("state", "500");
		}
		
		map.put("message",e.getMessage());
		return map;
	}

}
