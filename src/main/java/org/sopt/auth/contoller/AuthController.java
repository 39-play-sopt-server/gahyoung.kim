package org.sopt.auth.contoller;

import jakarta.servlet.http.HttpServletResponse;
import org.sopt.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return ResponseEntity.ok(authService.signup(request.email(), request.password()));
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response
	) {
		String accessToken = authService.login(request.email(), request.password(),response);
		return ResponseEntity.ok(new LoginResponse(accessToken));
	}
}

