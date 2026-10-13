package org.sopt.exception;

import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
public class GlobalExceptionHandler {

	// PostException 처리
	@ExceptionHandler(PostException.class)
	public ResponseEntity<ApiResponse<Void>> handlePostException(PostException exception) {
		return ApiResponse.error(exception.getErrorType(), exception.getMessage());
	}

	// 잘못된 요청 처리
	@ExceptionHandler({
			HttpMessageNotReadableException.class, // JSON 문법 오류 및 필수요청 본문 없음
			MethodArgumentTypeMismatchException.class // LONG id에 숫자 없으면 에러
	})
	public ResponseEntity<ApiResponse<Void>> handleInvalidRequest(Exception e) {
		return ApiResponse.error(
				ErrorType.INVALID_REQUEST,
				ErrorType.INVALID_REQUEST.getMessage()
		);
	}

	//예상하지 못한 서버 오류
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		if (e instanceof ErrorResponse error) {
			ApiResponse<Void> body = new ApiResponse<>(
					error.getStatusCode().value(),
					"HTTP_ERROR",
					"요청을 처리할 수 없습니다.",
					null
			);

			return ResponseEntity.status(error.getStatusCode())
					.headers(error.getHeaders())
					.body(body);
		}

		// 나머진 서버오류
		return ApiResponse.error(
				ErrorType.INTERNAL_SERVER_ERROR,
				ErrorType.INTERNAL_SERVER_ERROR.getMessage()
		);
	}
}