package org.sopt.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
	POST_NOT_FOUND("POST_NOT_FOUND","존재하지 않는 게시물입니다.",HttpStatus.NOT_FOUND),
	MEMBER_NOT_FOUND("MEMBER_NOT_FOUND","존재하지 않는 회원입니다.",HttpStatus.NOT_FOUND),
	INVALID_TITLE("INVALID_TITLE","제목은 필수로 입력해야합니다.",HttpStatus.BAD_REQUEST),
	INVALID_CONTENT("INVALID_CONTENT","내용은 필수로 입력해야합니다.",HttpStatus.BAD_REQUEST),
	INVALID_CATEGORY("INVALID_CATEGORY","해당 카테고리는 존재하지 않는 카테고리입니다.",HttpStatus.BAD_REQUEST),
	INVALID_REQUEST("INVALID_REQUEST", "올바르지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
	WITH_POST_MEMBER("WITH_POST_MEMBER", "작성한 게시글이 있는 회원은 삭제할 수 없습니다.", HttpStatus.CONFLICT),
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

	private final String status;
	private final String message;
	private final HttpStatus httpStatus;

	// 생성자
	ErrorType(String status, String message, HttpStatus httpStatus) {
		this.status = status;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	// getter
	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}

