package com.zinkworks.atm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmController {
	
	@GetMapping("test")
	public String myMethod() {

		return "TEST";

	}

}
