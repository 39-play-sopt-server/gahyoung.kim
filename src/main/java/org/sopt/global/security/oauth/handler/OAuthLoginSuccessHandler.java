package org.sopt.global.security.oauth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.sopt.auth.service.AuthService;
import org.sopt.auth.service.vo.AuthTokens;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final AuthService authService;

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) {
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");

		try {
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			AuthTokens tokens = authService.socialLogin(
					oauthToken.getAuthorizedClientRegistrationId(),
					oauthToken.getPrincipal().getAttributes()
			);

			ResponseCookie cookie = ResponseCookie.from("refreshToken", tokens.refreshToken())
					.httpOnly(true)
					.secure(request.isSecure())
					.sameSite("Strict")
					.path("/auth")
					.maxAge(3600)
					.build();

			response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken());
			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (ResponseStatusException e) {
			response.setStatus(e.getStatusCode().value());
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} finally {
			SecurityContextHolder.clearContext();
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
		}
	}
}