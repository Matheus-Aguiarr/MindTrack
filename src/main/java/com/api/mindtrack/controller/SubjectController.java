package com.api.mindtrack.controller;

import com.api.mindtrack.domain.subject.dto.SubjectEditDTO;
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
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody SubjectRequestDTO data) {
        return ResponseEntity.ok(subjectService.createSubject(data));
    }

    @GetMapping("/user")
    public ResponseEntity<List<SubjectResponseDTO>> getSubjectsByUserId() {
        return ResponseEntity.ok(subjectService.getSubjectsByUserId());
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> getSubjectBySubjectId(@PathVariable Long subjectId) {
        return ResponseEntity.ok(subjectService.getSubjectBySubjectId(subjectId));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectResponseDTO> editSubjectById(@PathVariable Long subjectId, @RequestBody SubjectEditDTO newData) {
        return ResponseEntity.ok(subjectService.editSubjectById(subjectId, newData));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity deleteSubjectById(@PathVariable Long subjectId) {
        subjectService.deleteSubjectById(subjectId);
        return ResponseEntity.noContent().build();
    }


}
