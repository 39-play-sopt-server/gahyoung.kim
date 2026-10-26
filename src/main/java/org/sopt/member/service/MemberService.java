package org.sopt.member.service;

import jakarta.transaction.Transactional;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public Long createMember(String name){
		Member member = new Member(name);
		Member members = memberRepository.save(member);

		return members.getId();
	}
}
