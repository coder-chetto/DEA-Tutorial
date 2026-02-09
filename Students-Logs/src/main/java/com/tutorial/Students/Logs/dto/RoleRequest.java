package com.tutorial.Students.Logs.dto;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String name) {
}
