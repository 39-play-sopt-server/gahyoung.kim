package org.sopt.post.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(
		@NotBlank String category,
		@NotBlank String title,
		@NotBlank String content
) {}