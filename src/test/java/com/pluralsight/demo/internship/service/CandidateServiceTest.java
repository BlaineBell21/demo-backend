package com.pluralsight.demo.internship.service;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    void getAllCandidates_shouldReturnOnlyVisible() {

        Candidate candidate1 = new Candidate("Name 1","Email 1","Field Of Study 1","Registered At 1");
        candidate1.setVisible(true);

        Candidate candidate2 = new Candidate("Name 2","Email 2","Field Of Study 2","Registered At 2");
        candidate2.setVisible(true);

        Candidate notVisible = new Candidate("Name 3","Email 3","Field Of Study 3","Registered At 3");
        notVisible.setVisible(false);

        List<Candidate> allCandidates = Arrays.asList(candidate1, candidate2, notVisible);
        when(candidateRepository.findAll()).thenReturn(allCandidates);

        List<Candidate> result = candidateService.getAllCandidates();

        assertEquals(2, result.size());
        assertTrue(result.contains(candidate1));
        assertTrue(result.contains(candidate2));
        assertFalse(result.contains(notVisible));
    }

    @Test
    void createCandidate_whenIsVisibleTrue_shouldBeVisible() {
        ReflectionTestUtils.setField(candidateService, "visibleByDefault", true);
        Candidate inputCandidate = new Candidate("Name 1","Email 1","Field Of Study 1","Registered At 1");
        inputCandidate.setVisible(true);

        Candidate savedCandidate = new Candidate("Name 1","Email 1","Field Of Study 1","Registered At 1");
        savedCandidate.setId(1L);
        savedCandidate.setVisible(true);

        when(candidateRepository.save(any(Candidate.class))).thenReturn(inputCandidate);

        Candidate result = candidateService.createCandidate(inputCandidate);

        assertTrue(result.isVisible());
        verify(candidateRepository, times(1)).save(any(Candidate.class));

    }

    @Test
    void createCandidate_whenIsVisibleFalse_shouldNotBeVisible() {

        ReflectionTestUtils.setField(candidateService, "visibleByDefault", false);

        Candidate inputCandidate = new Candidate("Name 1","Email 1","Field Of Study 1","Registered At 1");
        inputCandidate.setVisible(false);

        when(candidateRepository.save(any(Candidate.class))).thenReturn(inputCandidate);

        Candidate result = candidateService.createCandidate(inputCandidate);

        assertFalse(result.isVisible());
        }
}