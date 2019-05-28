package com.xs.jt.base.module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ErrorController {
	
	@GetMapping("/{param}")
    public String helloWord(@PathVariable String param) {
      return param;
    }

}
