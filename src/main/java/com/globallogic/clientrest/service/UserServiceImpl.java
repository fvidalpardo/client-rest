package com.globallogic.clientrest.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globallogic.clientrest.dto.UserDTO;
import com.globallogic.clientrest.exceptions.UserServiceException;
import com.globallogic.clientrest.model.Phone;
import com.globallogic.clientrest.model.User;
import com.globallogic.clientrest.model.request.UserRequest;
import com.globallogic.clientrest.repository.UserRepository;
import com.globallogic.clientrest.utils.CommonUtils;
import com.globallogic.clientrest.utils.JwtTokenUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService{
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtTokenUtil jwtTokenUtil;
	private final CommonUtils commonUtils;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
    public UserServiceImpl(
    		UserRepository userRepository,
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		JwtTokenUtil jwtTokenUtil,
    		CommonUtils commonUtils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.commonUtils = commonUtils;
    }

	@Override
	public User createUser(UserRequest userRequest) throws UserServiceException{
		User newUser = new User();
		
		//Validations
		
		if (!commonUtils.emailValidation(userRequest.getEmail())) 
			throw new UserServiceException("Email: " + userRequest.getEmail() +" has invalid email format");
		
		if (!commonUtils.passwordValidation(userRequest.getPassword())) 
			throw new UserServiceException("Invalid password format");
		
		// Check if user already exists
        if (userRepository.checkUserByEmail(userRequest.getEmail())) {
            throw new UserServiceException("The User: " + userRequest.getName() + ", already exists");
        }
		
        newUser = populateUserModel(userRequest);
        userRepository.save(newUser);
        return newUser;
	}
	
	@Override
	public UserDTO getUser(String token) throws UserServiceException{
		UserDTO returnValue = new UserDTO();
		
		if (!jwtTokenUtil.validateToken(token)) {
			throw new UserServiceException("Token is expired");
		}
		User existingUser = userRepository.getUserByToken(token);
        if (existingUser == null) {
            throw new UserServiceException("token: " + token + "did not found any user");
        }
        
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        existingUser.setLastLogin(new Date(stamp.getTime()));
        existingUser.setToken(jwtTokenUtil.generateToken(existingUser));
        BeanUtils.copyProperties(existingUser, returnValue);
        existingUser = userRepository.save(existingUser);
        return returnValue;
	}
	
	public User populateUserModel(UserRequest userRequest) {
		User returnValue = new User();
		List<Phone> phonesList = new ArrayList<>();
		
		userRequest.getPhones().forEach(phoneRequest -> {
			Phone phone = new Phone();
			phone.setNumber(phoneRequest.getNumber());
			phone.setCitycode(phoneRequest.getCitycode());
			phone.setCountrycode(phoneRequest.getCountrycode());
			
			phonesList.add(phone);
		});
		
		BeanUtils.copyProperties(userRequest, returnValue);
        // Generate secure password
        returnValue.setToken(jwtTokenUtil.generateToken(returnValue));
        returnValue.setIsActive(true);
        
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        returnValue.setCreated(new Date(stamp.getTime()));
        returnValue.setPhones(phonesList);
        returnValue.setLastLogin(new Date(stamp.getTime()));
        returnValue.setPassword(bCryptPasswordEncoder.encode(returnValue.getPassword()));
        
        return returnValue;
	}
}
