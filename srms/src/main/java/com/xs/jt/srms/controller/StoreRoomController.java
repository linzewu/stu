package com.xs.jt.srms.controller;

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
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IStoreRoomManager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/storeRoom")
@Modular(modelCode = "storeRoom", modelName = "库房管理")
public class StoreRoomController {
	
	@Autowired
	private IStoreRoomManager storeRoomManager;
	
	@UserOperation(code = "getStoreRoomList", name = "查询库房信息")
	@RequestMapping(value = "getStoreRoomList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStoreRoomList(Integer page, Integer rows, StoreRoom storeRoom) {
		return storeRoomManager.getStoreRoomList(page - 1, rows, storeRoom);
	}
	
	
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
	
	@UserOperation(code="delete",name="删除档案架信息")
	@RequestMapping(value = "delete", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	public @ResponseBody String delete(StoreRoom storeRoom){
		this.storeRoomManager.deleteStoreRoom(storeRoom);
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}
	
	
	@UserOperation(code="deleteStoreRoomByArchivesNo",name="删除档案室信息")
	@RequestMapping(value = "deleteStoreRoomByArchivesNo", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
	@Transactional
	public @ResponseBody String deleteStoreRoomByArchivesNo(StoreRoom storeRoom){
		this.storeRoomManager.deleteStoreRoomByArchivesNo(storeRoom.getArchivesNo());
		return  JSONObject.fromObject(ResultHandler.toSuccessJSON(Constant.ConstantMessage.SUCCESS)).toString();
	}

}
