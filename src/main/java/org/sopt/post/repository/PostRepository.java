package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	boolean existsByMember_Id(Long memberId);

	Page<Post> findAllByCategory(PostCategory category, Pageable pageable);
}