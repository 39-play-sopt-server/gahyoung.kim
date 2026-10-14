package org.sopt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.sopt.domain.Post;
import org.sopt.domain.PostCategory;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "게시글", description = "게시글 API")
public interface PostApi {

	@Operation(
			summary = "게시글 작성",
			description = "카테고리와 제목과 내용을 필수로 작성해주세요."
	)
	ResponseEntity<ApiResponse<Void>> createPost(
			PostController.CreatePostRequest request
	);

	@Operation(
			summary = "게시글 목록 조회",
			description = "게시글 목록을 조회한다. 최신순으로 조회되며 페이지로 나뉘어져있다."
	)
	ResponseEntity<ApiResponse<List<Post>>> showPostList(
			int page,
			int size,
			PostCategory category
	);

	@Operation(
			summary = "게시글 상세 조회",
			description = "게시글을 조회하고 조회수를 1 증가시킵니다."
	)
	ResponseEntity<ApiResponse<Post>> showPost(
			Long id,
			HttpSession session
	);

	@Operation(
			summary = "게시글 수정",
			description = "게시글의 제목 혹은 내용을 수정할 수 있다."
	)
	ResponseEntity<ApiResponse<Void>> updatePost(
			Long id,
			PostController.UpdatePostRequest request
	);

	@Operation(
			summary = "게시글 삭제",
			description = "게시글을 삭제합니다."
	)
	ResponseEntity<ApiResponse<Void>> deletePost(Long id);
}
