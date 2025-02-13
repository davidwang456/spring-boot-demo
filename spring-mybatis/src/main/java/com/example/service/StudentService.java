package com.example.service;

import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StudentService {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    
    @Autowired
    private StudentMapper studentMapper;
    
    public List<Student> findAll() {
        return studentMapper.findAll();
    }
    
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }
    
    public int insert(Student student) {
        logger.info("Inserting student: {}", student);
        int result = studentMapper.insert(student);
        logger.info("Insert result: {}", result);
        return result;
    }
    
    public int update(Student student) {
        return studentMapper.update(student);
    }
    
    public int delete(Integer id) {
        return studentMapper.delete(id);
    }
} 