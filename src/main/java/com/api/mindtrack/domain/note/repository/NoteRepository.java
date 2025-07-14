package com.api.mindtrack.domain.note.repository;

import com.api.mindtrack.domain.note.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<NoteModel, Long> {
    @Query("SELECT n FROM NoteModel n WHERE n.subject.id = :subjectId ORDER BY n.created_at")
    List<NoteModel> findAllBySubjectId(@Param("subjectId") Long subjectId);
}
