package com.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StudentService extends ServiceImpl<StudentMapper, Student> {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    
    public List<Student> findAll() {
        return list();
    }
    
    public Student findById(Integer id) {
        return getById(id);
    }
    
    public int insert(Student student) {
        logger.info("Inserting student: {}", student);
        return save(student) ? 1 : 0;
    }
    
    public int update(Student student) {
        return updateById(student) ? 1 : 0;
    }
    
    public int delete(Integer id) {
        return removeById(id) ? 1 : 0;
    }
} 