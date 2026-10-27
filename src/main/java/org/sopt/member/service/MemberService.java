package org.sopt.member.service;

import jakarta.transaction.Transactional;
import org.sopt.global.exception.ErrorType;
import org.sopt.global.exception.PostException;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;

	public MemberService(PostRepository postRepository, MemberRepository memberRepository) {
		this.postRepository = postRepository;
		this.memberRepository = memberRepository;
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

		if (postRepository.existsByMember_Id(memberId)) {
			throw new PostException(ErrorType.WITH_POST_MEMBER);
		}

		member.onDelete();
	}
}
