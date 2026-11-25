package org.sopt.auth.contoller;

import jakarta.servlet.http.HttpServletResponse;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.service.vo.AuthTokens;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signup")
	public ResponseEntity<Long> signup(@RequestBody SignupRequest request) {
		return ResponseEntity.ok(authService.signup(request.name(), request.email(), request.password()));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
			@RequestBody LoginRequest request,
			HttpServletResponse response
	) {
		AuthTokens tokens = authService.login(request.email(), request.password());

		ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
				.httpOnly(true)
				.secure(false) // 로컬 HTTP 테스트용
				.sameSite("Strict")
				.path("/auth")
				.maxAge(3600)
				.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return ResponseEntity.ok(new LoginResponse(tokens.accessToken()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
			@AuthenticationPrincipal Long memberId,
			@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
			HttpServletResponse response
	) {
		String accessToken = authorization.substring("Bearer ".length());
		authService.logout(memberId, accessToken);

		ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
				.httpOnly(true)
				.secure(false) // 로컬 HTTP 테스트용
				.sameSite("Strict")
				.path("/auth")
				.maxAge(0)
				.build();

		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/reissue")
	public ResponseEntity<LoginResponse> reissue(
			@CookieValue(name = "refreshToken", required = false) String refreshToken
	) {
		String accessToken = authService.reissueTokens(refreshToken);
		return ResponseEntity.ok(new LoginResponse(accessToken));
	}
}

