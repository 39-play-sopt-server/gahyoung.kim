package org.sopt.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private PostCategory category;
	private String title;
	private String content;

	private long viewCount = 0L;
	public Post(PostCategory category, String title, String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}

	protected  Post() {

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

	public long getViewCount() {
		return viewCount;
	}

	public void increaseViewCount() {
		viewCount++;
	}

	// 동시성 제어를 위한 복사
	public Post copy() {
		Post copiedPost = new Post(category, title, content);
		copiedPost.id = id;
		copiedPost.viewCount = viewCount;
		return copiedPost;
	}
}