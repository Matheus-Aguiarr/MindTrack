package com.api.mindtrack.domain.studygoal.dto;

import com.api.mindtrack.domain.studygoal.GoalModel;

import java.time.LocalDate;

public record GoalResponseWithoutSubjectDTO(String title, String description, LocalDate deadline, Boolean done) {
    public GoalResponseWithoutSubjectDTO(GoalModel goal) {
        this(goal.getTitle(), goal.getDescription(), goal.getDeadline(), goal.getDone());
    }
}
