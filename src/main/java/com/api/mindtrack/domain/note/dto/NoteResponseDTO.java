package com.api.mindtrack.domain.note.dto;

import com.api.mindtrack.domain.note.NoteModel;

import java.time.LocalDate;

public record NoteResponseDTO(Long id, String title, String content, LocalDate created_at, Long subjectId) {
    public NoteResponseDTO(NoteModel note) {
        this(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreated_at(),
                note.getSubject().getId()
        );
    }
}
