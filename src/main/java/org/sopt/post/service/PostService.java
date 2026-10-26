package org.sopt.post.service;

import jakarta.servlet.http.HttpSession;
import org.sopt.global.exception.ErrorType;
import org.sopt.global.exception.PostException;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.MemberRepository;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostCategory;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
	private final PostRepository repository ;
	private final MemberRepository memberRepository;

	public PostService(PostRepository repository, MemberRepository memberRepository) {
		this.repository = repository;
		this.memberRepository = memberRepository;
	}

	// 무한 새로고침 방지를 위한 10분 내에 조회수 연속으로 못올리게 방지
	private static final long VIEW_INTERVAL = 10 * 60 * 1000L;

	public void createPost(String category, String title, String content,Long memberId) {

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

		Member member = memberRepository.findById(memberId)
				.orElseThrow(() ->  new PostException(ErrorType.MEMBER_NOT_FOUND));

		Post post = new Post(postCategory, title, content, member);
		repository.save(post);
	}

	public List<Post> getAllPosts(int page, int size, PostCategory category) {
		if (page < 1 || size < 1) {
			throw new PostException(ErrorType.INVALID_REQUEST);
		}

		return repository.findAll();
	}

	@Transactional
	public Post getPost(Long id, HttpSession session) {
		Post post = repository.findById(id)
				.orElseThrow(() ->
						new PostException(ErrorType.POST_NOT_FOUND)
				);

		String view = "viewedAt:" + id;
		Long lastViewedAt = (Long) session.getAttribute(view);
		long now = System.currentTimeMillis();

		if (lastViewedAt == null || now - lastViewedAt >= VIEW_INTERVAL) {
			post.increaseViewCount();
			session.setAttribute(view, now);
		}

		return post;
	}

	@Transactional
	public void updatePost(Long id, String newTitle, String newContent) {
		if (newTitle == null || newTitle.isBlank()) {
			throw new PostException(ErrorType.INVALID_TITLE);
		}

		if (newContent == null || newContent.isBlank()) {
			throw new PostException(ErrorType.INVALID_CONTENT);
		}

		Post post = repository.findById(id)
				.orElseThrow(() ->
						new PostException(ErrorType.POST_NOT_FOUND)
				);

		post.update(newTitle, newContent);
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
