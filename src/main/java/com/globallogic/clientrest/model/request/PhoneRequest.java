package com.globallogic.clientrest.model.request;

import jakarta.validation.constraints.NotNull;

public class PhoneRequest {
	@NotNull
	private Long number;
	@NotNull
	private int citycode;
	@NotNull
	private String countrycode;
	
	public PhoneRequest(@NotNull Long number, @NotNull int citycode, @NotNull String countrycode) {
		super();
		this.number = number;
		this.citycode = citycode;
		this.countrycode = countrycode;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public int getCitycode() {
		return citycode;
	}
	public void setCitycode(int citycode) {
		this.citycode = citycode;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
}
