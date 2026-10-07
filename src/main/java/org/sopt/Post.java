package org.sopt;

public class Post {
	private Long id;
	private Category category;
	private String title;
	private String content;

	public Post(Category category,String title, String content) {
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

	public Category getCategory() {
		return category;
	}

	public void assignId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}