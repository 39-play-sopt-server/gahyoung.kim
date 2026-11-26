package org.sopt.global.security.oauth;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GoogleMemberMapper implements SocialMemberMapper {

	@Override
	public boolean supports(String registrationId) {
		return "google".equals(registrationId);
	}

	@Override
	public Profile map(Map<String, Object> attributes) {
		if (!(attributes.get("sub") instanceof String subject) || subject.isBlank()) {
			throw new IllegalArgumentException("Google 사용자 ID가 없습니다.");
		}

		if (!(attributes.get("email") instanceof String email)
				|| email.isBlank()
				|| !Boolean.TRUE.equals(attributes.get("email_verified"))) {
			throw new IllegalArgumentException("검증된 Google 이메일이 필요합니다.");
		}

		String name = attributes.get("name") instanceof String value && !value.isBlank()
				? value
				: "Google 사용자";

		return new Profile(subject, email, name);
	}
}