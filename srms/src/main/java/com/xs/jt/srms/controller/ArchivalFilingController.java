package com.xs.jt.srms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.util.FileUtil;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.ArchivalRegister;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.manager.IArchivalRegisterManager;
import com.xs.jt.srms.manager.IStoreRoomManager;
import com.xs.jt.srms.util.BarcodeUtil;

import javassist.bytecode.stackmap.TypeData.ClassName;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
@RequestMapping(value = "/archivalFiling")
@Modular(modelCode = "archivalFiling", modelName = "档案归档")
public class ArchivalFilingController {
	
	protected static Log log = LogFactory.getLog(ArchivalFilingController.class);
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private IArchivalRegisterManager archivalRegisterManager;
	
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@Autowired
	private IBaseParamsManager baseParamsManager;
	
	@Autowired
	private IStoreRoomManager storeRoomManager;
	
	@Transactional
	@UserOperation(code="newCarArchivalCheckIn",name="新车档案入库")
	@RequestMapping(value = "newCarArchivalCheckIn", method = RequestMethod.POST)
	public @ResponseBody Map newCarArchivalCheckIn(ArchivalCase archivalCase){
		List<ArchivalCase> existList = this.archivalCaseManager.getArchivalCaseByClsbdh(archivalCase.getClsbdh());
		if(!CollectionUtils.isEmpty(existList)) {
			return  ResultHandler.toErrorJSON("库房已存在该车辆信息，不能做新车档案入库，请核对！");
		}
		ArchivalCase ac = this.archivalCaseManager.newCarArchivalCheckIn(archivalCase);
		if(ac == null) {
			return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "档案格已满，无法入库！", ac);
		}
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, ac);//JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@Transactional
	@UserOperation(code="UsedCarArchivalCheckIn",name="在用车档案入库")
	@RequestMapping(value = "UsedCarArchivalCheckIn", method = RequestMethod.POST)
	public @ResponseBody Map UsedCarArchivalCheckIn(ArchivalCase archivalCase){		
		ArchivalCase ac = this.archivalCaseManager.UsedCarArchivalCheckIn(archivalCase);
		if(ac == null) {
			return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR, "档案格已满，无法入库！", ac);
		}
		//return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
		return  ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS, ac);
	}
	
	@Transactional
	@UserOperation(code="archivalCaseAdjust",name="档案格调整")
	@RequestMapping(value = "archivalCaseAdjust", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String archivalCaseAdjust(ArchivalCase archivalCase){		
		boolean flag = this.archivalCaseManager.archivalCaseAdjust(archivalCase);
		if(flag) {			
			return  JSONObject.fromObject(ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, Constant.ConstantMessage.SUCCESS)).toString();
		}else {
			return  JSONObject.fromObject(ResultHandler.toMyJSON(Constant.ConstantState.STATE_ERROR,Constant.ConstantMessage.FAILURE)).toString();
		}
		
	}
	
	@UserOperation(code = "createLabel", name = "条码标签打印")
	@RequestMapping(value = "createLabel", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> createLabel(String barCode){
		String path = cacheDir+"/barcode/";
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		Map<String, Object> data = new HashMap<String, Object>();
		File barFile = BarcodeUtil.generateFile(barCode, path+barCode+".jpg");
		
		return data;
	}
	
	/**
     * excel导出
     * 1.获取数据集List 插入到map集合中
     * 2.根据模板生成新的excel
     * 3.将新生成的excel文件从浏览器输出
     * 4.删除新生成的模板文件
	 * @throws Exception 
     */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ResponseBody
    public void excel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		User user = (User) session.getAttribute("user");
		String rksj = request.getParameter("rksj");
		System.out.println(rksj);
		List<ArchivalRegister> list = archivalRegisterManager.findArchivalRegisterCheckIn(user.getYhm(), ArchivalCase.ZT_RK,rksj);
		JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
		for(int i=0;i<array.size();i++) {
			Map map = array.getJSONObject(i);
			for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				if("zt".equals(key)) {
					BaseParams bp = baseParamsManager.getBaseParamByValue("dazt", map.get(key).toString());
					if(bp != null) {
						map.put(key, bp.getParamName());
					}
				}else if("hpzl".equals(key) || "ywlx".equals(key)) {
					BaseParams bp = baseParamsManager.getBaseParamByValue(key, map.get(key).toString());
					if(bp != null) {
						map.put(key, bp.getParamName());
					}
				}
			}
		}
        Map<String, Object> beans = new HashMap();
        beans.put("list", array);

        //加载excel模板文件
        InputStream is = ClassName.class.getResourceAsStream("/excelTemplate/checkInList.xlsx"); //ResourceUtils.getFile("classpath:excelTemplate/checkInList.xlsx");

        //配置下载路径
        String path = cacheDir+"/exceldownload/";
        FileUtil.createDirectory(path);// createDir(new File(path));

        
        //根据模板生成新的excel
        String name = "checkInList_"+user.getYhm()+"_"+sdf.format(new Date())+".xlsx";
        File excelFile = createNewFile(beans, path,name,is);

        //浏览器端下载文件
        downloadFile(response, excelFile);

        //删除服务器生成文件
        FileUtil.deleteFile(path + name);

    }


    /**
     * 根据excel模板生成新的excel
     *
     * @param beans
     * @param file
     * @param path
     * @return
     * @throws Exception 
     */
    private File createNewFile(Map<String, Object> beans, String path,String name,InputStream is) throws Exception {
        XLSTransformer transformer = new XLSTransformer();

        //可以写工具类来生成命名规则
       
        File newFile = new File(path + name);


        try (InputStream in = new BufferedInputStream(is);
             OutputStream out = new FileOutputStream(newFile)) {
            Workbook workbook = transformer.transformXLS(in, beans);
            workbook.write(out);
            out.flush();
            return newFile;
        } catch (Exception e) {
        	log.error("生成Excel失败！", e);
            throw e;
        }
        //return newFile;
    }

    /**
     * 将服务器新生成的excel从浏览器下载
     *
     * @param response
     * @param excelFile
     * @throws IOException 
     */
    private void downloadFile(HttpServletResponse response, File excelFile) throws IOException {
        /* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
        response.setContentType("multipart/form-data");
        /* 设置文件头：最后一个参数是设置下载文件名 */
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFile.getName());
        try (
                InputStream ins = new FileInputStream(excelFile);
                OutputStream os = response.getOutputStream()
        ) {
            byte[] b = new byte[1024];
            int len;
            while ((len = ins.read(b)) > 0) {
                os.write(b, 0, len);
            }
        } catch (IOException ioe) {
        	log.error("下载Excel失败！", ioe);
        	throw ioe;
        }
    }


    @UserOperation(code="newCarArchivalCheckIn",name="查看库房信息",isMain=false)
	@RequestMapping(value = "getStoreRoomInfoList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStoreRoomList(Integer page, Integer rows, StoreRoom storeRoom) {
		return storeRoomManager.getStoreRoomList(page - 1, rows, storeRoom);
	}
    
    @UserOperation(code="newCarArchivalCheckIn",name="查看档案格信息",isMain=false)
	@RequestMapping(value = "getNoUsedByArchivesNoAndRackNo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getNoUsedByArchivesNoAndRackNo(StoreRoom storeRoom) {
    	Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", this.archivalCaseManager.getNoUsedByArchivesNoAndRackNo(storeRoom.getArchivesNo(), storeRoom.getRackNo()));
		return data;
	}
	
	
	
	
	
	
	

}
