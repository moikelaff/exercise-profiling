package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Cacheable("studentsWithCourses")
    public List<StudentCourse> getAllStudentsWithCourses() {
        return studentCourseRepository.findAllWithStudentAndCourse();
    }

    @Cacheable("highestGpaStudent")
    public Optional<Student> findStudentWithHighestGpa() {
        List<Student> students = studentRepository.findAllByOrderByGpaDesc();
        return students.isEmpty() ? Optional.empty() : Optional.of(students.get(0));
    }

    @Cacheable("studentNames")
    public String joinStudentNames() {
        List<String> names = studentRepository.findAllStudentNames();
        return String.join(", ", names);
    }
}