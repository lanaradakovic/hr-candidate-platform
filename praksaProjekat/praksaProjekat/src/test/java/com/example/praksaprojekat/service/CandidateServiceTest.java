package com.example.praksaprojekat.service;

import com.example.praksaprojekat.dto.CandidateDto;
import com.example.praksaprojekat.model.Candidate;
import com.example.praksaprojekat.model.Skill;
import com.example.praksaprojekat.repository.CandidateRepository;
import com.example.praksaprojekat.repository.SkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    void shouldAddCandidateSuccessfully() {
        CandidateDto dto = new CandidateDto();
        dto.setEmail("test@gmail.com");
        dto.setFullName("Test Test");
        dto.setContactNumber("123");
        dto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Candidate savedCandidate = new Candidate();
        savedCandidate.setEmail("test@gmail.com");
        savedCandidate.setFullName("Test Test");

        when(candidateRepository.findCandidateByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        when(candidateRepository.save(any(Candidate.class)))
                .thenReturn(savedCandidate);

        Candidate result = candidateService.addCandidate(dto);

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        assertEquals("Test Test", result.getFullName());

        verify(candidateRepository).save(any(Candidate.class));
    }

    @Test
    void shouldThrowExceptionWhenCandidateAlreadyExists() {
        CandidateDto dto = new CandidateDto();
        dto.setEmail("test@gmail.com");
        dto.setFullName("Test Test");
        dto.setContactNumber("123");
        dto.setDateOfBirth(LocalDate.of(2000, 1, 1));

        Candidate existingCandidate = new Candidate();
        existingCandidate.setEmail("test@gmail.com");

        when(candidateRepository.findCandidateByEmail("test@gmail.com"))
                .thenReturn(Optional.of(existingCandidate));

        assertThrows(RuntimeException.class, () -> {
            candidateService.addCandidate(dto);
        });

        verify(candidateRepository, never()).save(any(Candidate.class));
    }


    @Test
    void shouldFindCandidateByName() {
        Candidate candidate = new Candidate();
        candidate.setFullName("Pera Peric");
        candidate.setSkills(new HashSet<>());

        when(candidateRepository.findCandidateByFullName("Pera Peric"))
                .thenReturn(Optional.of(candidate));

        Candidate result = candidateService.findCandidateByName("Pera Peric");

        assertNotNull(result);
        assertEquals("Pera Peric", result.getFullName());
    }


    @Test
    void shouldThrowExceptionWhenCandidateWithNameDoesNotExist() {
        when(candidateRepository.findCandidateByFullName("Nepostojeci Kandidat"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            candidateService.findCandidateByName("Nepostojeci Kandidat");
        });
    }



    @Test
    void shouldAddSkillToCandidate() {
        Candidate candidate = new Candidate();
        candidate.setEmail("test@gmail.com");
        candidate.setSkills(new HashSet<>());

        Skill skill = new Skill();
        skill.setName("Java");

        when(candidateRepository.findCandidateByEmail("test@gmail.com"))
                .thenReturn(Optional.of(candidate));

        when(skillRepository.findByName("Java"))
                .thenReturn(Optional.of(skill));

        when(candidateRepository.save(candidate))
                .thenReturn(candidate);

        Candidate result = candidateService.updateCandidateWithSkill("test@gmail.com", "Java");

        assertTrue(result.getSkills().contains(skill));
        verify(candidateRepository).save(candidate);
    }

    @Test
    void shouldUpdateCandidateSuccessfully() {
        Candidate existingCandidate = new Candidate();
        existingCandidate.setId(1);
        existingCandidate.setFullName("Pera Peric");
        existingCandidate.setEmail("pera@gmail.com");
        existingCandidate.setContactNumber("123");
        existingCandidate.setDateOfBirth(LocalDate.of(2000, 1, 1));

        CandidateDto dto = new CandidateDto();
        dto.setFullName("Pera Peric Updated");
        dto.setEmail("peraupdated@gmail.com");
        dto.setContactNumber("999");
        dto.setDateOfBirth(LocalDate.of(1999, 5, 5));

        when(candidateRepository.findById(1)).thenReturn(Optional.of(existingCandidate));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(existingCandidate);

        Candidate result = candidateService.updateCandidate(1, dto);

        assertNotNull(result);
        assertEquals("Pera Peric Updated", existingCandidate.getFullName());
        assertEquals("peraupdated@gmail.com", existingCandidate.getEmail());
        assertEquals("999", existingCandidate.getContactNumber());
        assertEquals(LocalDate.of(1999, 5, 5), existingCandidate.getDateOfBirth());

        verify(candidateRepository).save(existingCandidate);
    }


    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCandidate() {
        CandidateDto dto = new CandidateDto();
        dto.setFullName("Novo Ime");
        dto.setEmail("novo@gmail.com");

        when(candidateRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            candidateService.updateCandidate(1, dto);
        });

        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    void shouldDeleteCandidateSuccessfully() {
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setFullName("Pera Peric");

        when(candidateRepository.findById(1)).thenReturn(Optional.of(candidate));

        candidateService.deleteCandidate(1);

        verify(candidateRepository).delete(candidate);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCandidate() {
        when(candidateRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            candidateService.deleteCandidate(1);
        });

        verify(candidateRepository, never()).delete(any(Candidate.class));
    }

    @Test
    void shouldThrowExceptionWhenAddingSkillToNonExistingCandidate() {
        when(candidateRepository.findCandidateByEmail("test@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            candidateService.updateCandidateWithSkill("test@gmail.com", "Java");
        });

        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    void shouldThrowExceptionWhenSkillDoesNotExist() {
        Candidate candidate = new Candidate();
        candidate.setEmail("test@gmail.com");
        candidate.setSkills(new HashSet<>());

        when(candidateRepository.findCandidateByEmail("test@gmail.com"))
                .thenReturn(Optional.of(candidate));

        when(skillRepository.findByName("Java"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            candidateService.updateCandidateWithSkill("test@gmail.com", "Java");
        });

        verify(candidateRepository, never()).save(any(Candidate.class));
    }

    @Test
    void shouldReturnAllCandidates() {
        Candidate c1 = new Candidate();
        c1.setFullName("Pera Peric");

        Candidate c2 = new Candidate();
        c2.setFullName("Iva Ivic");

        when(candidateRepository.findAll()).thenReturn(java.util.List.of(c1, c2));

        var result = candidateService.allCandidates();

        assertEquals(2, result.size());
        verify(candidateRepository).findAll();
    }

}
