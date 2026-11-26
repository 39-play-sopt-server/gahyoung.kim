package org.sopt.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

final class JwtTokenParser {
	private final JwtParser parser;

	JwtTokenParser(SecretKey secretKey) {
		this.parser = Jwts.parser().verifyWith(secretKey).build();
	}

	Long getMemberId(String token, String expectedType) {
		return Long.valueOf(parseClaims(token, expectedType).getSubject());
	}

	Claims parseAccessToken(String token) {
		Claims claims = parseClaims(token, "ACCESS");
		if (claims.getId() == null || claims.getId().isBlank()) {
			throw new IllegalArgumentException("Access Token에 jti가 없습니다.");
		}
		return claims;
	}

	private Claims parseClaims(String token, String expectedType) {
		Claims claims = parser.parseSignedClaims(token).getPayload();

		if (!expectedType.equals(claims.get("tokenType", String.class))) {
			throw new IllegalArgumentException("올바르지 않은 토큰 종류입니다.");
		}

		return claims;
	}
}