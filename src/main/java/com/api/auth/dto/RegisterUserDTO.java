package com.api.auth.dto;

import com.api.auth.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank String login, @NotBlank String password, UserRole role) {
}
