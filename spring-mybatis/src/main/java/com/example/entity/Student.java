package com.example.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Student {
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