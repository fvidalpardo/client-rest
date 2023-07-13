package com.globallogic.clientrest.exceptions;

import java.util.Date;

public class ErrorMessage {
	private Date timestamp;
	private int codigo;
	private String details;
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(Date timestamp, int codigo, String details) {
	    this.setTimestamp(timestamp);
	    this.setCodigo(codigo);
	    this.setDetails(details);
	  }

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
