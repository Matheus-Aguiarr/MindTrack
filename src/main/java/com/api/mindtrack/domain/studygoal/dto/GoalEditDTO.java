package com.api.mindtrack.domain.studygoal.dto;

import java.time.LocalDate;

public record GoalEditDTO(String title, String description, LocalDate deadline) {
}
