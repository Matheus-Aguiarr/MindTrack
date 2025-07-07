package com.api.mindtrack.domain.note.repository;

import com.api.mindtrack.domain.note.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteModel, Long> {
    List<NoteModel> findAllBySubjectId(Long subjectId);
}
