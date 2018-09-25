package com.xs.jt.cms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Test2Controller")
@RequestMapping("/test2")
public class TestController {
	
	@GetMapping("/{param}")
    public String helloWord(@PathVariable String param) {
      return param;
    }

}
