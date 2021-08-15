package com.destkbo.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.destkbo.ppmtool.domain.User;

@Component
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {


			User user = (User) target;
			if(user.getPassword().length()<6) {
				errors.rejectValue("password","Length","Password must be atleat 6 charcters");
			}
			if(!user.getPassword().equals(user.getConfirmPassword())) {
				errors.rejectValue("confirmPassword","Match","Passwords must match");

			}
		
	}//confirmPassword

}
