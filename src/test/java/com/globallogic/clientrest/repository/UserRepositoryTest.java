package com.globallogic.clientrest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.globallogic.clientrest.model.Phone;
import com.globallogic.clientrest.model.User;
import com.globallogic.clientrest.utils.JwtTokenUtil;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private UserRepository underTest;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Test
	void itShouldCheckIfEmailExists() {
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
		userRequest.setToken(jwtTokenUtil.generateToken(userRequest));
		underTest.save(userRequest);
		//when
		Boolean expected = underTest.checkUserByEmail(userRequest.getEmail());
		//then
		assertThat(expected).isTrue();
	}
	
	@Test
	void itShouldBringUserWithToken() {
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
		userRequest.setToken(jwtTokenUtil.generateToken(userRequest));
		underTest.save(userRequest);
		//when
		User expected = underTest.getUserByToken(userRequest.getToken());
		//then
		assertThat(expected).isEqualTo(userRequest);
	}
}
