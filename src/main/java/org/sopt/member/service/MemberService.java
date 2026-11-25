package org.sopt.member.service;

import jakarta.transaction.Transactional;
import org.sopt.auth.service.AuthService;
import org.sopt.global.exception.ErrorType;
import org.sopt.global.exception.PostException;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.MemberList;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final AuthService authService;
	public MemberService(MemberRepository memberRepository, AuthService authService) {
		this.memberRepository = memberRepository;
		this.authService = authService;
	}

	@Transactional
	public Long createMember(String name){
		Member member = new Member(name);
		Member members = memberRepository.save(member);

		return members.getId();
	}

	@Transactional
	public void deleteMember(Long memberId , String accessToken) {
		Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
				.orElseThrow(() -> new PostException(ErrorType.MEMBER_NOT_FOUND)
				);
		member.onDelete();
		authService.logout(memberId, accessToken);	}

	public List<MemberList> getMembers() {
		return memberRepository.findAllByDeletedAtIsNull().stream()
				.map(member -> new MemberList(
						member.getId(), member.getName(), member.getRole()))
				.toList();
	}
}
