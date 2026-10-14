package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostCategory;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {
	//synchronized 로 동시성제어

	private final Map<Long, Post> posts = new HashMap<>();
	private Long sequence = 1L; // 게시글 ID 자동 생성을 위한 값

	public synchronized void save(Post post) {
		post.assignId(sequence);
		posts.put(sequence, post);
		sequence++;
	}

	public synchronized List<Post> findAll() {
		return posts.values().stream()
				.map(Post::copy)
				.sorted(Comparator.comparing(Post::getId).reversed())
				.toList();
	}
	// 페이지네이션
	public synchronized List<Post> findPage(int page, int size, PostCategory category) {
		long offset = (page - 1L) * size;

		return findAll().stream()
				.filter(post -> category == null || post.getCategory() == category)
				.skip(offset)
				.limit(size)
				.toList();
	}

	public synchronized Post findById(Long id) {
		return posts.get(id);
	}

	public synchronized boolean existsById(Long id) {
		return posts.containsKey(id);
	}

	public synchronized void deleteById(Long id) {
		posts.remove(id);
	}
}