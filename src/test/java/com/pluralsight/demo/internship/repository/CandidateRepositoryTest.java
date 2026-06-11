package com.pluralsight.demo.internship.repository;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_ShouldPersistCandidate(){
        Candidate candidate = new Candidate(
                "Blaine Anthony Bell",
                "blaine@email.com",
                "Software Development",
                DateUtils.currentDateAndTime());
        candidate.setVisible(true);

        Candidate saved = candidateRepository.save(candidate);

        assertNotNull(saved.getId());
        assertEquals("Blaine Anthony Bell",saved.getName());

        Candidate found = entityManager.find(Candidate.class, saved.getId());
        assertNotNull();
    }

    @Test
    void findById_shouldReturnCandidate(){

    }

    @Test
    void findAll_shouldReturnAllCandidates(){

    }

    @Test
    void delete_shouldRemoveCandidate(){

    }

}