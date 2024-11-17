package com.example.myblog.demos.Service;


import com.example.myblog.demos.Repository.TCommentRepository;
import com.example.myblog.demos.pojo.TComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService{

    private final TCommentRepository commentRepository;

    @Autowired
    public CommentService(TCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @Transactional(readOnly = true)
    public List<TComment> findAllComments() {
        return commentRepository.findAll();
    }

    public Page<TComment> findAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public TComment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void saveComment(TComment comment) {
        comment.setCreateTime(new java.util.Date());
        commentRepository.save(comment);
    }

    public List<TComment> findCommentsByBlogId(Long blogId) {
        return commentRepository.findCommentsByBlogId(blogId);
    }
    @Transactional
    public void updateComment(TComment comment) {
        TComment existingComment = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + comment.getId()));
        existingComment.setNickname(comment.getNickname());
        existingComment.setContent(comment.getContent());
        existingComment.setBlogId(comment.getBlogId()); // 确保更新 blogId
        commentRepository.save(existingComment);
    }

    // 删除评论
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public TComment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}