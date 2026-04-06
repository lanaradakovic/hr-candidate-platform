package com.example.praksaprojekat.repository;

import com.example.praksaprojekat.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {

    Optional<Skill> findByName(String name);


}