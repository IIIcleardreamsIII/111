package com.example.myblog.demos.Controller;

import com.example.myblog.demos.Service.BlogService;
import com.example.myblog.demos.Service.TTypeService;
import com.example.myblog.demos.pojo.TBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TTypeService typeService;
    @GetMapping
    public String homepage(Model model) {
        List<TBlog> blogs = blogService.findAllBlogs();
        for (TBlog b:blogs) {
            String t = typeService.findTypeById(b.getTypeId()).getName();
            b.setTypeName(t);
        }
        model.addAttribute("blogs", blogs);
        return "index";
    }
}
