package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@TableName("student")
public class Student {
    @TableId(type = IdType.AUTO)
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer age;
    @JsonProperty
    private String address;
    // 根据您的表结构添加其他字段
} 