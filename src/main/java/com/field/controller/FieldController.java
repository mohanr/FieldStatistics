package com.field.controller;


import com.statistics.Telemetry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class FieldController {

	@PostMapping("/field-conditions")
	public ResponseEntity<?> record(@Valid @RequestBody Telemetry telemetry) {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
