package com.xs.jt.base.module.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.entity.PowerPoint;
import com.xs.jt.base.module.enums.CommonUserOperationEnum;

//import com.xs.veh.manager.UserManager;

@Component("initServerCommonPowerUtil")
public class InitServerCommonPowerUtil {
	
	@Value("${stu.power.scan.pack}")
	private String scanPack;

	protected static Log log = LogFactory.getLog(InitServerCommonPowerUtil.class);

//	@Resource(name = "userManager")
//	private UserManager userManager;

	public List<PowerPoint> initPower() throws IOException {
		
		String[] packs = scanPack.split(",");
		
		List<PowerPoint> powerPonits = new ArrayList<PowerPoint>();
		
		Map<String,PowerPoint> powerPointMap=new HashMap<String,PowerPoint>();

		Set<Class<?>> classs = new HashSet<Class<?>>();
		for (String pack : packs) {
			classs.addAll(Common.getClasses(pack));
		}
		for (Class<?> c : classs) {
			if (c.isAnnotationPresent(Modular.class)) {
				Modular modular = c.getAnnotation(Modular.class);
				boolean empowered=  modular.isEmpowered();
				Method[] methods = c.getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(UserOperation.class)) {
						UserOperation userOperation = method.getAnnotation(UserOperation.class);
						if(userOperation.userOperationEnum()==CommonUserOperationEnum.Default&&userOperation.isMain()) {
							
							String key =modular.modelCode()+"."+userOperation.code();
							PowerPoint powerPonit = new PowerPoint();
							powerPonit.setModel(modular.modelName());
							powerPonit.setModeCode(modular.modelCode());
							powerPonit.setCode(key);
							powerPonit.setName(userOperation.name());
							powerPonit.setEmpowered(empowered);
							powerPonit.setJsjb(modular.jsjb());
							powerPointMap.put(key, powerPonit);
						}
						
					}
				}

			}
		}
		
		for(String key:powerPointMap.keySet()) {
			powerPonits.add(powerPointMap.get(key));
		}

		
		return powerPonits;
	}

}
