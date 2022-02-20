package com.zinkworks.atm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectPinException extends RuntimeException {

	public IncorrectPinException(String message) {
		super(message);
	}

}