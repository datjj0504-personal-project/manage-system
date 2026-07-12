package dev.datnt.taskmanagement.security;

import dev.datnt.taskmanagement.exception.AuthenticationFailureException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

private static final String CLAIM_TOKEN_TYPE = "tokenType";
private static final String ACCESS_TOKEN_TYPE = "access";
private static final String REFRESH_TOKEN_TYPE = "refresh";

private final JwtProperties jwtProperties;
private final SecretKey signingKey;

public JwtTokenProvider(JwtProperties jwtProperties) {
	this.jwtProperties = jwtProperties;
	this.signingKey = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
}

public String generateAccessToken(String subject) {
	return generateToken(subject, ACCESS_TOKEN_TYPE, jwtProperties.accessTokenExpirationSeconds());
}

public String generateRefreshToken(String subject) {
	return generateToken(subject, REFRESH_TOKEN_TYPE, jwtProperties.refreshTokenExpirationSeconds());
}

public long getAccessTokenExpirationSeconds() {
	return jwtProperties.accessTokenExpirationSeconds();
}

public String extractSubject(String token) {
	return parseClaims(token).getSubject();
}

public boolean isRefreshToken(String token) {
	return REFRESH_TOKEN_TYPE.equals(parseClaims(token).get(CLAIM_TOKEN_TYPE, String.class));
}

public boolean validateToken(String token) {
	try {
		parseClaims(token);
		return true;
	} catch (Exception exception) {
		return false;
	}
}

private String generateToken(String subject, String tokenType, long expirationSeconds) {
	Instant now = Instant.now();
	Instant expiresAt = now.plusSeconds(expirationSeconds);

	return Jwts.builder()
			.subject(subject)
			.claim(CLAIM_TOKEN_TYPE, tokenType)
			.issuedAt(Date.from(now))
			.expiration(Date.from(expiresAt))
			.signWith(signingKey)
			.compact();
}

private Claims parseClaims(String token) {
	try {
		return Jwts.parser()
				.verifyWith(signingKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	} catch (Exception exception) {
		throw new AuthenticationFailureException("Invalid or expired token");
	}
}
}
