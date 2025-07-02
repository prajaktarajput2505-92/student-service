package com.student.student_service.controller;

import com.student.student_service.model.Course;
import com.student.student_service.model.Student;
import com.student.student_service.repository.StudentRepository;
import com.student.student_service.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private StudentRepository repo;

    @Autowired
    private StudentService studentService;


    @GetMapping("/join")
    public List<Student> testJoin() {
        List<Student> students = repo.getStudentsWithJoinOnly();
        // Triggering lazy loading manually
        for (Student s : students) {
            System.out.println("Student: " + s.getName());
            for (Course c : s.getCourses()) {
                System.out.println(c.getName()); // Triggers N+1 queries
            }
        }
        return students;
    }

    @GetMapping("/join-fetch")
    public List<Student> testJoinFetch() {
        List<Student> students = repo.findAllWithCourses();
        // Already loaded course, no extra query
        for (Student s : students) {
            System.out.println("Student: " + s.getName());
            for (Course c : s.getCourses()) {
                System.out.println("  Course: " + c.getName());
            }
        }
        return students;
    }

    @GetMapping("/lock/test1")
    public String lock1() {
        new Thread(() -> studentService.updateStudentName(1, "Thread-1")).start();
        return "Started Thread 1";
    }

    @GetMapping("/lock/test2")
    public String lock2() {
        new Thread(() -> studentService.updateStudentName(1, "Thread-2")).start();
        return "Started Thread 2";
    }
}
