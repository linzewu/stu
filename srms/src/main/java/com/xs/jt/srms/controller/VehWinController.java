package com.xs.jt.srms.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.srms.manager.IVehImgManager;
import com.xs.jt.srms.vehimg.entity.VehImg;

@Controller
@RequestMapping(value = "/vehWin")
public class VehWinController {
	
	@Autowired
	private IVehImgManager vehImgManager;
	@Autowired
	private IBaseParamsManager baseParamsManager;
	
	
	@RequestMapping(value = "saveImg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveImg(VehImg vehimg,String base64Img) {
		
		return vehImgManager.saveImg(vehimg, base64Img);
	}
	
	@RequestMapping(value = "getImgBylsh", method = RequestMethod.POST)
	public @ResponseBody List<VehImg>  getImgBylsh(@RequestParam String lsh) {
		return this.vehImgManager.getVehImgByLshOfzc(lsh);
	}
	
	@RequestMapping(value = "getZpzl", method = RequestMethod.POST)
	public @ResponseBody List<BaseParams>  getZpzl(@RequestParam String ywlx) {
		return baseParamsManager.getBaseParamsByType(ywlx);
	}
	
	@RequestMapping(value = "getImgById", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getImgById(Integer id) throws IOException{
		String imgPath = vehImgManager.buildImgById(id);
//		if(!ImageBase64Cash.getInstance().exists(id.toString())){
//			VehImg img = this.vehImgManager.getImgById(id);
//			ImageBase64Cash.getInstance().cashIamge(img.getZp(), img.getId().toString());
//		}
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "查看照片成功！", imgPath);
	}
	
	
	@RequestMapping(value = "getYwByClsbdh", method = RequestMethod.POST)
	public @ResponseBody List<Map<String,Object>>  getYwByClsbdh(@RequestParam String clsbdh) {
		
		return this.vehImgManager.getYwListByClsbdh(clsbdh);
	}
	
	
	

}
