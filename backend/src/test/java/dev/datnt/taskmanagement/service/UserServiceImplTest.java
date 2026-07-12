package dev.datnt.taskmanagement.service;

import dev.datnt.taskmanagement.dto.user.ChangePasswordRequest;
import dev.datnt.taskmanagement.dto.user.UpdateMyProfileRequest;
import dev.datnt.taskmanagement.dto.user.UserProfileResponse;
import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.exception.AuthorizationException;
import dev.datnt.taskmanagement.mapper.UserMapper;
import dev.datnt.taskmanagement.repository.UserRepository;
import dev.datnt.taskmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

@Mock
private UserRepository userRepository;
@Mock
private UserMapper userMapper;
@Mock
private PasswordEncoder passwordEncoder;

@InjectMocks
private UserServiceImpl userService;

private UserEntity user;

@BeforeEach
void setUp() {
	SecurityContextHolder.clearContext();
	SecurityContextHolder.getContext()
			.setAuthentication(new UsernamePasswordAuthenticationToken("john@example.com", "N/A", Collections.emptyList()));

	user = new UserEntity();
	user.setId(UUID.randomUUID());
	user.setEmail("john@example.com");
	user.setPasswordHash("encoded-password");
	user.setFullName("John Doe");
	user.setAvatarUrl("https://example.com/avatar.png");
	user.setCreatedAt(Instant.now());
	user.setUpdatedAt(Instant.now());
}

@Test
void getMyProfile_whenAuthenticated_shouldReturnProfile() {
	UserProfileResponse response = new UserProfileResponse(
			user.getId(), user.getEmail(), user.getFullName(), user.getAvatarUrl(), user.getCreatedAt(), user.getUpdatedAt()
	);
	when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
	when(userMapper.toUserProfileResponse(user)).thenReturn(response);

	UserProfileResponse result = userService.getMyProfile();

	assertThat(result).isEqualTo(response);
}

@Test
void updateMyProfile_whenRequestHasFields_shouldPersistChanges() {
	UpdateMyProfileRequest request = new UpdateMyProfileRequest("John Wick", "https://example.com/new.png");
	UserProfileResponse response = new UserProfileResponse(
			user.getId(), user.getEmail(), "John Wick", "https://example.com/new.png", user.getCreatedAt(), user.getUpdatedAt()
	);

	when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
	when(userRepository.save(any(UserEntity.class))).thenReturn(user);
	when(userMapper.toUserProfileResponse(user)).thenReturn(response);

	UserProfileResponse result = userService.updateMyProfile(request);

	assertThat(result.fullName()).isEqualTo("John Wick");
	assertThat(result.avatarUrl()).isEqualTo("https://example.com/new.png");
	verify(userRepository).save(user);
}

@Test
void changePassword_whenCurrentPasswordInvalid_shouldThrowAuthorizationException() {
	ChangePasswordRequest request = new ChangePasswordRequest("wrong-pass", "newPassword123");
	when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
	when(passwordEncoder.matches("wrong-pass", "encoded-password")).thenReturn(false);

	assertThatThrownBy(() -> userService.changePassword(request))
			.isInstanceOf(AuthorizationException.class)
			.hasMessage("Current password is incorrect");
}
}
