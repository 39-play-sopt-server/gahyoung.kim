package org.sopt.auth.repository;

import org.sopt.auth.domain.OAuthProvider;
import org.sopt.auth.domain.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

	Optional<SocialAccount> findByProviderAndProviderSubject(
			OAuthProvider provider,
			String providerSubject
	);
}