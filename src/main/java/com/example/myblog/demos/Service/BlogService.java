package com.example.myblog.demos.Service;

import com.example.myblog.demos.Repository.TBlogRepository;
import com.example.myblog.demos.Repository.TCommentRepository;
import com.example.myblog.demos.Repository.TUserRepository;
import com.example.myblog.demos.pojo.TBlog;
import com.example.myblog.demos.pojo.TComment;
import com.example.myblog.demos.pojo.TUser; // 假设有TUser实体类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private TBlogRepository blogRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TUserRepository userRepository;
    @Autowired
    private TCommentRepository commentRepository;
    // 如果需要，可以注入TUser的仓库

    public BlogService(TBlogRepository blogRepository, CommentService commentService) {
        this.blogRepository = blogRepository;
        this.commentService = commentService;
    }


    @Transactional
    public TBlog saveBlog(TBlog blog) {
        if (blog.getComments() != null && !blog.getComments().isEmpty()) {
            blog.getComments().forEach(comment -> {
                comment.setBlogId(blog.getId()); // 确保评论的 blogId 设置正确
                commentRepository.save(comment); // 保存每个评论
            });
        }
        blog.setCreateTime(blog.getCreateTime() != null ? blog.getCreateTime() : new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(blog.getViews()!= null ? blog.getViews()+1 : 0);
        blog.setTypeId(blog.getTypeId()!= null ? blog.getTypeId() : 4);
        blog.setUserId(blog.getUserId()!= null ? blog.getUserId() : 5);
        return blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    public TBlog getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TBlog> findAllBlogs() {
        return blogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<TBlog> findAllBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    public void addCommentToBlog(Long blogId, TComment comment) {
        TBlog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id " + blogId));

        // 设置评论对应的博客ID
        comment.setBlogId(blogId);
        // 保存评论到数据库
        commentRepository.save(comment);
    }

    // 根据userId获取用户昵称的方法，可以添加到TBlogRepository中
    @Transactional(readOnly = true)
    public TUser getUserById(Long blogId) {
        TBlog blog = getBlogById(blogId);
        if (blog != null) {
            // 使用 userRepository 根据 userId 获取 TUser 对象
            TUser user = userRepository.findById(blog.getUserId()).orElse(null);
            return user;
        }
        return null;
    }
    @Transactional
    public TBlog incrementViewCount(Long blogId) {
        TBlog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog not found with id " + blogId));
        System.out.println("计数方法执行了");
        blog.setViews(blog.getViews() + 1);
        return blogRepository.save(blog);
    }

    @Transactional
    public boolean deleteBlogById(Long id) {
        TBlog blog = blogRepository.findById(id).orElse(null);
        if (blog != null) {
            blogRepository.delete(blog);
            return true;
        } else {
            return false;
        }
    }
}