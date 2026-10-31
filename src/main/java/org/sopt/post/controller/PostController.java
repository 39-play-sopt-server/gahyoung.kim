package org.sopt.post.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.sopt.global.request.BasePageRequest;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.PageResponse;
import org.sopt.post.domain.PostCategory;
import org.sopt.post.dto.request.CreatePostRequest;
import org.sopt.post.dto.request.UpdatePostRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
public class PostController implements PostApi {

	private final PostService service;

	public PostController(PostService service) {
		this.service = service;
	}

	// 게시글 작성
	@Override
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createPost(
			@Valid @RequestBody CreatePostRequest request
	) {
		service.createPost(
				request.category(),
				request.title(),
				request.content(),
				request.memberId()
		);

		return ApiResponse.success(
				HttpStatus.CREATED,"게시글이 작성되었습니다.",null
		);
	}

	// 게시글 목록 조회
	@Override
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> showPostList(
			@Valid @ModelAttribute BasePageRequest request,
			@RequestParam(name = "category", required = false) PostCategory category
	) {
		return ApiResponse.success(
				HttpStatus.OK, "게시글 목록 조회에 성공했습니다.",
				service.getAllPosts(request,category)
		);
	}


	// 게시글 상세 조회
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PostResponse>> showPost(@PathVariable Long id, HttpSession session) {
		return ApiResponse.success(
				HttpStatus.OK, "게시글 조회에 성공했습니다.", PostResponse.from(service.getPost(id, session))
		);
	}

	// 게시글 수정
	@Override
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> updatePost(
			@PathVariable Long id, @Valid @RequestBody UpdatePostRequest request
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
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
		service.deletePost(id);

		return ApiResponse.success(
				HttpStatus.OK, "게시글이 삭제되었습니다.",null
		);
	}

}
