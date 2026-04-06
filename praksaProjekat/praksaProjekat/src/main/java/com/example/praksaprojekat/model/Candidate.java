package com.example.praksaprojekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "candidate", schema = "praksa")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCandidate", nullable = false)
    private Integer id;

    @Email
    @NotBlank(message = "Email is required")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = "contact number is required")
    @Size(min = 4, max = 10)
    @Column(name = "contactNumber")
    private String contactNumber;

    @Past(message = "Date of birth must be in the past")
    @NotNull(message = "Date of birth is required")
    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @NotBlank(message = "name is required")
    @Column(name = "fullName")
    private String fullName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "candidate_skill",
            schema = "praksa",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new LinkedHashSet<>();
}