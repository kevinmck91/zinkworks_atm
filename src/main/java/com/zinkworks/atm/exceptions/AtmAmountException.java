package com.zinkworks.atm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class AtmAmountException  extends RuntimeException{

	public AtmAmountException(String message)  {
		super(message);
	}
}
