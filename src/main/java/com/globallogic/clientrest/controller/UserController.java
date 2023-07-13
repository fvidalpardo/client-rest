package com.globallogic.clientrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.globallogic.clientrest.dto.UserDTO;
import com.globallogic.clientrest.exceptions.UserServiceException;
import com.globallogic.clientrest.model.User;
import com.globallogic.clientrest.model.request.UserRequest;
import com.globallogic.clientrest.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/sign-up")
	public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) throws UserServiceException{
		
			User returnValue = userService.createUser(userRequest);
			
			return new ResponseEntity<>(returnValue, HttpStatus.OK);
	}
	
	@GetMapping("/login/{token}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String token) {
		UserDTO responseValue = userService.getUser(token);
		
		return new ResponseEntity<>(responseValue, HttpStatus.OK);
	}
}
