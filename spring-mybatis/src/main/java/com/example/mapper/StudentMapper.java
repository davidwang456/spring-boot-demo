package com.example.mapper;

import com.example.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    
    @Select("SELECT * FROM student")
    List<Student> findAll();
    
    @Select("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") Integer id);
    
    @Insert("INSERT INTO student(name, age, address) VALUES(#{name}, #{age}, #{address})")
    int insert(Student student);
    
    @Update("UPDATE student SET name = #{name}, age = #{age}, address = #{address} WHERE id = #{id}")
    int update(Student student);
    
    @Delete("DELETE FROM student WHERE id = #{id}")
    int delete(@Param("id") Integer id);
} 