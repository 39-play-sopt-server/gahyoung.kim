package org.sopt.auth.service;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.domain.OAuthProvider;
import org.sopt.auth.domain.SocialAccount;
import org.sopt.auth.repository.SocialAccountRepository;
import org.sopt.global.security.oauth.SocialMemberMapper.Profile;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialMemberService {

	private final SocialAccountRepository socialAccountRepository;
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member findOrCreate(OAuthProvider provider, Profile profile) {
		return socialAccountRepository
				.findByProviderAndProviderSubject(provider, profile.subject())
				.map(account -> {
					Member member = account.getMember();
					if (member.isDeleted()) {
						throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
					}
					return member;
				})
				.orElseGet(() -> createMember(provider, profile));
	}

	private Member createMember(OAuthProvider provider, Profile profile) {
		if (memberRepository.existsByEmail(profile.email())) {
			throw new ResponseStatusException(
					HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
		}

		String unusedPassword = passwordEncoder.encode(UUID.randomUUID().toString());
		Member member = memberRepository.save(
				new Member(profile.name(), profile.email(), unusedPassword));

		socialAccountRepository.save(new SocialAccount(provider, profile.subject(), member));
		return member;
	}
}