package org.sopt;

public class Post {
	private String category;
	private String title;
	private String content;

	public Post(String category,String title, String content) {
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

	public String getCategory() {
		return category;
	}

}