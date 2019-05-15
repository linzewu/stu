package com.xs.jt.base.module.controller;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.entity.SecurityLog;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.manager.ISecurityLogManager;
import com.xs.jt.base.module.util.ExcelExportUtils;
import com.xs.jt.base.module.util.FileUtil;

import javassist.bytecode.stackmap.TypeData.ClassName;



@Controller
@RequestMapping(value = "/securityLog")
@Modular(modelCode="SecurityLog",modelName="安全日志管理",isEmpowered=false,jsjb=Role.JSJB_SJGL)
public class SecurityLogController {
	protected static Log log = LogFactory.getLog(SecurityLogController.class);
	
	@Autowired
	private HttpServletRequest request;
	
	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;
	
	@Resource(name = "securityLogManager")
	private ISecurityLogManager securityLogManager;
	
	@Autowired
	private HttpSession session;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@UserOperation(code="getSecurityLog",name="安全日志查询")
	@RequestMapping(value = "getSecurityLog", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getSecurityLog(Integer page, Integer rows, SecurityLog securityLog) {		
		
		return securityLogManager.getSecurityLogs(page-1, rows, securityLog);
	}
	
	
	@UserOperation(code="saveCcqx",name="设置日志存储期限")
	@RequestMapping(value = "saveCcqx", method = RequestMethod.POST)
	public @ResponseBody Map save(String rzccqx) {
		BaseParams bp = null;
		List<BaseParams> list = this.baseParamsManager.getBaseParamsByType("rzccqx");
		if(!CollectionUtils.isEmpty(list)) {
			bp = list.get(0);
		}else{
			bp = new BaseParams();
			bp.setType("rzccqx");
			bp.setMemo("日志存储期限");
			bp.setParamName("日志存储期限");
		}
		bp.setParamValue(rzccqx);
		baseParamsManager.save(bp);
		
		//刷新系统参数
		ServletContext servletContext = request.getSession().getServletContext();
		List<BaseParams> bplist = baseParamsManager.getBaseParams();
		servletContext.setAttribute("bps", bplist);
		Map<String, List<BaseParams>>  bpsMap = baseParamsManager.convertBaseParam2Map();
		servletContext.setAttribute("bpsMap", bpsMap);
		return ResultHandler.toSuccessJSON("设置日志存储期限成功");
	}
	
	@UserOperation(code="export",name="安全日志导出")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ResponseBody
    public void excel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String operationDateBegin = request.getParameter("operationDateBegin");
		String operationDateEnd = request.getParameter("operationDateEnd")+" 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		User user = (User) session.getAttribute("user");

		List<SecurityLog> list = this.securityLogManager.getSecurityLogList(operationDateBegin,operationDateEnd);
		
        Map<String, Object> beans = new HashMap();
        beans.put("list", list);

        //加载excel模板文件
        InputStream is = ClassName.class.getResourceAsStream("/excelTemplate/securityLogList.xlsx"); 

        //配置下载路径
        String path = cacheDir+"/exceldownload/";
        FileUtil.createDirectory(path);// createDir(new File(path));

        
        //根据模板生成新的excel
        String name = "securityLogList_"+user.getYhm()+"_"+sdf.format(new Date())+".xlsx";
        File excelFile = ExcelExportUtils.createNewFile(beans, path,name,is);

        //浏览器端下载文件
        ExcelExportUtils.downloadFile(response, excelFile);

        //删除服务器生成文件
        FileUtil.deleteFile(path + name);

    }
	
	@UserOperation(code="getStatisticsSecurityLog",name="安全日志统计")
	@RequestMapping(value = "getStatisticsSecurityLog", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStatisticsSecurityLog(){
		List list = this.securityLogManager.getStatisticsSecurityLog();
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "安全日志统计", list);
	}
	


    

}
