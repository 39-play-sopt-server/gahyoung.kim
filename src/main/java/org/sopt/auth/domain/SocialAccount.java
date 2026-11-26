package org.sopt.auth.domain;

import jakarta.persistence.*;
import org.sopt.member.domain.Member;

@Entity
@Table(
		name = "social_account",
		uniqueConstraints = @UniqueConstraint(
				name = "uk_social_account_provider_subject",
				columnNames = {"provider", "provider_subject"}
		)
)
public class SocialAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private OAuthProvider provider;

	@Column(name = "provider_subject", nullable = false)
	private String providerSubject;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	protected SocialAccount() {
	}

	public SocialAccount(OAuthProvider provider, String providerSubject, Member member) {
		this.provider = provider;
		this.providerSubject = providerSubject;
		this.member = member;
	}

	public Member getMember() {
		return member;
	}
}