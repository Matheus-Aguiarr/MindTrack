package com.api.mindtrack.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthDTO(@NotBlank String login, @NotBlank String password) {
}
