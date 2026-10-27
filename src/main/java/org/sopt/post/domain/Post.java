package org.sopt.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.sopt.global.domain.BaseEntity;
import org.sopt.member.domain.Member;

@Entity
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;
	@Column(nullable = false)
	private PostCategory category;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;
	private long viewCount = 0L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	public Post(PostCategory category, String title, String content, Member member) {
		this.category = category;
		this.title = title;
		this.content = content;
		this.member = member;
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

	public Member getMember() {
		return member;
	}

	// 동시성 제어를 위한 복사
	public Post copy() {
		Post copiedPost = new Post(category, title, content, member);
		copiedPost.id = id;
		copiedPost.viewCount = viewCount;
		return copiedPost;
	}
}
