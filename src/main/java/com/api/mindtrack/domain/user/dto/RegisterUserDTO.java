package com.api.mindtrack.domain.user.dto;

import com.api.mindtrack.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank String login, @NotBlank String password, UserRole role) {
}
