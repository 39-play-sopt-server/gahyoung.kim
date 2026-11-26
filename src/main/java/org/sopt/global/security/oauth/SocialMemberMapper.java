package org.sopt.global.security.oauth;

import java.util.Map;

public interface SocialMemberMapper {

	boolean supports(String registrationId);

	Profile map(Map<String, Object> attributes);

	record Profile(String subject, String email, String name) {
	}
}