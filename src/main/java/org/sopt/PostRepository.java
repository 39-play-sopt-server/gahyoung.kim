package org.sopt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {

	private final Map<Long, Post> posts = new HashMap<>();
	private Long sequence = 1L; // 게시글 ID 자동 생성을 위한 값

	public void save(Post post) {
		post.assignId(sequence);
		posts.put(sequence, post);
		sequence++;
	}

	public List<Post> findAll() {
		return new ArrayList<>(posts.values());
	}

	public Post findById(Long id) {
		return posts.get(id);
	}

	public boolean existsById(Long id) {
		return posts.containsKey(id);
	}

	public void deleteById(Long id) {
		posts.remove(id);
	}
}