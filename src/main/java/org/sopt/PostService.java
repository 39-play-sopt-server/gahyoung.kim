package org.sopt;

import java.util.List;

public class PostService {
	private PostRepository repository = new PostRepository();

	public void createPost(String category, String title, String content) {

		if (category.isBlank() || title.isBlank() || content.isBlank()) {
			throw new IllegalArgumentException("카테고리와 제목과 내용은 비어 있을 수 없습니다.");
		}

		Post post = new Post(category, title, content);
		repository.save(post);
	}

	public List<Post> getAllPosts() {

		return repository.findAll();
	}

	public Post getPost(int index) {

		if (index < 0 || index >= repository.findAll().size()) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}

		return repository.findIndex(index);
	}



	public void updatePost(int index, String newTitle, String newContent) {

		if (index < 0 || index >= repository.findAll().size()) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}

		if (newTitle.isBlank() || newContent.isBlank()) {
			throw new IllegalArgumentException("제목과 내용은 비어 있을 수 없습니다.");
		}
// 		Post posts = new Post(newTitle,newContent); -- 이렇게 하면 새로운 게시글 작성으로 여겨짐

		Post post = repository.findIndex(index);
		post.update(newTitle, newContent);
	}

	public void deletePost(int index) {

		if (index < 0 || index >= repository.findAll().size()) {
			throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
		}

		repository.deleteIndex(index);
	}
}
