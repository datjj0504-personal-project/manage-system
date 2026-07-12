package dev.datnt.taskmanagement.service.impl;

import dev.datnt.taskmanagement.dto.user.ChangePasswordRequest;
import dev.datnt.taskmanagement.dto.user.UpdateMyProfileRequest;
import dev.datnt.taskmanagement.dto.user.UserProfileResponse;
import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.exception.AuthorizationException;
import dev.datnt.taskmanagement.exception.ResourceNotFoundException;
import dev.datnt.taskmanagement.mapper.UserMapper;
import dev.datnt.taskmanagement.repository.UserRepository;
import dev.datnt.taskmanagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

private final UserRepository userRepository;
private final UserMapper userMapper;
private final PasswordEncoder passwordEncoder;

public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
	this.userRepository = userRepository;
	this.userMapper = userMapper;
	this.passwordEncoder = passwordEncoder;
}

@Override
@Transactional(readOnly = true)
public UserProfileResponse getMyProfile() {
	return userMapper.toUserProfileResponse(getCurrentUser());
}

@Override
@Transactional
public UserProfileResponse updateMyProfile(UpdateMyProfileRequest request) {
	UserEntity user = getCurrentUser();

	if (request.fullName() != null) {
		user.setFullName(request.fullName().trim());
	}
	if (request.avatarUrl() != null) {
		user.setAvatarUrl(request.avatarUrl().trim());
	}

	UserEntity savedUser = userRepository.save(user);
	return userMapper.toUserProfileResponse(savedUser);
}

@Override
@Transactional
public void changePassword(ChangePasswordRequest request) {
	UserEntity user = getCurrentUser();
	if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
		throw new AuthorizationException("Current password is incorrect");
	}

	user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
	userRepository.save(user);
}

private UserEntity getCurrentUser() {
	String email = getCurrentUserEmail();
	return userRepository.findByEmail(email)
			.orElseThrow(() -> new ResourceNotFoundException("User not found"));
}

private String getCurrentUserEmail() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
		throw new AuthorizationException("Authenticated user not found");
	}
	return authentication.getName().trim().toLowerCase(Locale.ROOT);
}
}
