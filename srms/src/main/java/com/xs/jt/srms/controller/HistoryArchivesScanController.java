package com.xs.jt.srms.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.srms.manager.IVehImgManager;
import com.xs.jt.srms.vehimg.entity.VehImg;

@Controller
@RequestMapping(value = "/historyArchivesScan")
@Modular(modelCode = "historyArchivesScan", modelName = "历史档案扫描")
public class HistoryArchivesScanController {
	
	@Autowired
	private IVehImgManager vehImgManager;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;
	
	@RequestMapping(value = "saveImg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveImg(VehImg vehimg,String base64Img) {
		return vehImgManager.saveImg(vehimg, base64Img);
	}
	
//	@RequestMapping(value = "getImgById", method = RequestMethod.GET)
//	public String getImgById(Integer id) throws IOException{
//		
//		if(!ImageBase64Cash.getInstance().exists(id.toString())){
//			VehImg img = this.vehImgManager.getImgById(id);
//			ImageBase64Cash.getInstance().cashIamge(img.getZp(), img.getId().toString());
//		}
//		return "forward:/images/cache/"+id+".jpg";
//	}
	
	@UserOperation(code = "updateZplx", name = "修改历史档案照片类型")
	@RequestMapping(value = "updateZplx", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateZplx(VehImg vehimg) {
		VehImg img = this.vehImgManager.getImgById(vehimg.getId());
		img.setZplx(vehimg.getZplx());
		this.vehImgManager.saveVehImg(img);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "修改历史档案照片类型成功", vehimg);
	}
	
	@UserOperation(code = "saveVehImg", name = "历史档案扫描")
	@RequestMapping(value = "saveVehImg", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveVehImg(VehImg vehimg) throws Exception {
		vehImgManager.uploadVehImg(vehimg);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "上传成功", vehimg.getId());
	}
	
	@UserOperation(code = "getImgById", name = "查看照片", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getImgById", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getImgById(Integer id) throws IOException{
		String imgPath = vehImgManager.buildImgById(id);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "查看照片成功！", imgPath);
	}
	
	@UserOperation(code = "getVehImgMapByLsh", name = "根据流水号查询历史档案", userOperationEnum = CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getVehImgMapByLsh", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> getVehImgMapByLsh(String lsh) throws IOException{
		List<Map<String,Object>> list = vehImgManager.getVehImgMapByLsh(lsh);
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "根据流水号查询历史档案成功！", list);
	}
	
	@UserOperation(code = "deleteVehImgByLsh", name = "根据流水号删除历史档案")
	@RequestMapping(value = "deleteVehImgByLsh", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteVehImgByLsh(String lsh){
		List<Map<String,Object>> list = vehImgManager.getVehImgMapByLsh(lsh);
		vehImgManager.deleteVehImgByLsh(lsh);
		for(Map map:list) {
			File pathFile = new File(cacheDir+"\\vehimg\\"+map.get("id").toString()+".jpg");
			if(pathFile.exists()) {
				pathFile.delete();
			}
		}
		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "根据流水号删除历史档案成功！", null);
	}
	
	@UserOperation(code = "deleteVehImgById", name = "根据Id删除历史档案")
	@RequestMapping(value = "deleteVehImgById", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteVehImgById(Integer id){
		vehImgManager.deleteVehImgById(id);
		File pathFile = new File(cacheDir+"\\vehimg\\"+id+".jpg");
		if(pathFile.exists()) {
			pathFile.delete();
		}		
		return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "根据Id删除历史档案成功！", null);
	}

}
