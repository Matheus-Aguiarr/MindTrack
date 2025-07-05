package com.api.mindtrack.domain.studygoal.repository;

import com.api.mindtrack.domain.studygoal.GoalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<GoalModel, Long> {
    List<GoalModel> findAllBySubjectId(Long subjectId);
}
