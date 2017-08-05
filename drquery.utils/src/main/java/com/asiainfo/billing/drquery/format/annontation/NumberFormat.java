
package com.asiainfo.billing.drquery.format.annontation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberFormat {

	Style style() default Style.NUMBER;

	String pattern() default "";

	public enum Style {

		NUMBER,
		
		CURRENCY,

		PERCENT
	}

}
