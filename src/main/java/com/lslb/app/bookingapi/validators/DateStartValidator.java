package com.lslb.app.bookingapi.validators;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lslb.app.bookingapi.annotations.DateStartAnnotation;

public class DateStartValidator implements ConstraintValidator<DateStartAnnotation, Calendar> {
	
	@Override
	public void initialize(DateStartAnnotation constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(Calendar value, ConstraintValidatorContext context) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		
		System.out.println("Start Date => " + value.getTime());
		
		if (value.getTime().before(calendar.getTime())) {
			return false;
		}
		
		return value.after(calendar);
	}

}
