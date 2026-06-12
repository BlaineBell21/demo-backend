package com.pluralsight.demo.internship.repository;

import com.pluralsight.demo.internship.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Object getCandidatesByFieldOfStudy(String fieldOfStudy);
    // No custom queries yet
}
