package com.xs.jt.veh.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.veh.manager.ICheckQueueManager;

@Controller
@RequestMapping(value = "/linecheck")
public class VehLineCheckController {

	@Autowired
	private ICheckQueueManager checkQueueManager;

	@RequestMapping(value = "upLine", method = RequestMethod.POST)
	public @ResponseBody Map upLine(@RequestParam String jylsh, @RequestParam Integer jccs) {

		Message message = checkQueueManager.upLine(jylsh, jccs);

		return ResultHandler.toMessage(message);
	}

}
