package com.api.mindtrack.domain.studygoal.repository;

import com.api.mindtrack.domain.studygoal.GoalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<GoalModel, Long> {
}
