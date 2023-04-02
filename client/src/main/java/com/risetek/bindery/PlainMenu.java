package com.risetek.bindery;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.risetek.utils.Icons.Icon;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface PlainMenu {
	String token();
	String title();
	Class<? extends Icon> iconClass() default Icon.class;
	int order();
	boolean isEnabled() default true;
}
