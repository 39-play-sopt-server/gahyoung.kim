package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostCategory;

public record PostResponse(
		Long id,
		PostCategory category,
		String title,
		String content,
		long viewCount
) {
	public static PostResponse from(Post post) {
		return new PostResponse(
				post.getId(),
				post.getCategory(),
				post.getTitle(),
				post.getContent(),
				post.getViewCount()
		);
	}
}

