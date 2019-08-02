package com.field.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldController {

	@PostMapping("/field-conditions")
	public ResponseEntity<?> singleConditions() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
