package org.sopt.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.domain.AccessTokenBlacklist;
import org.sopt.auth.domain.OAuthProvider;
import org.sopt.auth.service.vo.AuthTokens;
import org.sopt.global.security.JwtProvider;
import org.sopt.global.security.oauth.SocialMemberMapper;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final AccessTokenBlacklist accessTokenBlacklist;
	private final List<SocialMemberMapper> socialMemberMappers;
	private final SocialMemberService socialMemberService;


	@Transactional
	public Long signup(String name, String email, String password) {
		if (memberRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		Member member = new Member(name, email, passwordEncoder.encode(password));
		return memberRepository.save(member).getId();
	}

	@Transactional(readOnly = true)
	public AuthTokens login(String email, String password) {
		Member member = memberRepository.findByEmailAndDeletedAtIsNull(email)
				.orElseThrow(() -> new IllegalArgumentException(
						"이메일 또는 비밀번호가 올바르지 않습니다."));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
		}

		return issueTokens(member);
	}
	public AuthTokens socialLogin(String registrationId, Map<String, Object> attributes) {
		SocialMemberMapper mapper = socialMemberMappers.stream()
				.filter(candidate -> candidate.supports(registrationId))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "지원하지 않는 소셜 로그인입니다."));

		OAuthProvider provider = OAuthProvider.valueOf(registrationId.toUpperCase(Locale.ROOT));
		Member member = socialMemberService.findOrCreate(provider, mapper.map(attributes));

		return issueTokens(member);
	}

	private AuthTokens issueTokens(Member member) {
		String accessToken = jwtProvider.createAccessToken(member.getId());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());
		refreshTokenService.save(member.getId(), refreshToken);

		return new AuthTokens(accessToken, refreshToken);
	}
	public void logout(Long memberId, String accessToken) {
		Claims claims = jwtProvider.parseAccessToken(accessToken);
		accessTokenBlacklist.add(claims.getId(), claims.getExpiration().toInstant());
		refreshTokenService.delete(memberId);
	}
	public String reissueTokens(String refreshToken) {
		Long memberId;
		try {
			memberId = jwtProvider.getMemberIdFromRefreshToken(refreshToken);
		} catch (JwtException | IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다.");
		}

		if (!refreshTokenService.matches(memberId, refreshToken)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다.");
		}
		if (memberRepository.findByIdAndDeletedAtIsNull(memberId).isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		return jwtProvider.createAccessToken(memberId);
	}

}
