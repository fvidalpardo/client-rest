package com.globallogic.clientrest.service;

import com.globallogic.clientrest.dto.UserDTO;
import com.globallogic.clientrest.model.User;
import com.globallogic.clientrest.model.request.UserRequest;

public interface UserService{
	User createUser(UserRequest userRequest);
	UserDTO getUser(String token);
}
