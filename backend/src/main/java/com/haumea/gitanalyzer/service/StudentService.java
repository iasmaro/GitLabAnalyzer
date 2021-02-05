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

    public Project getProject(String projectName, GitLabApi gitLabApi) throws GitLabApiException{

        List<Project> projects = gitLabApi.getProjectApi().getMemberProjects();

        Project selectedProject = null;

        for(Project cur : projects) {
            System.out.println("Project is " + cur.getName());
            if(cur.getName().equals(projectName)) {
                selectedProject = cur;

                System.out.println("name in here is " + selectedProject.getName());
            }
        }

        return selectedProject;
    }

    public List<Student> getCommits(String projectName, String hostUrl, String personalAccessToken) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken);

        Project selectedProject = getProject(projectName, gitLabApi);

        CommitsApi commits = new CommitsApi(gitLabApi);

        List<Commit> commitData = commits.getCommits(selectedProject);

        for (int i = 0; i < commitData.size(); i++) {

            Commit currentCommit = commitData.get(i);

            String studentName = currentCommit.getCommitterName();
            String studentID = currentCommit.getId();
            String studentEmail = currentCommit.getCommitterEmail();
            String committedCode = "";

            List<Diff> newCode = commits.getDiff(selectedProject, commitData.get(i).getId());
            for (Diff code : newCode) {

                committedCode = committedCode + "New Code:\n" + code.getDiff();
            }

            studentDAL.addNewStudent(new Student(studentID, studentName, studentEmail, committedCode));
        }

        //studentRepository.deleteAll();

        return studentRepository.findAll();
    }

}
