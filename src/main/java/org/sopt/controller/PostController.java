package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.global.response.ApiResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {

	private final PostService service;

	public PostController(PostService service) {
		this.service = service;
	}

	// 게시글 작성
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createPost(
			@RequestBody CreatePostRequest request
	) {
		service.createPost(
				request.category(),
				request.title(),
				request.content()
		);

		return ApiResponse.success(
				HttpStatus.CREATED,"게시글이 작성되었습니다.",null
		);
	}

	// 게시글 목록 조회
	@GetMapping
	public ResponseEntity<ApiResponse<List<Post>>> showPostList() {
		return ApiResponse.success(
				HttpStatus.OK, "게시글 목록 조회에 성공했습니다.",service.getAllPosts()
		);
	}

	// 게시글 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Post>> showPost(@PathVariable Long id) {
		return ApiResponse.success(
				HttpStatus.OK, "게시글 조회에 성공했습니다.", service.getPost(id)
		);
	}

	// 게시글 수정
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> updatePost(
			@PathVariable Long id, @RequestBody UpdatePostRequest request
	) {
		service.updatePost(
				id,
				request.title(),
				request.content()
		);

		return ApiResponse.success(
				HttpStatus.OK, "게시글이 수정되었습니다.",null
		);
	}

	// 게시글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
		service.deletePost(id);

		return ApiResponse.success(
				HttpStatus.OK, "게시글이 삭제되었습니다.",null
		);
	}

	public record CreatePostRequest(
			String category,
			String title,
			String content
	) {}

	public record UpdatePostRequest(
			String title,
			String content
	) {}
}