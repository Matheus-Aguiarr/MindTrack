package com.api.mindtrack.controller;

import com.api.mindtrack.domain.note.dto.NoteRequestDTO;
import com.api.mindtrack.domain.note.dto.NoteResponseDTO;
import com.api.mindtrack.domain.note.repository.NoteRepository;
import com.api.mindtrack.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO data) {
        return ResponseEntity.ok(noteService.createNote(data));
    }
}
