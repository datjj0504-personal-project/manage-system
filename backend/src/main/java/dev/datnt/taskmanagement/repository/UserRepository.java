package dev.datnt.taskmanagement.repository;

import dev.datnt.taskmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

Optional<UserEntity> findByEmail(String email);

boolean existsByEmail(String email);
}
