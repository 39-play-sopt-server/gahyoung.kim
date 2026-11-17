package org.sopt.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.sopt.global.security.JwtProvider;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;


	@Transactional
	public Long signup( String email, String password) {
		if (memberRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("이미 가입된 이메일입니다.");
		}

		Member member = new Member(email, passwordEncoder.encode(password));
		return memberRepository.save(member).getId();
	}

	@Transactional(readOnly = true)
	public String login(String email, String password, HttpServletResponse response) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
		}

		String refreshToken = jwtProvider.createRefreshToken(member.getId());
		refreshTokenService.save(member.getId(), refreshToken);

		return jwtProvider.createAccessToken(member.getId());
	}
}