package com.example.praksaprojekat.controller;

import com.example.praksaprojekat.dto.CandidateDto;
import com.example.praksaprojekat.model.Candidate;
import com.example.praksaprojekat.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("candidate/")
@CrossOrigin(origins = "http://localhost:5173")

public class CandidateController {

    private final CandidateService service;

    @GetMapping("all")
    public ResponseEntity<List<Candidate>> all(){
        return new ResponseEntity<>(service.allCandidates(),HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<Candidate> addCandidate(@RequestBody @Valid CandidateDto candidate) {
        return new ResponseEntity<>(service.addCandidate(candidate),HttpStatus.CREATED);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Candidate> findByName(@PathVariable String name) {
        return new ResponseEntity<>(service.findCandidateByName(name), HttpStatus.OK);
    }


    @GetMapping("addSkill")
    public ResponseEntity<Candidate> addSkillToCandidate(@RequestParam String email, @RequestParam String skill) {
        return new ResponseEntity<>(service.updateCandidateWithSkill(email, skill), HttpStatus.OK);
    }


    @PutMapping("/{candidateid}")
    public ResponseEntity<String> update(@PathVariable Integer candidateid, @RequestBody CandidateDto candidate) {
        service.updateCandidate(candidateid, candidate);
        return new ResponseEntity<>("Candidate successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("delete/{candidateId}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Integer candidateId) {
        service.deleteCandidate(candidateId);
        return new ResponseEntity<>("Candidate deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("deleteSkill/{candidateId}/skills/{skillName}")
    public ResponseEntity<String> deleteSkillFromCandidate(@PathVariable Integer candidateId, @PathVariable String skillName) {
        service.deleteSkillFromCandidate(candidateId, skillName);
        return new ResponseEntity<>("Skill successfully deleted", HttpStatus.OK);
    }



    @GetMapping("withSkill/{skillName}")
    public ResponseEntity<List<Candidate>> allCandidatesWithSkill(@PathVariable String skillName) {
        return new ResponseEntity<>(service.allCandidatesWithSkill(skillName), HttpStatus.OK);
    }

}
