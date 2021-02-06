package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Student;

import java.util.List;

public interface StudentDAL {
    List<Student> getAllStudents();
    Student addNewStudent(Student student);
}
