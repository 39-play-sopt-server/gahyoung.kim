package org.sopt.member.service;

import jakarta.transaction.Transactional;
import org.sopt.auth.service.RefreshTokenService;
import org.sopt.global.exception.ErrorType;
import org.sopt.global.exception.PostException;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final RefreshTokenService refreshTokenService;

	public MemberService(MemberRepository memberRepository, RefreshTokenService refreshTokenService) {
		this.memberRepository = memberRepository;
		this.refreshTokenService = refreshTokenService;
	}

	@Transactional
	public Long createMember(String name){
		Member member = new Member(name);
		Member members = memberRepository.save(member);

		return members.getId();
	}

	@Transactional
	public void deleteMember(Long memberId) {
		Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
				.orElseThrow(() -> new PostException(ErrorType.MEMBER_NOT_FOUND)
				);
		member.onDelete();
		refreshTokenService.delete(memberId);
	}
}
