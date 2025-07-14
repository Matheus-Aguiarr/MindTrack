package com.api.mindtrack.domain.studygoal.repository;

import com.api.mindtrack.domain.studygoal.GoalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoalRepository extends JpaRepository<GoalModel, Long> {
    @Query("SELECT g FROM GoalModel g WHERE g.subject.id = :subjectId ORDER BY g.done ASC")
    List<GoalModel> findAllBySubjectId(@Param("subjectId") Long subjectId);
}
