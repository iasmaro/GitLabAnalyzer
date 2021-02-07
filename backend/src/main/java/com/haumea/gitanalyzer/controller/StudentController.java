package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.Student;
import com.haumea.gitanalyzer.service.StudentService;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public List<Student> getStudent(){

        return studentService.getStudent();
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

    @GetMapping("/projects")
    public List<String> getConnection(@RequestBody String personalAccessToken){

        GitLabApi gitLabApi = studentService.connectToGitLab(personalAccessToken);

        try{
            return studentService.getProjects(gitLabApi);
        }
        catch (GitLabApiException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projects not found.", e);

        }
    }

}
