package com.example.myblog.demos.Controller;

import com.example.myblog.demos.Service.BlogService;
import com.example.myblog.demos.Service.CommentService;
import com.example.myblog.demos.Service.UserService;
import com.example.myblog.demos.pojo.TBlog;
import com.example.myblog.demos.pojo.TComment;
import com.example.myblog.demos.pojo.TUser;
import com.example.myblog.demos.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/frontend/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    // 展示博客列表页面
    @GetMapping("/")
    public String listBlogs(Model model) {
        List<TBlog> blogs = blogService.findAllBlogs();
        model.addAttribute("blogs",blogs ); // 假设findAllBlogs方法存在
        return "blogs-list"; // blogs-list.html
    }

    // 跳转到添加博客表单页面
    @GetMapping("/add")
    public String showAddBlogForm(Model model) {
        model.addAttribute("blog", new TBlog());
        return "add-blog"; // add-blog.html
    }

    // 处理添加博客表单提交
    @PostMapping("/add")
    public String processAddBlog(@ModelAttribute TBlog blog,
                                 @RequestParam("userId") Long userId,
                                 @RequestParam("username") String username,
                                @RequestParam("typeId") Long typeId) {
        try{
            TUser user = blogService.getUserById(userId);
            blog.setUserId(user.getId());
            blog.setTypeId(typeId);
            blogService.saveBlog(blog);
            return "redirect:/frontend/blogs/";
        }catch (Exception e){
            TUser user = new TUser();
            user.setId(userId);
            // 假设用户名称也从某处获取
            user.setUsername(username);
            userService.saveUser(user);
            blog.setUserId(user.getId());
            // 设置博客类型ID
            blog.setTypeId(typeId);
            // 保存博客
            blogService.saveBlog(blog);
            return "redirect:/frontend/blogs/";
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
            return "edit-blog"; // edit-blog.html
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

        TBlog existingBlog = blogService.getBlogById(id);
        if (existingBlog == null) {
            // 如果博客不存在，则返回错误页面
            model.addAttribute("error", "Blog not found");
            return "error1"; // error.html
        }
        blogService.saveBlog(blog); // 假设saveBlog方法更新博客
        return "redirect:/blogs/"; // 重定向到博客列表页面
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
            return "blog-details";
        } else {
            return "error1";
        }
    }

    // 处理评论的添加
    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @ModelAttribute TComment comment) {

        blogService.addCommentToBlog(id, comment);

        return "redirect:/frontend/blogs/" + id;
    }
    // 控制器中的方法
    @PostMapping("/view")
    public ResponseEntity<?> incrementViewCount(@RequestParam Long blogId) {
        TBlog blog = blogService.incrementViewCount(blogId);
        return ResponseEntity.ok().body(blog.getViews());
    }
}
