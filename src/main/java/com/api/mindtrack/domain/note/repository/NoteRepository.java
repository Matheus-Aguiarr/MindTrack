package com.api.mindtrack.domain.note.repository;

import com.api.mindtrack.domain.note.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteModel, Long> {
}
