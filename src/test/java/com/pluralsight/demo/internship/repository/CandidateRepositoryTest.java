package com.pluralsight.demo.internship.repository;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import javax.swing.text.html.Option;

import java.util.List;
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
        assertNotNull(found);
        assertEquals("Blaine Anthony Bell", found.getName());
    }

    @Test
    void findById_shouldReturnCandidateById(){
        Candidate candidate = new Candidate(
                "Blaine Anthony Bell",
                "blaine@email.com",
                "Software Development",
                DateUtils.currentDateAndTime());

        Candidate saved = entityManager.persistAndFlush(candidate);

        Optional<Candidate> found = candidateRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Software Development", found.get().getFieldOfStudy());
    }

    @Test
    void findAll_shouldReturnAllCandidates(){

        entityManager.persist(new Candidate("Candidate 1", "Email 1", "Field Of Study 1", "Date 1"));
        entityManager.persist(new Candidate("Candidate 2", "Email 2", "Field Of Study 2", "Date 2"));
        entityManager.persist(new Candidate("Candidate 3", "Email 3", "Field Of Study 3", "Date 3"));

        List<Candidate> allCandidates = candidateRepository.findAll();

        assertEquals(3, allCandidates.size());
    }

    @Test
    void delete_shouldRemoveCandidate(){
        Candidate candidate = new Candidate("Candidate 1", "Email 1", "Field Of Study 1", "Date 1");
        Candidate saved = entityManager.persistAndFlush(candidate);
        Long id = saved.getId();

        candidateRepository.deleteById(id);
        entityManager.flush();

        Optional<Candidate> found = candidateRepository.findById(id);
        assertFalse(found.isPresent());
    }
}