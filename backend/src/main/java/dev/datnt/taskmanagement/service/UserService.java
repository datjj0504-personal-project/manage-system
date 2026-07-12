package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.user.ChangePasswordRequest;
import dev.datnt.taskmanagement.dto.user.UpdateMyProfileRequest;
import dev.datnt.taskmanagement.dto.user.UserProfileResponse;

public interface UserService {

UserProfileResponse getMyProfile();

UserProfileResponse updateMyProfile(UpdateMyProfileRequest request);

void changePassword(ChangePasswordRequest request);
}
