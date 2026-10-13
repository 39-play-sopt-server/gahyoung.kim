package org.sopt.service;

import org.sopt.domain.PostCategory;
import org.sopt.domain.Post;
import org.sopt.exception.PostException;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.sopt.exception.ErrorType;


import java.util.List;

@Service
public class PostService {
	private final PostRepository repository ;

	public PostService(PostRepository repository) {
		this.repository = repository;
	}

	// 무한 새로고침 방지를 위한 10분 내에 조회수 연속으로 못올리게 방지
	// private static final long VIEW_INTERVAL = 10 * 60 * 1000L;

	public void createPost(String category, String title, String content) {

	 // if (category.isBlank() || category == null )
		// 이렇게 쓰면 null이 isBlank()를 호출하는 오류를 막을 수 없음
		if (category == null || category.isBlank() ) {
			throw new PostException(ErrorType.INVALID_CATEGORY);
		}

		if (title == null || title.isBlank()  ) {
			throw new PostException(ErrorType.INVALID_TITLE);
		}
		if (content == null || content.isBlank()) {
			throw new PostException(ErrorType.INVALID_CONTENT);
		}

		PostCategory postCategory;

		try {
			postCategory = PostCategory.valueOf(category.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new PostException(ErrorType.INVALID_CATEGORY);
		}
		Post post = new Post(postCategory, title, content);
		repository.save(post);
	}

	public List<Post> getAllPosts(int page, int size, PostCategory category) {
		if (page < 1 || size < 1) {
			throw new PostException(ErrorType.INVALID_REQUEST);
		}

		return repository.findPage(page, size, category );
	}

	public Post getPost(Long id) {
		synchronized (repository) {
			validatePostId(id);

			Post post = repository.findById(id);
			post.increaseViewCount();

			return post.copy();
		}
	}


	public void updatePost(Long id, String newTitle, String newContent) {
		synchronized (repository) {
			validatePostId(id);


			if (newTitle == null || newTitle.isBlank()) {
				throw new PostException(ErrorType.INVALID_TITLE);
			}

			if (newContent == null || newContent.isBlank()) {
				throw new PostException(ErrorType.INVALID_CONTENT);
			}

			Post post = repository.findById(id);
			post.update(newTitle, newContent);
		}
	}


	public void deletePost(Long id) {
		synchronized (repository) {
			validatePostId(id);
			repository.deleteById(id);
		}
	}

	private void validatePostId(Long id) {
		if (!repository.existsById(id)) {
			throw new PostException(ErrorType.POST_NOT_FOUND);
		}
	}

}
