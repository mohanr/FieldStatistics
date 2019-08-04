package com.field.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class DateValidator {

    //Refactor to a separate class
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);

    public void validate(String timeStamp, Errors errors) {
        try{

            LocalDate date = LocalDate.parse(timeStamp, dateTimeFormatter);

        }catch( Exception e){
            errors.reject("Invalid Dete");
        }
    }
}
