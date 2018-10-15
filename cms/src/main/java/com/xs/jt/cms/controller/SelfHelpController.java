package com.xs.jt.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xs.jt.base.module.annotation.Modular;

@Controller
@RequestMapping(value = "/selfHelp")
@Modular(modelCode = "selfHelp", modelName = "机动车自助终端")
public class SelfHelpController {

}
