package com.example.myblog.demos.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "t_blog")
public class TBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 博客的唯一标识符，由数据库自动生成

    private boolean appreciation; // 是否允许点赞
    private boolean commentabled; // 是否允许评论
    private String content; // 博客的主要内容
    private Date createTime; // 博客的创建时间
    private String description; // 博客的简要描述
    private String firstPicture; // 博客第一张图片的URL或路径
    private boolean published; // 博客是否已发布（对公众可见）
    private boolean recommend; // 博客是否被推荐
    private boolean shareStatement; // 是否允许分享声明
    private String title; // 博客的标题
    private Date updateTime; // 博客的最后更新时间
    private Integer views; // 博客的浏览次数
    private Long typeId; // 外键，链接到博客的类型或类别
    private Long userId; // 外键，链接到创建博客的用户
    @OneToMany(mappedBy = "blogId", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TComment> comments;
    private String typeName;

    public void addComment(TComment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
        // 这里不设置 comment.setBlogId(this.id); 因为这是双向关系，应由 TComment 实体管理
    }
}

