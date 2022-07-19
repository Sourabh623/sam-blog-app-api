package com.samtechblog.repositories;

import com.samtechblog.models.Comment;
import com.samtechblog.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
