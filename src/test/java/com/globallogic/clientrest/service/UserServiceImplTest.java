package com.globallogic.clientrest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.globallogic.clientrest.exceptions.UserServiceException;
import com.globallogic.clientrest.model.Phone;
import com.globallogic.clientrest.model.User;
import com.globallogic.clientrest.model.request.PhoneRequest;
import com.globallogic.clientrest.model.request.UserRequest;
import com.globallogic.clientrest.repository.UserRepository;
import com.globallogic.clientrest.utils.CommonUtils;
import com.globallogic.clientrest.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class UserServiceImplTest {
	
	private UserServiceImpl underTest;
	@Mock private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private JwtTokenUtil jwtTokenUtil= new JwtTokenUtil();
	private CommonUtils commonUtils= new CommonUtils();
	
	@BeforeEach
	void setUp() {
		underTest = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtTokenUtil, commonUtils);
	}
	
	@Test 
	void itShouldCreateAnUserBasedOfAnUserRequest() {
		//given
		List<PhoneRequest> phonesRequest = new ArrayList<>();
		PhoneRequest phoneRequest = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phonesRequest.add(phoneRequest);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phonesRequest
				);
		
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		User newUser = new User(
				new Date(stamp.getTime()), new Date(stamp.getTime()), true, "Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phones
				);
		newUser.setToken(jwtTokenUtil.generateToken(newUser));
		//when
		User expected = underTest.populateUserModel(userRequest);
		//then
		assertThat(expected).usingRecursiveComparison()
		.ignoringFields("token", "created", "lastLogin", "password")
		.isEqualTo(newUser);
	}
	
	@Test
	void itShouldCheckIfIsCreatingUser() {
		//given
		User newUser = new User();
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phones
				);
		newUser = underTest.populateUserModel(userRequest);
		//when
		underTest.createUser(userRequest);
		//then
		ArgumentCaptor<User> userArgumentCaptor =
				ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userArgumentCaptor.capture());
		User capturedUser = userArgumentCaptor.getValue();
		assertThat(capturedUser).usingRecursiveComparison()
		.ignoringFields("token", "created", "lastLogin", "password")
		.isEqualTo(newUser);
	}
	
	@Test
	void itShouldIfIsGettingUser() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phones
				);
		underTest.createUser(userRequest);
		//when
		Boolean expected = userRepository.checkUserByEmail(userRequest.getEmail());
		//then
		assertThat(expected).isTrue();
	}
	
	@Test
	void willThrowWhenEmailIsInvalid() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		// email has invalid format
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb", 
				"greg4grfdZ6", 
				phones
				);
		//when
		//then
		assertThatThrownBy(() -> underTest.createUser(userRequest))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("Email: " + userRequest.getEmail() +" has invalid email format");
	}
	
	@Test
	void willThrowWhenPasswordHasExtraUppercase() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4HrfdZ6", 
				phones
				);
		//when
		//then
		assertThatThrownBy(() -> underTest.createUser(userRequest))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("Invalid password format");
	}
	
	@Test
	void willThrowWhenPasswordHasMoreThanTwoNumbers() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4hrf8dZ6", 
				phones
				);
		//when
		//then
		assertThatThrownBy(() -> underTest.createUser(userRequest))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("Invalid password format");
	}
	
	@Test
	void willThrowWhenPasswordHasMoreThanTwelveCharacters() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4HrfdZ6hsd", 
				phones
				);
		//when
		//then
		assertThatThrownBy(() -> underTest.createUser(userRequest))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("Invalid password format");
	}
	
	@Test
	void willThrowWhenUserAlreadyExists() {
		//given
		List<PhoneRequest> phones = new ArrayList<>();
		PhoneRequest phone = new PhoneRequest(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		UserRequest userRequest = new UserRequest(
				"Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phones
				);
		given(userRepository.checkUserByEmail(userRequest.getEmail()))
			.willReturn(true);
		//when
		//then
		assertThatThrownBy(() -> underTest.createUser(userRequest))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("The User: " + userRequest.getName() + ", already exists");
	}
	
	@Test
	void willThrowWhenUserIsNotFoundByTokenGiven() {
		//given
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone(
				34563474L, 
				7,
				"25"
				);
		phones.add(phone);
		User userRequest = new User(
				new Date(stamp.getTime()), new Date(stamp.getTime()), true, "Jose Alvarez", 
				"jalvarezb@example.com", 
				"greg4grfdZ6", 
				phones
				);
		String token= jwtTokenUtil.generateToken(userRequest);
		
		given(userRepository.getUserByToken(token))
		.willReturn(null);
		//when
		//then
		assertThatThrownBy(() -> underTest.getUser(token))
		.isInstanceOf(UserServiceException.class)
		.hasMessageContaining("token: " + token + "did not found any user");
	}
}
