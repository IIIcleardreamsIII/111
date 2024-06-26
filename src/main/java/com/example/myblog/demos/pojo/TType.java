package com.example.myblog.demos.pojo;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "t_type")
public class TType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
