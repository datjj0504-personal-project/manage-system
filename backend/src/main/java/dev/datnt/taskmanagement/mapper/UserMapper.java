package dev.datnt.taskmanagement.mapper;

import dev.datnt.taskmanagement.dto.auth.RegisterResponse;
import dev.datnt.taskmanagement.dto.user.UserProfileResponse;
import dev.datnt.taskmanagement.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

RegisterResponse toRegisterResponse(UserEntity user);

UserProfileResponse toUserProfileResponse(UserEntity user);
}
