package com.example.controller;

import com.example.entity.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public ResponseEntity<List<Student>> findAll() {
        return ResponseEntity.ok(studentService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@PathVariable Integer id) {
        logger.info("Finding student with id: {}", id);
        Student student = studentService.findById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Integer> insert(@RequestBody Student student) {
        logger.info("Received student: {}", student);
        return ResponseEntity.ok(studentService.insert(student));
    }
    
    @PutMapping
    public ResponseEntity<Integer> update(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(student));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(studentService.delete(id));
    }
} 