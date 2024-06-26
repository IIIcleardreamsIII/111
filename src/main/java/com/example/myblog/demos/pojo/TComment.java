package com.example.myblog.demos.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "t_comment")
public class TComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    @Column(name = "blog_id")
    private Long blogId;
    private Long parentCommentId;
    private boolean adminComment;
    public TComment() {
        this.createTime = new Date();
    }

}
