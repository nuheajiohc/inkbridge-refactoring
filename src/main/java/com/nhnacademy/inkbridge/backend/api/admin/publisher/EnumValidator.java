package com.nhnacademy.inkbridge.backend.api.admin.publisher;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

	private Set<String> allowedValues;
	private boolean ignoreCase;
	private String messageTemplate;

	@Override
	public void initialize(EnumValid constraintAnnotation) {
		ignoreCase = constraintAnnotation.ignoreCase();
		Enum<?>[] enumConstants = constraintAnnotation.target().getEnumConstants();
		allowedValues = Arrays.stream(enumConstants).map(Enum::name).collect(Collectors.toSet());

		messageTemplate = convertMessageTemplate(constraintAnnotation.message());
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		String convertedValue = checkIgnoreCase(value);

		if (allowedValues.contains(convertedValue)) {
			return true;
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(messageTemplate)
			.addConstraintViolation();
		return false;
	}

	private String convertMessageTemplate(String message) {
		return message.replace("{enumValues}", String.join(" | ", allowedValues));
	}

	private String checkIgnoreCase(String value) {
		return this.ignoreCase ? value.toUpperCase() : value;
	}

}
