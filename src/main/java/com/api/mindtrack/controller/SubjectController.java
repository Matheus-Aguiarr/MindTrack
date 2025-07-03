package com.api.mindtrack.controller;

import com.api.mindtrack.domain.subject.dto.SubjectRequestDTO;
import com.api.mindtrack.domain.subject.dto.SubjectResponseDTO;
import com.api.mindtrack.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity createSubject(@RequestBody SubjectRequestDTO data) {
        subjectService.createSubject(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(subjectService.getSubjectsByUserId(userId));
    }

}
