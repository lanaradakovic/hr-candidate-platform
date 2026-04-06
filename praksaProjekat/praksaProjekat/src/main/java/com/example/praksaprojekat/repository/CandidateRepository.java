package com.example.praksaprojekat.repository;

import com.example.praksaprojekat.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {


    Optional<Candidate> findCandidateByEmail(String email);

    Optional<Candidate> findCandidateByFullName(String fullName);

    List<Candidate> findCandidatesBySkills_Name(String skillsName);




}