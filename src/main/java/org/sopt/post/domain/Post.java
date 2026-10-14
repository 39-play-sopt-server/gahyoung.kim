package org.sopt.post.domain;

import java.util.concurrent.atomic.AtomicLong;

public class Post {
	private Long id;
	private PostCategory category;
	private String title;
	private String content;

	// AtomicLong은 동시성을  잡아주는 정수타입
	private final AtomicLong viewCount = new AtomicLong(0L);

	public Post(PostCategory category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}

	public String getTitle(){
		return title;
	}

	public String getContent() {
		return content;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public PostCategory getCategory() {
		return category;
	}

	public void assignId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	// AtomicLong으로 선언시 .get(); 사용 = > 객체 자체를 반환하면 외부에서 조회수를 직접 변경할 수 있음

	public long getViewCount() {
		return viewCount.get();
	}
	
	public void increaseViewCount() {
		viewCount.incrementAndGet();
	}

	// 동시성 제어를 위한 복사
	public Post copy() {
		Post copiedPost = new Post(category, title, content);
		copiedPost.id = id;
		copiedPost.viewCount.set(viewCount.get());
		return copiedPost;
	}
}