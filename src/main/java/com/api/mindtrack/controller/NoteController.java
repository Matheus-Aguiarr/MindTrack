package com.api.mindtrack.controller;

import com.api.mindtrack.domain.note.dto.NoteEditDTO;
import com.api.mindtrack.domain.note.dto.NoteRequestDTO;
import com.api.mindtrack.domain.note.dto.NoteResponseDTO;
import com.api.mindtrack.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(@RequestBody NoteRequestDTO data) {
        return ResponseEntity.ok(noteService.createNote(data));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<NoteResponseDTO>> getNoteBySubjectId(@PathVariable Long subjectId) {
        return ResponseEntity.ok(noteService.getNoteBySubjectId(subjectId));
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNoteByNoteId(@PathVariable Long noteId) {
        return ResponseEntity.ok(noteService.getNoteByNoteId(noteId));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> editNoteById(@PathVariable Long noteId, @RequestBody NoteEditDTO data)  {
        return ResponseEntity.ok(noteService.editNoteById(noteId, data));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity deleteNoteById(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.noContent().build();
    }
}


