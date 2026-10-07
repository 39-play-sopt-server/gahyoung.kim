package org.sopt;

import java.util.List;

public class PostService {
	private PostRepository repository = new PostRepository();

	public void createPost(String category, String title, String content) {

		if (category.isBlank() || title.isBlank() || content.isBlank()) {
			throw new PostException("카테고리와 제목과 내용은 비어 있을 수 없습니다.");
		}

		Category postCategory;

		try {
			postCategory = Category.valueOf(category.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new PostException("존재하지 않는 카테고리입니다.");
		}

		Post post = new Post(postCategory, title, content);
		repository.save(post);
	}

	public List<Post> getAllPosts() {

		return repository.findAll();
	}

	public Post getPost(Long id) {

		validatePostId(id);

		return repository.findById(id);
	}



	public void updatePost(Long id, String newTitle, String newContent) {
		validatePostId(id);

		if (newTitle.isBlank() || newContent.isBlank()) {
			throw new PostException("제목과 내용은 비어 있을 수 없습니다.");
		}
// 		Post posts = new Post(newTitle,newContent); -- 이렇게 하면 새로운 게시글 작성으로 여겨짐

		Post post = repository.findById(id);
		post.update(newTitle, newContent);
	}

	public void deletePost(Long id) {

		validatePostId(id);
		repository.deleteById(id);
	}
	private void validatePostId(Long id) {
		if (!repository.existsById(id)) {
			throw new PostException("존재하지 않는 게시글입니다.");
		}
	}
}
