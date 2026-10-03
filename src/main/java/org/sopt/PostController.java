package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostController {

	private final List<Post> posts = new ArrayList<>();
	private final PostView view = new PostView();

	public void run() {

		while (true) {
			int command = view.showMenu();

			switch (command) {
				case 1:
					createPost();
					break;

				case 2:
					readPostList();
					break;

				case 3:
					readPost();
					break;

				case 4:
					updatePost();
					break;

				case 5:
					deletePost();
					break;

				case 6:
					view.showMessage("프로그램을 종료합니다.");
					return;

				default:
					view.showMessage("잘못된 입력입니다.");
			}
		}
	}

	private void createPost() {
		String title = view.inputTitle();
		String content = view.inputContent();

		Post post = new Post(title, content);
		posts.add(post);

		view.showMessage("게시글이 작성되었습니다.");
	}

	private void readPostList() {
		if (posts.isEmpty()) {
			view.showMessage("게시글이 없습니다.");
			return;
		}


		view.showPostlist(posts);
	}

	private void readPost() {
		if (posts.isEmpty()) {
			view.showMessage("게시글이 없습니다.");
			return;
		}

		int index = view.inputPostNumber("조회할 게시글 번호: ");

		if (!isValidIndex(index)) {
			view.showMessage("존재하지 않는 게시글입니다.");
			return;
		}

		Post post = posts.get(index);
		view.showPost(post);
	}

	private void updatePost() {
		if (posts.isEmpty()) {
			view.showMessage("게시글이 없습니다.");
			return;
		}

		int index = view.inputPostNumber("수정할 게시글 번호: ");

		if (!isValidIndex(index)) {
			view.showMessage("존재하지 않는 게시글입니다.");
			return;
		}

		Post post = posts.get(index);

		String newTitle = view.inputNewTitle();
		String newContent = view.inputNewContent();

		post.update(newTitle, newContent);

		view.showMessage("게시글이 수정되었습니다.");
	}

	private void deletePost() {
		if (posts.isEmpty()) {
			view.showMessage("게시글이 없습니다.");
			return;
		}

		int index = view.inputPostNumber("삭제할 게시글 번호: ");

		if (!isValidIndex(index)) {
			view.showMessage("존재하지 않는 게시글입니다.");
			return;
		}

		posts.remove(index);

		view.showMessage("게시글이 삭제되었습니다.");
	}

	private boolean isValidIndex(int index) {
		return index >= 0 && index < posts.size();
	}
}