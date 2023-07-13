package com.globallogic.clientrest.model.request;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
public class UserRequest {
	@NotNull
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotNull
	private List<PhoneRequest> phones;
	
	public UserRequest(@NotNull String name, @NotBlank String email, @NotBlank String password,
			@NotNull List<PhoneRequest> phones) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<PhoneRequest> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneRequest> phones) {
		this.phones = phones;
	}
}
