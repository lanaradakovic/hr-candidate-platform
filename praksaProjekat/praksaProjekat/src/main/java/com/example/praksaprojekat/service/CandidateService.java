package com.example.praksaprojekat.service;

import com.example.praksaprojekat.dto.CandidateDto;
import com.example.praksaprojekat.exception.CandidateDoesntExist;
import com.example.praksaprojekat.exception.SkillDoesntExist;
import com.example.praksaprojekat.model.Candidate;
import com.example.praksaprojekat.model.Skill;
import com.example.praksaprojekat.repository.CandidateRepository;
import com.example.praksaprojekat.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final SkillRepository skillRepository;

    public List<Candidate> allCandidates(){
        return candidateRepository.findAll();
    }   //radi


    public Candidate addCandidate(CandidateDto candidateDto){
        Optional<Candidate> candidateByEmail = candidateRepository.findCandidateByEmail(candidateDto.getEmail());
        if(candidateByEmail.isPresent()){
            throw new CandidateDoesntExist("Candidate already exists");
        }

        Candidate candidate = new Candidate();
        candidate.setEmail(candidateDto.getEmail());
        candidate.setDateOfBirth(candidateDto.getDateOfBirth());
        candidate.setFullName(candidateDto.getFullName());
        candidate.setContactNumber(candidateDto.getContactNumber());

        return candidateRepository.save(candidate);
    }

    public Candidate findCandidateByName(String name){
        Optional<Candidate> candidateName = candidateRepository.findCandidateByFullName(name);
        if(candidateName.isEmpty()){
            throw new CandidateDoesntExist("There is no candidate with that name");
        }
        return candidateName.get();
    }

    public Candidate updateCandidateWithSkill(String email, String skillName){
        Candidate candidateExists = candidateRepository.findCandidateByEmail(email)
                .orElseThrow(() -> new CandidateDoesntExist("There is no candidate with that email"));

        Skill existingSkill = skillRepository.findByName(skillName)
                .orElseThrow(()-> new SkillDoesntExist("There is no skill with that name"));

        if(candidateExists.getSkills().contains(existingSkill)){
            throw new CandidateDoesntExist("Candidate already has this skill");
        }

        candidateExists.getSkills().add(existingSkill);
        return  candidateRepository.save(candidateExists);

    }

    public Candidate updateCandidate(Integer candidateId, CandidateDto candidateDto) {
        Candidate candidateExists = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateDoesntExist("There is no candidate with that id"));

        candidateExists.setEmail(candidateDto.getEmail());
        candidateExists.setDateOfBirth(candidateDto.getDateOfBirth());
        candidateExists.setFullName(candidateDto.getFullName());
        candidateExists.setContactNumber(candidateDto.getContactNumber());

        return candidateRepository.save(candidateExists);
    }



    public void deleteCandidate(Integer candidateId){
        Candidate findCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(()->new CandidateDoesntExist("There is no candidate with that id"));

        candidateRepository.delete(findCandidate);

    }

    public String deleteSkillFromCandidate(Integer candidateId, String skillName){
        Candidate findCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(()->new CandidateDoesntExist("There is no candidate with that id"));

        Skill skillExists = skillRepository.findByName(skillName)
                .orElseThrow(()-> new SkillDoesntExist("Skill does not exist"));

        if(findCandidate.getSkills().contains(skillExists)){
            findCandidate.getSkills().remove(skillExists);
            candidateRepository.save(findCandidate);
            return "Skill from candidate is successfully  deleted";
        }
        else{
            return "Candidate does not have this skill";
        }

    }



    public List<Candidate> allCandidatesWithSkill(String skillName){
        List<Candidate> candidates = candidateRepository.findCandidatesBySkills_Name(skillName);
        if(candidates.isEmpty()){
            throw new CandidateDoesntExist("There is no candidates with " + skillName + " skill");
        }
        return candidates;
    }

}
