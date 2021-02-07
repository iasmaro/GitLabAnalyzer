package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.model.Student;
import com.haumea.gitanalyzer.dao.StudentDAL;
import com.haumea.gitanalyzer.dao.StudentRepository;
import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private BeanFactory beanFactory;

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

    public GitLabApi connectToGitLab(String personalAccessToken){

        return beanFactory.getBean(GitLabApi.class, personalAccessToken);
    }

    public List<String> getProjects(GitLabApi gitLabApi) throws GitLabApiException{

        List<String> projectNames = new ArrayList<>();

        List<Project> projects = gitLabApi.getProjectApi().getMemberProjects();
        for(Project cur : projects){
            projectNames.add(cur.getHttpUrlToRepo());
        }

        return projectNames;
    }
}
