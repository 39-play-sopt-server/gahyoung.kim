package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private static final String REFRESH_TOKEN_KEY_PREFIX = "auth:refresh-token:";

	private final StringRedisTemplate stringRedisTemplate;

	@Value("${jwt.refresh-token-expiration}")
	private long refreshTokenExpiration;

	public void save(Long memberId, String refreshToken) {
		stringRedisTemplate.opsForValue()
				.set(
						createRefreshTokenKey(memberId),
						refreshToken,
						Duration.ofMillis(refreshTokenExpiration)
				);
	}

	public boolean matches(Long memberId, String refreshToken) {
		String savedRefreshToken = stringRedisTemplate.opsForValue()
				.get(createRefreshTokenKey(memberId));

		return refreshToken != null && refreshToken.equals(savedRefreshToken);
	}

	public void delete(Long memberId) {
		stringRedisTemplate.delete(createRefreshTokenKey(memberId));
	}

	private String createRefreshTokenKey(Long memberId) {
		return REFRESH_TOKEN_KEY_PREFIX + memberId;
	}
	private final RefreshTokenService refreshTokenService;
}