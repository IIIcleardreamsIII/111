package com.example.myblog.demos.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "t_user")
public class TUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String avatar;
    private Date createTime;
    private String email;
    private String nickname;
    private String password;
    private Integer type;
    private Date updateTime;
    private String username;
}

