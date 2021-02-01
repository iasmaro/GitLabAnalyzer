package com.haumea.gitanalyzer.student;

import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    // Using MongoRepository under the hood
    @GetMapping("/all")
    public String getStudent(){

        return "Hello\\\n Hello again";
    }

    // ID must be provide in the request body
    @PostMapping("/add")
    public String saveStudent(@RequestBody Student student){

        studentService.addStudent(student);
        return "Student added!";

    }

    // Using MongoTemplate under the hood
    @GetMapping("/all/dal")
    public List<Student> getStudentDAL(){

        return studentService.getStudentDAL();
    }

    // ID is auto generated
    @PostMapping("/add/dal")
    public String saveStudentDAL(@RequestBody Student student){

        studentService.addStudentDAL(student);
        return "Student added!";

    }

    @GetMapping("/commit")
    public String getCommit() throws GitLabApiException {

        return studentService.getCommit("tester");
    }

}
