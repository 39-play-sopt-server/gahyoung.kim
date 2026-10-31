package org.sopt.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.sopt.global.request.BasePageRequest;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.PageResponse;
import org.sopt.post.domain.PostCategory;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시글", description = "게시글 API")
public interface PostApi {

	@Operation(
			summary = "게시글 작성",
			description = "카테고리와 제목과 내용을 필수로 작성해주세요."
	)
	ResponseEntity<ApiResponse<Void>> createPost(
			CreatePostRequest request
	);

	@Operation(
			summary = "게시글 목록 조회",
			description = "게시글 목록을 조회한다. 최신순으로 조회되며 페이지로 나뉘어져있다."
	)
	ResponseEntity<ApiResponse<PageResponse<PostResponse>>> showPostList(
			BasePageRequest request,
			PostCategory category
	);

	@Operation(
			summary = "게시글 상세 조회",
			description = "게시글을 조회합니다. 같은 세션에서는 게시글별로 최초 조회 시와 마지막 집계 후 10분이 지난 조회 시 조회수를 1 증가시킵니다."
	)
	ResponseEntity<ApiResponse<PostResponse>> showPost(
			Long id,
			HttpSession session
	);

	@Operation(
			summary = "게시글 수정",
			description = "게시글의 제목과 내용을 수정합니다. 제목과 내용은 모두 필수입니다."
	)
	ResponseEntity<ApiResponse<Void>> updatePost(
			Long id,
			UpdatePostRequest request
	);

	@Operation(
			summary = "게시글 삭제",
			description = "게시글을 삭제합니다."
	)
	ResponseEntity<ApiResponse<Void>> deletePost(Long id);
}
