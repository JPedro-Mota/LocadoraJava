package com.springboot.locadora.users.DTOs;

import com.springboot.locadora.users.enums.UserRoleEnum;
import lombok.Builder;

@Builder
public record UserRecordDto(
        int id,
        String name,
        String email,
        UserRoleEnum role)  {
}
