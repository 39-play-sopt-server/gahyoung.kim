package org.sopt.global.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

	private final SecretKey secretKey;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;
	private final JwtTokenParser tokenParser;

	public JwtProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.access-token-expiration}") long accessTokenExpiration,
			@Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
	) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
		this.tokenParser = new JwtTokenParser(this.secretKey);
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}
	public String createAccessToken(Long memberId) {
		return createToken(memberId, "ACCESS", accessTokenExpiration);
	}

	public String createRefreshToken(Long memberId) {
		return createToken(memberId, "REFRESH", refreshTokenExpiration);
	}

	private String createToken(Long memberId, String tokenType, long expirationMillis) {
		Date now = new Date();
		JwtBuilder builder = Jwts.builder()
				.subject(String.valueOf(memberId))
				.claim("tokenType", tokenType)
				.issuedAt(now)
				.expiration(new Date(now.getTime() + expirationMillis));

		if ("REFRESH".equals(tokenType)) {
			builder.id(UUID.randomUUID().toString());
		}

		return builder.signWith(secretKey).compact();
	}
	public Long getMemberId(String token) {
		return tokenParser.getMemberId(token, "ACCESS");
	}

	public Long getMemberIdFromRefreshToken(String token) {
		return tokenParser.getMemberId(token, "REFRESH");
	}

}