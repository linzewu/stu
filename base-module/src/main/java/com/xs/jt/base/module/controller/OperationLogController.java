package com.xs.jt.base.module.controller;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.RecordLog;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.OperationLog;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IOperationLogManager;
import com.xs.jt.base.module.util.ExcelExportUtils;
import com.xs.jt.base.module.util.FileUtil;

import javassist.bytecode.stackmap.TypeData.ClassName;


@Controller
@RequestMapping(value = "/opeationLog")
@Modular(modelCode="OperationLog",modelName="日志管理",isEmpowered=false,jsjb= {Role.JSJB_STGL,Role.JSJB_AQGL,Role.JSJB_SJGL})
public class OperationLogController {
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private HttpSession session;
	
	@Resource(name = "operationLogManager")
	private IOperationLogManager operationLogManager;
	
	@RecordLog
	@UserOperation(code="getOperationLog",name="日志查询")
	@RequestMapping(value = "getOperationLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getOperationLog(Integer page, Integer rows, OperationLog operationLog) {
				
		return this.operationLogManager.getOperationLogs(page-1, rows, operationLog);
	}
	
	@RecordLog
	@UserOperation(code="getLoginOperationLog",name="登录日志查询")
	@RequestMapping(value = "getLoginOperationLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getLoginOperationLog(Integer page, Integer rows, OperationLog operationLog) {
			
		
		return this.operationLogManager.getLoginOperationLogs(page-1, rows, operationLog);
	}
	
	@RecordLog
	@UserOperation(code="exportLog",name="操作日志导出")
	@RequestMapping(value = "/exportLog", method = RequestMethod.GET)
	@ResponseBody
    public void excel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		String operationDateBegin = request.getParameter("operationDate");
		String operationDateEnd = request.getParameter("operationDateEnd");
		String operationUser = request.getParameter("operationUser");
		String module = request.getParameter("module");
		String coreFunction = request.getParameter("coreFunction");
		OperationLog operationLog = new OperationLog();
		operationLog.setOperationUser(operationUser);
		operationLog.setOperationDate(sdf1.parse(operationDateBegin));
		operationLog.setOperationDateEnd(sdf1.parse(operationDateEnd));
		operationLog.setModule(module);
		operationLog.setCoreFunction(coreFunction);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		User user = (User) session.getAttribute("user");
		
		List<OperationLog> list = this.operationLogManager.getExportList(operationLog);
		
        Map<String, Object> beans = new HashMap();
        beans.put("list", list);

        //加载excel模板文件
        InputStream is = ClassName.class.getResourceAsStream("/excelTemplate/operationLogList.xlsx"); 

        //配置下载路径
        String path = cacheDir+"/exceldownload/";
        FileUtil.createDirectory(path);// createDir(new File(path));

        
        //根据模板生成新的excel
        String name = "operationLogList_"+user.getYhm()+"_"+sdf.format(new Date())+".xlsx";
        File excelFile = ExcelExportUtils.createNewFile(beans, path,name,is);

        //浏览器端下载文件
        ExcelExportUtils.downloadFile(response, excelFile);

        //删除服务器生成文件
        FileUtil.deleteFile(path + name);

    }
	
	@RecordLog
	@UserOperation(code="exportLoginLog",name="登录日志导出")
	@RequestMapping(value = "/exportLoginLog", method = RequestMethod.GET)
	@ResponseBody
    public void exportLoginLog(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		String operationDateBegin = request.getParameter("operationDate");
		String operationDateEnd = request.getParameter("operationDateEnd");
		String operationUser = request.getParameter("operationUser");
		String module = request.getParameter("module");
		String coreFunction = request.getParameter("coreFunction");
		OperationLog operationLog = new OperationLog();
		operationLog.setOperationUser(operationUser);
		operationLog.setOperationDate(sdf1.parse(operationDateBegin));
		operationLog.setOperationDateEnd(sdf1.parse(operationDateEnd));
		operationLog.setModule(module);
		operationLog.setCoreFunction(coreFunction);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		User user = (User) session.getAttribute("user");
		
		List<OperationLog> list = this.operationLogManager.getLoginExportList(operationLog);
		
        Map<String, Object> beans = new HashMap();
        beans.put("list", list);

        //加载excel模板文件
        InputStream is = ClassName.class.getResourceAsStream("/excelTemplate/operationLogList.xlsx"); 

        //配置下载路径
        String path = cacheDir+"/exceldownload/";
        FileUtil.createDirectory(path);// createDir(new File(path));

        
        //根据模板生成新的excel
        String name = "operationLogList_"+user.getYhm()+"_"+sdf.format(new Date())+".xlsx";
        File excelFile = ExcelExportUtils.createNewFile(beans, path,name,is);

        //浏览器端下载文件
        ExcelExportUtils.downloadFile(response, excelFile);

        //删除服务器生成文件
        FileUtil.deleteFile(path + name);

    }
	
	@RecordLog
	@UserOperation(code="getStatisticsLoginLog",name="登录日志统计")
	@RequestMapping(value = "getStatisticsLoginLog", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStatisticsLoginLog(){
		List list = this.operationLogManager.getStatisticsLoginLog();
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "登录日志统计", list);
	}
	
	@RecordLog
	@UserOperation(code="getStatisticsOperationLog",name="操作日志统计")
	@RequestMapping(value = "getStatisticsOperationLog", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStatisticsOperationLog(){
		List list = this.operationLogManager.getStatisticsOperationLog();
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "操作日志统计", list);
	}

}
