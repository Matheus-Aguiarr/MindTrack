package com.api.mindtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthDTO(@NotBlank String login, @NotBlank String password) {
}
