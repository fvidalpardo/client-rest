package com.globallogic.clientrest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	private static final String EMAIL_PATTERN = 
			"^[A-Za-z0-9+_.-]+@(.+)$";
	private static final String PASSWORD_PATTERN =
            "^(?=(.*[A-Z]){1})(?!(.*[A-Z]){2})(?=(.*\\d){2})(?!(.*\\d){3})[a-zA-Z0-9]{8,12}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
	

	public boolean emailValidation(String emailAddress) {
	    return Pattern.compile(EMAIL_PATTERN)
	      .matcher(emailAddress)
	      .matches();
	}
	
	public boolean passwordValidation(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
