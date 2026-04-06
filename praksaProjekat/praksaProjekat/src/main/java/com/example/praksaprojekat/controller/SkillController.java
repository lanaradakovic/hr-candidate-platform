package com.example.praksaprojekat.controller;

import com.example.praksaprojekat.dto.SkillDto;
import com.example.praksaprojekat.model.Skill;
import com.example.praksaprojekat.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("skill/")
@CrossOrigin(origins = "http://localhost:5173")

public class SkillController {

    private final SkillService skillService;

    @GetMapping("all")
    public ResponseEntity<List<Skill>> allSkill(){

        return new ResponseEntity<>(skillService.getAll(),HttpStatus.OK);
    }

    @PostMapping("addSkill")
    public ResponseEntity<Skill> addSkill(@RequestBody SkillDto skillDto){
        Skill skill = skillService.addSkill(skillDto);
        return new  ResponseEntity<>(skill,HttpStatus.CREATED);

    }
}
