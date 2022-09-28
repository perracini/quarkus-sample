package org.acme.quarkus.sample;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringAsPhoneValidator implements ConstraintValidator<StringAsPhoneValid, String> {

	/*private String value;

	@Override
	public void initialize(StringAsPhoneValid constraintAnnotation) {
		this.value = constraintAnnotation.value();
	}*/

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if (value.matches("[0-9]+") && value.length() > 7 && value.length() < 10) {
			return true;
		}
		return false;
	}

}
