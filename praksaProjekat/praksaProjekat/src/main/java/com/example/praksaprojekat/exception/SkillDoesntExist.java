package com.example.praksaprojekat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SkillDoesntExist extends RuntimeException {
    private String message;
}
