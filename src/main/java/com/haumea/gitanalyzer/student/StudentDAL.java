package com.haumea.gitanalyzer.student;

import java.util.List;

public interface StudentDAL {
    List<Student> getAllStudents();
    Student addNewStudent(Student student);
}
