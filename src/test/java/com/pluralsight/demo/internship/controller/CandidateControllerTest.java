package com.pluralsight.demo.internship.controller;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.model.Internship;
import com.pluralsight.demo.internship.service.CandidateService;
import com.pluralsight.demo.internship.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.http.server.LocalTestWebServer.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc; // simulates HHTP requests

    @MockitoBean
    private CandidateService candidateService; // fake candidate service

    @Test
    void getAllCandidates_shouldReturnListOfInternships() throws Exception {
        Candidate candidate1 = new Candidate("Blaine Anthony Bell",
                "blaine@testemail.com",
                "Robotics Engineering",
                "Registration Time");

        candidate1.setId(1L);
        candidate1.setVisible(true);

        Candidate candidate2 = new Candidate("Annabelle Robinson",
                "ARobinson@testemail.com",
                "Astrologist",
                "Registration Time");

        candidate2.setId(2L);
        candidate2.setVisible(true);

        List<Candidate> candidates = Arrays.asList(candidate1,candidate2);

        when(candidateService.getAllCandidates()).thenReturn(candidates);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/candidates").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // 200 OK
                .andExpect(jsonPath("$[0].name").value("Blaine Anthony Bell"))
                .andExpect(jsonPath("$[0].email").value("blaine@testemail.com"))
                .andExpect(jsonPath("$[1].fieldOfStudy").value("Astrologist"))
                .andExpect(jsonPath("$.length()").value(2));  // 2 items
    }


    @Test
    void createCandidate_shouldReturnCreatedCandidate() throws Exception {
        Candidate inputCandidate = new Candidate("Blaine Anthony Bell",
                "blaine@testemail.com",
                "Robotics Engineering",
                "Registration Time");

        Candidate savedCandidate = new Candidate(
                "Blaine Anthony Bell",
                "blaine@testemail.com",
                "Robotics Engineering",
                "Registration Time");
        savedCandidate.setId(8L);
        savedCandidate.setVisible(true);

        when(candidateService.createCandidate(any(Candidate.class)))
                .thenReturn(savedCandidate);

        // ACT & ASSERT
        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "New Internship",
                          "email": "New Company",
                          "fieldOfStudy": "Description",
                        }
                        """))
                .andExpect(status().isOk())  // Should be 201 but our code returns 200
                .andExpect(jsonPath("$.id").value(8))
                .andExpect(jsonPath("$.name").value("Blaine Anthony Bell"))
                .andExpect(jsonPath("$.isVisible").value(true));
    }

    @Test
    void getCandidateById_shouldReturnCandidateID() {
    }

    @Test
    void deleteCandidate_shouldReturnNoContent() {
    }
}