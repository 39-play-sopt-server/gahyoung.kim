package org.sopt;


import java.util.List;

public class PostController {
	private final PostService service = new PostService();
	private final PostView view = new PostView();


	public void run() {

		while (true) {

			int command = view.showcontext();

			switch (command) {
				case 1:
					createPost();
					break;

				case 2:
					showPostList();
					break;

				case 3:
					showPost();
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
			String category = view.inputCategory();
			String title = view.inputTitle();
			String content = view.inputContent();

			try {
				service.createPost(category,title, content);
				view.showMessage("게시글이 작성되었습니다.");

			} catch (IllegalArgumentException e) {
				// view.showMessage("게시글이 작성되지 않았습니다."); // 자세한 설명 부족 및 우리가 e에 넣어놓은거 찾을 수 없음
				view.showMessage(e.getMessage());
			}
		}


		private void showPostList() {

			List<Post> posts = service.getAllPosts();

			if (posts.isEmpty()) {
				view.showMessage("게시글이 없습니다.");
				return;
			}

			view.showList(posts);
		}

		private void showPost() {

			List<Post> posts = service.getAllPosts();

			if (posts.isEmpty()) {
				view.showMessage("게시글이 없습니다.");
				return;
			}

			int index = view.inputNumber("조회할 게시글 번호:");

			try {
				Post post = service.getPost(index);
				view.showPost(post);
			} catch (IllegalArgumentException e) {
				view.showMessage(e.getMessage());
			}

		}


		private void updatePost() {

			List<Post> posts = service.getAllPosts();


			if (posts.isEmpty()) {
				view.showMessage("게시글이 없습니다.");
				return;
			}
			int index = view.inputNumber("수정할 게시글 번호:");


			String newTitle = view.inputNewTitle();
			String newContent = view.inputNewContent();


			try {
				service.updatePost(index, newTitle, newContent);
				view.showMessage("게시글이 수정되었습니다.");
			} catch (IllegalArgumentException e) {
				view.showMessage(e.getMessage());
			}
		}


		private void deletePost() {

			List<Post> posts = service.getAllPosts();

			if (posts.isEmpty()) {
				view.showMessage("게시글이 없습니다.");
				return;
			}

			int index = view.inputNumber("삭제할 게시글 번호: ");

			try {
				service.deletePost(index);
				view.showMessage("게시글이 삭제되었습니다.");
			} catch (IllegalArgumentException e) {
				view.showMessage(e.getMessage());
			}
		}
	}


