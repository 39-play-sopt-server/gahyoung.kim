package org.sopt.auth.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AccessTokenBlacklist {

	private static final String KEY_PREFIX = "auth:blacklist:";

	private final StringRedisTemplate redisTemplate;

	public void add(String tokenId, Instant expiresAt) {
		Duration ttl = Duration.between(Instant.now(), expiresAt);

		if (ttl.isZero() || ttl.isNegative()) {
			return;
		}

		redisTemplate.opsForValue().set(KEY_PREFIX + tokenId, "1", ttl);
	}

	public boolean contains(String tokenId) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(KEY_PREFIX + tokenId));
	}
}