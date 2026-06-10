package com.pluralsight.demo.internship.controller;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.service.CandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates(
            @RequestParam(required = false) String fieldOfStudy) {
        List<Candidate> candidates;
        if(fieldOfStudy != null){
            candidates = candidateService.getCandidatesByFieldOfStudy(fieldOfStudy);
        } else {
            candidates = candidateService.getAllCandidates();
        }
        return ResponseEntity.ok(candidates);
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        // Same flaw: returns 200 instead of 201
        Candidate created = candidateService.createCandidate(candidate);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable Long id,
            @RequestBody Candidate candidate) {
        Candidate updated = candidateService.updateCandidate(id, candidate);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidatesById(id);
        return ResponseEntity.ok(candidate);
    }


    @GetMapping("/search/name/{name}")
    public List<Candidate> searchByCandidateName (@PathVariable String name) {
        return candidateService.getCandidatesByName(name);
    }

    @GetMapping("/search/email/{email}")
    public List<Candidate> searchByCandidateEmail(@PathVariable String email){
        return candidateService.getCandidatesByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
