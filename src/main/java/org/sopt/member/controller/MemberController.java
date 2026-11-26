package org.sopt.member.controller;

import jakarta.validation.Valid;
import org.sopt.global.response.ApiResponse;
import org.sopt.member.dto.CreateMemberRequest;
import org.sopt.member.dto.MemberList;
import org.sopt.member.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;

	}

	@PostMapping("/member")
	public ResponseEntity<ApiResponse<Long>> createMember(
			@Valid @RequestBody CreateMemberRequest request
			) {
		Long memberId = memberService.createMember(request.name());

		return ApiResponse.success(
				HttpStatus.CREATED, "회원이 생성되었습니다.",memberId
		);
	}

	@DeleteMapping("/member/me")
	public ResponseEntity<ApiResponse<Void>> deleteMember(
			@AuthenticationPrincipal Long memberId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
		String accessToken = authorization.substring("Bearer ".length());
		memberService.deleteMember(memberId, accessToken);

		return ApiResponse.success(HttpStatus.OK,"회원이 탈퇴되었습니다.",null);
	}

	@GetMapping("/member/me")
	public ResponseEntity<Long> me(@AuthenticationPrincipal Long memberId) {
		return ResponseEntity.ok(memberId);
	}
	@GetMapping("/members")
	public ResponseEntity<ApiResponse<List<MemberList>>> getMembers() {
		return ApiResponse.success(
				HttpStatus.OK, "회원 목록을 조회했습니다.", memberService.getMembers());
	}
}
