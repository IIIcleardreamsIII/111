package com.example.myblog.demos.Controller;

import com.example.myblog.demos.Service.CommentService;
import com.example.myblog.demos.pojo.TComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String listComments(Model model) {
        model.addAttribute("comments", commentService.findAllComments());
        return "/comments-list";
    }
    @PostMapping("/{blogId}")
    public String addComment(@PathVariable Long blogId, @ModelAttribute TComment comment) {
        comment.setCreateTime(new java.util.Date());
        commentService.saveComment(comment); // 假设这里会关联到博客
        return "/frontend/blogs/" + blogId;
    }
    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id, Model model) {
        TComment comment = commentService.findById(id);
        if (comment != null) {
            model.addAttribute("comment", comment);
            return "comment-edit"; // 评论修改页面
        }
        return "error1"; // 如果评论不存在，显示错误页面
    }

    // 提交修改的评论
    @PostMapping("/edit")
    public String updateComment(@ModelAttribute TComment comment, @RequestParam("blogId") Long blogId) {
        comment.setBlogId(blogId); // 重新设置 blogId
        commentService.updateComment(comment);
        return "redirect:/frontend/comments"; // 修改后重定向到评论列表
    }
    // 删除评论的路由
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return "redirect:/admin/comments/"; // 删除后重定向到评论列表
    }

}
