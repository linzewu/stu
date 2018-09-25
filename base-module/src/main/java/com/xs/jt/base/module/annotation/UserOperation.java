package com.xs.jt.base.module.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.xs.jt.base.module.enums.CommonUserOperationEnum;

@Retention(RUNTIME)
@Target(METHOD)
public @interface UserOperation {
	
	
	public String code();
	
	public String name();
	
	public CommonUserOperationEnum userOperationEnum() default CommonUserOperationEnum.Default;
	
	public boolean isMain() default true;
	
	/**
	 * 是否允许被授权
	 * @return
	 */
	public boolean isEmpowered() default true;

}
