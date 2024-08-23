package com.springboot.locadora.users.mappers;

import com.springboot.locadora.users.DTOs.UserRecordDto;
import com.springboot.locadora.users.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public List<UserRecordDto> toUserResponseList(List<UserEntity> userList){
        return userList.stream().map(this::toUserResponse).toList();
    }

    public UserRecordDto toUserResponse(UserEntity model){
        return UserRecordDto
                .builder()
                .id(model.getId())
                .name(model.getName())
                .email(model.getEmail())
                .role(model.getRole())
                .build();
    }
}