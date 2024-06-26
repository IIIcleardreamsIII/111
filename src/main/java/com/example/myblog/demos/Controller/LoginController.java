package com.example.myblog.demos.Controller;

import com.example.myblog.demos.Service.BlogService;
import com.example.myblog.demos.Service.CommentService;
import com.example.myblog.demos.Service.UserService;
import com.example.myblog.demos.pojo.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @GetMapping
    public String showLoginForm(Model model) {
        model.addAttribute("user", new TUser());
        return "login";
    }
    @PostMapping
    public String handleLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        TUser user = userService.authenticate(username, password);
        if (user != null) {
            model.addAttribute("blogs", blogService.findAllBlogs());
            model.addAttribute("comments", commentService.findAllComments());
            session.setAttribute("user", user); // 存储用户信息到会话
            return "blogs-listAdmin"; // 登录成功，重定向到管理页面
        } else {
            model.addAttribute("error", "用户名或密码错误");
            return "login"; // 登录失败，返回登录页面
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 使会话无效
        return "redirect:/login"; // 重定向到登录页面
    }
}
