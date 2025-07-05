package com.api.mindtrack.domain.studygoal.dto;

import java.time.LocalDate;

public record GoalRequestDTO(String title, String description, LocalDate deadline, Long subject_id) {
}
