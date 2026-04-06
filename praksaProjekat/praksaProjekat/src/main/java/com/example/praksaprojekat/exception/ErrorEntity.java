package com.example.praksaprojekat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ErrorEntity {

    private String message;
    private LocalDate localDate;

}
