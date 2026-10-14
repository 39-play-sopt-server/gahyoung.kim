package org.sopt.global.exception;

public class PostException extends RuntimeException {

	private final ErrorType errorType;

	// 생성자
	public PostException(ErrorType errorType) {
		super(errorType.getMessage());
		this.errorType = errorType;
	}
	// getter
	public ErrorType getErrorType(){
		return errorType;
	}
}