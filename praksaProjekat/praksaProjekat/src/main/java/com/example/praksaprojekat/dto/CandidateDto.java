package com.example.praksaprojekat.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4, max = 10)
    private String contactNumber;

    @NotNull
    @Past
    private LocalDate dateOfBirth;

    @NotBlank
    private String fullName;
}
