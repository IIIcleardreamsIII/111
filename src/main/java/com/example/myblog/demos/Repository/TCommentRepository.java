package com.example.myblog.demos.Repository;

import com.example.myblog.demos.pojo.TComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TCommentRepository extends JpaRepository<TComment, Long> {
    List<TComment> findCommentsByBlogId(Long blogId);

}
