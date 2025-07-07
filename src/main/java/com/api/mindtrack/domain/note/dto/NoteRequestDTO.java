package com.api.mindtrack.domain.note.dto;

public record NoteRequestDTO(String title, String content, Long subjectId) {
}
