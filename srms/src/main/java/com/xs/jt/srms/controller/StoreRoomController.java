package com.xs.jt.srms.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.RecordLog;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.entity.User;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IArchivalCaseManager;
import com.xs.jt.srms.manager.IStoreRoomManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/storeRoom")
@Modular(modelCode = "storeRoom", modelName = "库房管理")
public class StoreRoomController {
	
	@Autowired
	private IStoreRoomManager storeRoomManager;
	@Autowired
	private IArchivalCaseManager archivalCaseManager;
	
	@UserOperation(code = "getStoreRoomList", name = "查询库房信息")
	@RequestMapping(value = "getStoreRoomList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStoreRoomList(Integer page, Integer rows, StoreRoom storeRoom) {
		return storeRoomManager.getStoreRoomList(page - 1, rows, storeRoom);
	}
	
	@Transactional
	@UserOperation(code="save",name="保存档案架信息")
	@RequestMapping(value = "save", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String saveStoreRoom(@Valid StoreRoom storeRoom, BindingResult result) {
		if (!result.hasErrors()) {
			storeRoomManager.saveStoreRoom(storeRoom);
			return  JSONObject.fromObject(ResultHandler.resultHandle(result,null ,Constant.ConstantMessage.SAVE_SUCCESS)).toString();
		}else{
			return JSONObject.fromObject(ResultHandler.resultHandle(result,null ,null)).toString();
		}
	}
	
	@Transactional
	@UserOperation(code="delete",name="删除档案架信息")
	@RequestMapping(value = "delete", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(StoreRoom storeRoom){
		List<ArchivalCase> useCase = this.archivalCaseManager.findUseArchivalCase(ArchivalCase.ZT_WSY, storeRoom.getRackNo());
		if(useCase != null && useCase.size() > 0) {
			return  JSONObject.fromObject(ResultHandler.toErrorJSON("档案架中还有档案格在使用，不能删除！")).toString();			
		}else {
			this.storeRoomManager.deleteStoreRoom(storeRoom);
			return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
		}
	}
	
	
	@UserOperation(code="deleteStoreRoomByArchivesNo",name="删除档案室信息")
	@RequestMapping(value = "deleteStoreRoomByArchivesNo", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	@Transactional
	public @ResponseBody String deleteStoreRoomByArchivesNo(StoreRoom storeRoom){
		this.storeRoomManager.deleteStoreRoomByArchivesNo(storeRoom.getArchivesNo());
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	@UserOperation(code = "getArchiveRackStatistics", name = "档案架库存统计")
	@RequestMapping(value = "getArchiveRackStatistics", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getArchiveRackStatistics(){
		DecimalFormat   df   =new   DecimalFormat("0.00");  
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map> list = storeRoomManager.getArchiveRackStatistics(ArchivalCase.ZT_WSY);
		
		for(Map map:list) {
			Double use = Double.valueOf(map.get("use").toString());
			
			Double capacity = Double.valueOf(map.get("capacity").toString());
			
			map.put("used", df.format(use/capacity));
			map.put("noused", 1-Double.valueOf(df.format(use/capacity)));
		}
		data.put("rows", list);
		return data;
	}
	
	@RecordLog
	@UserOperation(code="save",name="校验档案架编号",isMain=false)
	@RequestMapping(value = "validateRackNo")
	public @ResponseBody boolean validateRackNo(StoreRoom storeRoom) {
		StoreRoom room = storeRoomManager.findByRackNo(storeRoom.getRackNo());
		if (room == null) {
			return true;
		}else {
			return false;
		}
	}

}
