package org.sopt;

import java.util.List;
import java.util.Scanner;

public class PostView {
	private final Scanner scanner = new Scanner(System.in);

	public int showcontext() {

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
	public String inputCategory() {
		System.out.print("카테고리: ");
		return scanner.nextLine();
	}
	public String inputTitle() {
		System.out.print("제목: ");
		return scanner.nextLine();
	}

	public String inputContent() {
		System.out.print("내용: ");
		return scanner.nextLine();
	}

	public Long inputId(String message) {
		System.out.print(message);
		return Long.parseLong(scanner.nextLine());
	}


	public String inputNewTitle() {
		System.out.print("새로운 제목: ");
		return scanner.nextLine();
	}

	public String inputNewContent() {
		System.out.print("새로운 내용: ");
		return scanner.nextLine();
	}
	// 이렇게 삭제 수정 다 나눠서 코드짜도 가능 단, 그럼 3개를 각각 나눠야하니까 한 번에 묶는 방법으로 사용하자
	// public int inputNumber() {
	// 	System.out.print("조회할 게시글 번호: ");
	// 	return Integer.parseInt(scanner.nextLine()) - 1;
	// }

	public int inputNumber(String message) {
		System.out.println(message);
		return Integer.parseInt(scanner.nextLine()) - 1;
	}

	public void showList(List<Post> posts) {
		System.out.println("\n=== 게시글 목록 ===");

		for (Post post : posts) {
			System.out.println(
					post.getId() + ".  [" + post.getCategory() + "]" + post.getTitle()
			);
		}
	}

	public void showPost(Post post) {
		System.out.println("\n=== 게시글 ===");
		System.out.println("번호: " + post.getId());
		System.out.println("카테고리: " + post.getCategory());
		System.out.println("제목: " + post.getTitle());
		System.out.println("내용: " + post.getContent());
	}

	// 일반 메세지 출력은 아래와 같이 묶어서 하나의 메서드로 묶어서출력하면 좋음
	//System.out.println("게시글이 없습니다.");
	//System.out.println("존재하지 않는 게시글입니다.");
	//System.out.println("게시글이 작성되었습니다.");
	//System.out.println("게시글이 수정되었습니다.");
	//System.out.println("게시글이 삭제되었습니다."); 이런것들.

	public void showMessage(String message) {
		System.out.println(message);
	}


}