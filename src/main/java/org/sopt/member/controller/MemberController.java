package org.sopt.member.controller;

import jakarta.validation.Valid;
import org.sopt.global.response.ApiResponse;
import org.sopt.member.dto.CreateMemberRequest;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;

	}

	@PostMapping
	public ResponseEntity<ApiResponse<Long>> createMember(
			@Valid @RequestBody CreateMemberRequest request
			) {
		Long memberId = memberService.createMember(request.name());

		return ApiResponse.success(
				HttpStatus.CREATED, "회원이 생성되었습니다.",memberId
		);
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<ApiResponse<Void>> deleteMember(
			@PathVariable("memberId") Long memberId
	) {
		memberService.deleteMember(memberId);

		return ApiResponse.success(HttpStatus.OK,"회원이 삭제되었습니다.",null);
	}
}
