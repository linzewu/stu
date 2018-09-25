package com.xs.jt.base.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Modular {
	
	public String modelName();
	
	public String modelCode();
	
	public boolean isEmpowered() default true;

}
