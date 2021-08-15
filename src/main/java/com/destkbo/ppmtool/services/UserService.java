package com.destkbo.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.destkbo.ppmtool.domain.User;
import com.destkbo.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.destkbo.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//username has to be unique (costum exeption)
			newUser.setUsername(newUser.getUsername());
			//make sure tht pswd and confirmpswd match
			//we don't perssisit or show the confirmpswd
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"'already exist");
		}
		

	}

}
