package org.sopt.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

// 전역 @EnableJpaAuditing(Step1, 타 담당)에 의존하지 않도록 lifecycle 콜백으로 시간 컬럼을 채운다.
// 모든 도메인 엔티티가 상속하는 JPA 베이스 클래스(@MappedSuperclass). 시간/소프트삭제/수정주체 컬럼을 공통으로 제공한다.

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@PrePersist
	void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public void onDelete() {
		this.deletedAt = LocalDateTime.now();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
