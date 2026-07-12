package dev.datnt.taskmanagement.controller;

import dev.datnt.taskmanagement.common.ApiResponse;
import dev.datnt.taskmanagement.dto.user.ChangePasswordRequest;
import dev.datnt.taskmanagement.dto.user.UpdateMyProfileRequest;
import dev.datnt.taskmanagement.dto.user.UserProfileResponse;
import dev.datnt.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

private final UserService userService;

public UserController(UserService userService) {
	this.userService = userService;
}

@GetMapping("/me")
public ResponseEntity<ApiResponse<UserProfileResponse>> getMyProfile() {
	UserProfileResponse profile = userService.getMyProfile();
	return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", profile));
}

@PatchMapping("/me")
public ResponseEntity<ApiResponse<UserProfileResponse>> updateMyProfile(
		@Valid @RequestBody UpdateMyProfileRequest request
) {
	UserProfileResponse profile = userService.updateMyProfile(request);
	return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", profile));
}

@PutMapping("/me/password")
public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
	userService.changePassword(request);
	return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
}
}
