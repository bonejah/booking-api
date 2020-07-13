package com.lslb.app.bookingapi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.lslb.app.bookingapi.validators.DateStartValidator;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateStartValidator.class)
@Documented
public @interface DateStartAnnotation {
	String message() default "Start Date cannot be less than current date!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
