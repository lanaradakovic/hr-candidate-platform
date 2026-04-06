package com.example.praksaprojekat.controller;

import com.example.praksaprojekat.dto.CandidateDto;
import com.example.praksaprojekat.model.Candidate;
import com.example.praksaprojekat.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CandidateService service;

    @Test
    void shouldAddCandidate() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setFullName("Pera Peric");
        candidate.setEmail("pera@gmail.com");
        candidate.setContactNumber("063123456");
        candidate.setDateOfBirth(LocalDate.of(2000, 1, 1));

        when(service.addCandidate(any(CandidateDto.class)))
                .thenReturn(candidate);

        mockMvc.perform(post("/candidate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "fullName": "Pera Peric",
                    "email": "pera@gmail.com",
                    "contactNumber": "063123456",
                    "dateOfBirth": "2000-01-01"
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Pera Peric"));
    }

    @Test
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing() throws Exception {
        mockMvc.perform(post("/candidate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "fullName": "Pera Peric"
                }
            """))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnAllCandidates() throws Exception {
        Candidate c1 = new Candidate();
        c1.setFullName("Pera Peric");
        c1.setEmail("pera@gmail.com");

        Candidate c2 = new Candidate();
        c2.setFullName("Iva Ivić");
        c2.setEmail("iva@gmail.com");

        when(service.allCandidates()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/candidate/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Pera Peric"))
                .andExpect(jsonPath("$[1].fullName").value("Iva Ivić"));
    }

    @Test
    void shouldReturnCandidateByName() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setFullName("Pera Peric");
        candidate.setEmail("pera@gmail.com");

        when(service.findCandidateByName("Pera Peric")).thenReturn(candidate);

        mockMvc.perform(get("/candidate/name/Pera Peric"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Pera Peric"))
                .andExpect(jsonPath("$.email").value("pera@gmail.com"));
    }




    @Test
    void shouldDeleteCandidate() throws Exception {
        doNothing().when(service).deleteCandidate(1);

        mockMvc.perform(delete("/candidate/delete/{candidateId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidate deleted successfully"));
    }


    @Test
    void shouldUpdateCandidate() throws Exception {
        Candidate updated = new Candidate();
        updated.setFullName("Pera Peric Updated");
        updated.setEmail("pera@gmail.com");

        when(service.updateCandidate(any(Integer.class), any(CandidateDto.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/candidate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "fullName": "Pera Peric Updated",
                "email": "pera@gmail.com",
                "contactNumber": "063123456",
                "dateOfBirth": "2000-01-01"
            }
        """))
                .andExpect(status().isOk())
                .andExpect(content().string("Candidate successfully updated"));
    }


    @Test
    void shouldAddSkillToCandidate() throws Exception {
        Candidate candidate = new Candidate();
        candidate.setFullName("Iva Ivić");
        candidate.setEmail("iva@gmail.com");

        when(service.updateCandidateWithSkill("iva@gmail.com", "German"))
                .thenReturn(candidate);

        mockMvc.perform(get("/candidate/addSkill")
                        .param("email", "iva@gmail.com")
                        .param("skill", "German"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("iva@gmail.com"));
    }






}
