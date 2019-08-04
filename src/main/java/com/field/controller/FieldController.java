package com.field.controller;


import com.statistics.FieldStatistics;
import com.statistics.Telemetry;
import com.statistics.TelemetrySummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@RestController
public class FieldController {


    //Refactor to a separate class
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);

    FieldStatistics fieldStatistics = new FieldStatistics();

    Logger logger = LoggerFactory.getLogger("com.statistics");
    @PostMapping("/field-conditions")
	public ResponseEntity<?> record(@Valid @RequestBody Telemetry telemetry,  Errors errors) {


        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        LocalDate date = LocalDate.parse(telemetry.getTimeStamp(), dateTimeFormatter);
        fieldStatistics.record( new Float( telemetry.getValue()).longValue(), date.toEpochDay() );
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


    @GetMapping(path="/field-statistics")
    public ResponseEntity<?> summarize() throws TelemetrySummaryException {
        double[] dataSummary = fieldStatistics.summarize();
        TelemetrySummary summary = new TelemetrySummary(dataSummary[0],dataSummary[1],dataSummary[2]);
        logger.debug( "Summary {}",summary.toString());
        return new ResponseEntity<Object>(summary, HttpStatus.OK);

    }
}
