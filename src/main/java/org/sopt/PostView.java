package org.sopt;

import java.util.List;
import java.util.Scanner;

public class PostView {
	private final Scanner scanner = new Scanner(System.in) ;

	public int showMenu() {
		System.out.println("\n=== 게시판 ===");
		System.out.println("1. 게시글 작성");
		System.out.println("2. 게시글 목록 조회");
		System.out.println("3. 게시글 단건 조회");
		System.out.println("4. 게시글 수정");
		System.out.println("5. 게시글 삭제");
		System.out.println("6. 종료");
		System.out.print("선택: ");

		return Integer.parseInt(scanner.nextLine());
	}

	public String inputTitle(){
		System.out.print("제목: ");
		return scanner.nextLine();
	}

	public String inputContent(){
		System.out.println("내용: ");
		return scanner.nextLine();
	}

	public String inputNewTitle() {
		System.out.println("새로운 제목: ");
		return scanner.nextLine();
	}

	public String inputNewContent() {
		System.out.print("새로운 내용: ");
		return scanner.nextLine();
	}

	public int inputPostNumber(String message) {
		System.out.print(message);
		return Integer.parseInt(scanner.nextLine()) - 1;
	}

	public void showPostlist(List<Post> posts) {
		System.out.println("\n=== 게시글 목록 ===");
			for (int i = 0; i < posts.size(); i++) {
				System.out.println(
						(i + 1) + ". " + posts.get(i).getTitle()
				);
			}
	}

	public void showPost(Post post) {
		System.out.println("\n=== 게시글 ===");
		System.out.println("제목: " + post.getTitle());
		System.out.println("내용: " + post.getContent());
	}
	public void showMessage(String message) {
		System.out.println(message);
	}
}
