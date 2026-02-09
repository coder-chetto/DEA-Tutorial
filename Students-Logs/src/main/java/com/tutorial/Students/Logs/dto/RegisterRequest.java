package com.tutorial.Students.Logs.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        Set<String> roles
) {
}
