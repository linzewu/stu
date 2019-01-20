package com.xs.jt.veh.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.Sql2WordUtil;
import com.xs.jt.base.module.entity.BaseParams;

@Controller
@RequestMapping(value = "/checkReport", produces = "application/json")
@Modular(modelCode = "checkReport", modelName = "车辆性能检验", isEmpowered = false)
public class CheckReportController {

	@Value("${stu.cache.dir}")
	private String cacheDir;// = "D:\\cache";

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private HttpServletRequest request;

	@UserOperation(code = "printJyReport", name = "打印道路运输车辆性能检验记录单")
	@RequestMapping(value = "printJyReport", method = RequestMethod.POST)
	public @ResponseBody Map printJyReport(String lsh) throws Exception {
		String basePath = "cache/report/";
		String filePath = request.getSession().getServletContext().getRealPath("/") + basePath;
		String fileName = "";

		// PreCarRegister bcr = this.preCarRegisterManager.findPreCarRegisterByLsh(lsh);
		Map<String, Object> data = new HashMap<String, Object>();
//				data.put("lshCode", BarcodeUtil.generateInputStream(bcr.getLsh()));
//				if(StringUtils.isEmpty(bcr.getHphm())) {
//					data.put("hphm", bcr.getClsbdh());
//				}

		Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
		String template = "道路运输车辆性能检验记录单.docx";
		fileName = "template_performance_record" + new Date().getTime() + ".jpg";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, data, bpsMap);
		Sql2WordUtil.toCase(doc, filePath, fileName);

		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "打印道路运输车辆性能检验记录单成功", fileName);
	}

	@UserOperation(code = "printJyBgReport", name = "打印道路运输车辆性能检验报告单")
	@RequestMapping(value = "printJyBgReport", method = RequestMethod.POST)
	public @ResponseBody Map printJyBgReport(String lsh) throws Exception {
		String basePath = "cache/report/";
		String filePath = request.getSession().getServletContext().getRealPath("/") + basePath;
		String fileName = "";

		// PreCarRegister bcr = this.preCarRegisterManager.findPreCarRegisterByLsh(lsh);
		Map<String, Object> data = new HashMap<String, Object>();
//				data.put("lshCode", BarcodeUtil.generateInputStream(bcr.getLsh()));
//				if(StringUtils.isEmpty(bcr.getHphm())) {
//					data.put("hphm", bcr.getClsbdh());
//				}

		Map<String, List<BaseParams>> bpsMap = (Map<String, List<BaseParams>>) servletContext.getAttribute("bpsMap");
		String template = "道路运输车辆综合性能检验报告单.docx";
		fileName = "template_performance_record" + new Date().getTime() + ".jpg";
		com.aspose.words.Document doc = Sql2WordUtil.map2WordUtil(template, data, bpsMap);
		Sql2WordUtil.toCase(doc, filePath, fileName);

		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "打印道路运输车辆性能检验报告单成功", fileName);
	}

}
