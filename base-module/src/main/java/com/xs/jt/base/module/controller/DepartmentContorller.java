package com.xs.jt.base.module.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.xs.jt.base.module.entity.Department;
import com.xs.jt.base.module.entity.Role;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;
import com.xs.jt.base.module.manager.impl.DepartmentManagerImpl;



@Controller
@RequestMapping(value = "/dept")
@Modular(modelCode="dept",modelName="组织架构管理",isEmpowered=false,jsjb= {Role.JSJB_STGL})
//@ModuleAnnotation(modeName = Constant.ConstantDZYXH.MODE_NAME_SYSTEM, appName = Constant.ConstantDZYXH.APP_NAME_DEPT,href="/dzyxh/page/system/department.html",icoUrl="/dzyxh/images/Organization_48.png",modeIndex=4,appIndex=4)
public class DepartmentContorller {

	@Resource(name = "departmentManager")
	private DepartmentManagerImpl deptManager;

	@RecordLog
	@UserOperation(code="getDepts",name="部门查询",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getDepts", method = RequestMethod.POST)
	public @ResponseBody List<Department> getDepts() {
		List<Department> depts = deptManager.getDepts();
		return depts;
	}
	
	@RecordLog
	@UserOperation(code="getDeptList",name="部门列表查询")
	@RequestMapping(value = "getDeptList", method = RequestMethod.POST)
	public @ResponseBody List<Department> getDeptList() {
		List<Department> depts = deptManager.getDepts();
		return depts;
	}

	@RecordLog
	@UserOperation(code="save",name="部门信息修改")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveDept(Department dept, BindingResult result) {
		
		if(!result.hasErrors()){
			Department d = this.deptManager.saveDept(dept);
			return ResultHandler.resultHandle(result, d, Constant.ConstantMessage.SAVE_SUCCESS);
		}else{
			return ResultHandler.resultHandle(result, null, null);
		}

		

	}

	@RecordLog
	@UserOperation(code="delete",name="部门信息删除")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteDept(Department dept, BindingResult result) {
		this.deptManager.deleteDept(dept);
		return ResultHandler.toSuccessJSON("删除成功！");
	}
	
	@RecordLog
	@UserOperation(code="getAllDepts",name="所有部门查询",userOperationEnum=CommonUserOperationEnum.AllLoginUser)
	@RequestMapping(value = "getAllDepts", method = RequestMethod.POST)
	public @ResponseBody List<Department> getAllDepts() {
		List<Department> depts = deptManager.getDepts();
		return depts;
	}

}
