package com.haumea.gitanalyzer.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentDAL studentDAL;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentDAL studentDAL) {

        this.studentRepository = studentRepository;
        this.studentDAL = studentDAL;
    }

    public List<Student> getStudent(){

        return studentRepository.findAll();

    }

    public void addStudent(Student student){

        studentRepository.save(student);
    }

    public List<Student> getStudentDAL(){

        return studentDAL.getAllStudents();

    }

    public void addStudentDAL(Student student){

        studentDAL.addNewStudent(student);
    }
}
