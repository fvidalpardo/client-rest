package com.globallogic.clientrest.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private Date created;
	
	private Date lastLogin;
	
	private String token;
	
	private Boolean isActive;
	
	private String name;
	
	private String email;
	
	private String password;
	
	@OneToMany(targetEntity = Phone.class, cascade = CascadeType.ALL)
	private List<Phone> phones;
	
	public User() {}
	
	public User(Date created, Date lastLogin, Boolean isActive, String name, String email, String password,
			List<Phone> phones) {
		super();
		this.created = created;
		this.lastLogin = lastLogin;
		this.isActive = isActive;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phones = phones;
	}
	// Getters and Setters
	public UUID getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
}
