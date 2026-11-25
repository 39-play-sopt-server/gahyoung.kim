package org.sopt.member.repository;

import org.sopt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByIdAndDeletedAtIsNull(Long id);

	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndDeletedAtIsNull(String email);

	List<Member> findAllByDeletedAtIsNull();

	boolean existsByEmail(String email);

}
