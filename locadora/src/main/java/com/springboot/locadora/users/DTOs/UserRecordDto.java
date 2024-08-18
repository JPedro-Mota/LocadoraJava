package com.springboot.locadora.users.DTOs;

import com.springboot.locadora.users.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRecordDto(
        @NotBlank String name,
        @Email @NotBlank String email,
        @NotNull UserRoleEnum role,
        @NotBlank String password) {
}
