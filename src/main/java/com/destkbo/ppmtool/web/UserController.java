package com.destkbo.ppmtool.web;

import javax.validation.Valid;

import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.destkbo.ppmtool.domain.User;
import com.destkbo.ppmtool.payload.JWTLoginSucessResponse;
import com.destkbo.ppmtool.payload.LoginRequest;
import com.destkbo.ppmtool.security.JwtTokenProvider;
import com.destkbo.ppmtool.services.MapValidationErrorService;
import com.destkbo.ppmtool.services.UserService;
import com.destkbo.ppmtool.validator.UserValidator;


import static com.destkbo.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<?> AuthenticatedUserRealm (@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if(errorMap != null) return errorMap;
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt));
	}
	
	
	
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
		//validate password match
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap =mapValidationErrorService.MapValidationService(result);
		if(errorMap != null)return errorMap;
		
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
	}

}
