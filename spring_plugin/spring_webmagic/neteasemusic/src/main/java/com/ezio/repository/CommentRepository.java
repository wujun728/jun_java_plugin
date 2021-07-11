package com.ezio.repository;

import com.ezio.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ezio on 2017/6/28.
 */

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	int countByCommentId(int commentId);
}
