package org.sopt;

public class Post {

	private String title;
	private String content;

	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	// 게시글 제목 조회
	public String getTitle() {
		return title;
	}

	// 게시글 내용 조회
	public String getContent() {
		return content;
	}

	// 게시글 수정
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}