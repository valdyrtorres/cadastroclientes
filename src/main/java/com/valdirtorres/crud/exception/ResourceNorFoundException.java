package com.valdirtorres.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNorFoundException extends RuntimeException {

	private static final long serialVersionUID = 8996323857603215092L;
	
	public ResourceNorFoundException(String exception) {
		super(exception);
	}

}
