package com.example.myblog.demos.Repository;

import com.example.myblog.demos.pojo.TBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TBlogRepository extends JpaRepository<TBlog, Long> {
    List<TBlog> findAll();
}