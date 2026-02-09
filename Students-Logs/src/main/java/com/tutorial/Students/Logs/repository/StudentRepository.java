package com.tutorial.Students.Logs.repository;

import com.tutorial.Students.Logs.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
    extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);
}
