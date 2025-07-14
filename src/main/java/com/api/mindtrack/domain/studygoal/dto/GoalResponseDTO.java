package com.api.mindtrack.domain.studygoal.dto;

import com.api.mindtrack.domain.studygoal.GoalModel;

import java.time.LocalDate;

public record GoalResponseDTO(Long id, String title, String description, LocalDate deadline, Boolean done, Long subject_id) {
    public GoalResponseDTO(GoalModel goalModel) {
        this(
                goalModel.getId(),
                goalModel.getTitle(),
                goalModel.getDescription(),
                goalModel.getDeadline(),
                goalModel.getDone(),
                goalModel.getSubject().getId()
        );
    }
}
