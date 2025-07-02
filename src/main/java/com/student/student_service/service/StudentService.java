package com.student.student_service.service;

import com.student.student_service.model.Student;
import com.student.student_service.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Transactional
    public void updateStudentName(Integer id, String newName) {
        Student student = studentRepo.findByIdWithLock(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(newName);
        // Simulate long-running transaction
        try {
            Thread.sleep(10000); // 10 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
