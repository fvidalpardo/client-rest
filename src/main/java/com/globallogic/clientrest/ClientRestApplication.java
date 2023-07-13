package com.globallogic.clientrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.globallogic.clientrest.utils.CommonUtils;
import com.globallogic.clientrest.utils.JwtTokenUtil;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ClientRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientRestApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	 return new BCryptPasswordEncoder();
	}
	@Bean
	public JwtTokenUtil jwtTokenUtil() {
	 return new JwtTokenUtil();
	}
	
	@Bean
	public CommonUtils commonUtils() {
	 return new CommonUtils();
	}
}
