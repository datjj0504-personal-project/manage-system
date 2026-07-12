package dev.datnt.taskmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

@Column(nullable = false, unique = true, length = 255)
private String email;

@Column(name = "password_hash", nullable = false, length = 255)
private String passwordHash;

@Column(name = "full_name", nullable = false, length = 100)
private String fullName;

@Column(name = "avatar_url", length = 500)
private String avatarUrl;

@Column(name = "created_at", nullable = false, updatable = false)
private Instant createdAt;

@Column(name = "updated_at", nullable = false)
private Instant updatedAt;

@Column(name = "deleted_at")
private Instant deletedAt;

@PrePersist
void prePersist() {
	Instant now = Instant.now();
	this.createdAt = now;
	this.updatedAt = now;
}

@PreUpdate
void preUpdate() {
	this.updatedAt = Instant.now();
}

public UUID getId() {
	return id;
}

public void setId(UUID id) {
	this.id = id;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPasswordHash() {
	return passwordHash;
}

public void setPasswordHash(String passwordHash) {
	this.passwordHash = passwordHash;
}

public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}

public String getAvatarUrl() {
	return avatarUrl;
}

public void setAvatarUrl(String avatarUrl) {
	this.avatarUrl = avatarUrl;
}

public Instant getCreatedAt() {
	return createdAt;
}

public void setCreatedAt(Instant createdAt) {
	this.createdAt = createdAt;
}

public Instant getUpdatedAt() {
	return updatedAt;
}

public void setUpdatedAt(Instant updatedAt) {
	this.updatedAt = updatedAt;
}

public Instant getDeletedAt() {
	return deletedAt;
}

public void setDeletedAt(Instant deletedAt) {
	this.deletedAt = deletedAt;
}
}
