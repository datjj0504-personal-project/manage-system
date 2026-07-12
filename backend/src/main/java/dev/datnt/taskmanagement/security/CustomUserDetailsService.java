package dev.datnt.taskmanagement.security;

import dev.datnt.taskmanagement.entity.UserEntity;
import dev.datnt.taskmanagement.repository.UserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

private final UserRepository userRepository;

public CustomUserDetailsService(UserRepository userRepository) {
	this.userRepository = userRepository;
}

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserEntity user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

	return new User(user.getEmail(), user.getPasswordHash(), AuthorityUtils.NO_AUTHORITIES);
}
}
