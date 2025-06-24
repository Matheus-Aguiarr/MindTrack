package com.api.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAuthDTO(@NotBlank String login, @NotBlank String password) {
}
