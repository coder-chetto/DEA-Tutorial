package com.tutorial.Students.Logs.service;

import com.tutorial.Students.Logs.entity.Student;
import com.tutorial.Students.Logs.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Page<Student> getStudents(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return studentRepository.findAll(pageRequest);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updated) {
        Student existing = getStudent(id);
        if (!existing.getEmail().equals(updated.getEmail())
                && studentRepository.existsByEmail(updated.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setBatch(updated.getBatch());
        existing.setGpa(updated.getGpa());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student existing = getStudent(id);
        studentRepository.delete(existing);
    }
}
