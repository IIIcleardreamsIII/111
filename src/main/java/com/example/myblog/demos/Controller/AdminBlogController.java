package com.example.myblog.demos.Controller;

import com.example.myblog.demos.Service.BlogService;
import com.example.myblog.demos.Service.CommentService;
import com.example.myblog.demos.pojo.TBlog;
import com.example.myblog.demos.pojo.TComment;
import com.example.myblog.demos.pojo.TUser;
import com.example.myblog.demos.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/blogs")
public class AdminBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String listBlogs(Model model) {
        model.addAttribute("blogs", blogService.findAllBlogs());
        return "blogs-listAdmin";
    }


    @GetMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id, Model model) {
        boolean isDeleted = blogService.deleteBlogById(id);
        if (isDeleted) {
            return "redirect:/admin/blogs/";
        } else {
            model.addAttribute("error", "博客未找到或删除失败");
            return "error1";
        }
    }
    // 跳转到编辑博客表单页面
    @GetMapping("/edit/{id}")
    public String showEditBlogForm(@PathVariable Long id, Model model) {
        TBlog blog = blogService.getBlogById(id);
        if (blog != null) {
            //调用计数方法进行计数
            blogService.incrementViewCount(id);
            model.addAttribute("blog", blog);
            return "edit-blogAdmin";
        } else {
            // 如果博客不存在，重定向到列表页面或其他错误页面
            return "redirect:/blogs/";
        }
    }

    // 处理编辑博客表单提交
    @PostMapping("/edit/{id}")
    public String processEditBlog(@PathVariable Long id, @ModelAttribute TBlog blog, Model model) {
        if (blog.getId() == null || !blog.getId().equals(id)) {
            model.addAttribute("error", "Invalid blog ID");
            return "error1"; // error.html
        }
        if (blog.getTypeId() == null) {
            blog.setTypeId(4L);
        }
        if (blog.getUserId() == null) {
            blog.setUserId(5L);
        }
        TBlog existingBlog = blogService.getBlogById(id);
        if (existingBlog == null) {
            // 如果博客不存在，则返回错误页面
            model.addAttribute("error", "Blog not found");
            return "error1";
        }
        blogService.saveBlog(blog);
        return "redirect:/admin/blogs/";
    }
    @GetMapping("/{id}")
    public String showBlogDetails(@PathVariable Long id, Model model) {
        TBlog blog = blogService.getBlogById(id);
        if (blog != null) {
            TUser user = blogService.getUserById(id);
            List<TComment> comment = commentService.findCommentsByBlogId(id);
            String htmlContent = MarkdownUtils.markdownToHtml(blog.getContent()); // 使用工具类转换Markdown
            model.addAttribute("blog", blog);
            model.addAttribute("user", user);
            model.addAttribute("comment", comment);
            model.addAttribute("htmlContent", htmlContent); // 将转换后的HTML添加到模型
            return "blog-detailsAdmin";
        } else {
            return "error1";
        }
    }
}

