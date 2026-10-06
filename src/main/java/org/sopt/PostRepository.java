package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {
	private final List<Post> posts = new ArrayList<>();

	// 저장
	public void save(Post post) {
		posts.add(post);
	}

	// 전체 조회
	public List<Post> findAll() {
		return posts;
	}

	// 하나 조회
	// get(index) = 번호를 알고 있을 때 → 객체를 가져오기
	// indexOf(object) = 객체를 알고 있을 때 → 그 객체의 번호를 찾기
	public Post findIndex(int index) {
		return posts.get(index);
	}

	public void deleteIndex(int index) {
		posts.remove(index);
	}
}
