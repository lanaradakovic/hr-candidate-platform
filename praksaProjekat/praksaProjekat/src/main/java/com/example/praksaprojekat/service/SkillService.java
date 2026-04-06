package com.example.praksaprojekat.service;

import com.example.praksaprojekat.dto.SkillDto;
import com.example.praksaprojekat.exception.SkillDoesntExist;
import com.example.praksaprojekat.model.Candidate;
import com.example.praksaprojekat.model.Skill;
import com.example.praksaprojekat.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAll(){
        return skillRepository.findAll();
    }

    public Skill addSkill(SkillDto skillDto){
        if(skillRepository.findByName(skillDto.getName()).isPresent()){
            throw new SkillDoesntExist("Skill with that name already exists");
        }

        Skill newSkill = new Skill();
        newSkill.setName(skillDto.getName());
        return skillRepository.save(newSkill);
    }


}
