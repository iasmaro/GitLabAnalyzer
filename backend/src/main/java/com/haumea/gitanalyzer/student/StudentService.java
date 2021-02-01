package com.haumea.gitanalyzer.student;

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

    public String getCommit(String projectName) throws GitLabApiException {

        GitLabApi gitLabApi = new GitLabApi("http://142.58.22.176/", "XqHspL4ix3qXsww4ismP");

        List<Project> projects = gitLabApi.getProjectApi().getProjects();

        Project selectedProject = null;

        for(Project cur : projects) {

            System.out.println("Project is " + cur.getName());

            if(cur.getName().equals(projectName)) {
                selectedProject = cur;

                System.out.println("name in here is " + selectedProject.getName());


            }
        }


        CommitsApi commits = new CommitsApi(gitLabApi);

        List<Commit> commitData = commits.getCommits(selectedProject);

        String commit = "";
        String newline = System.getProperty("line.separator");


        for (int i=0; i<commitData.size(); i++) {
            commit = commit + "Commit data" + newline + commitData.get(i);

            List<Diff> newCode = commits.getDiff(selectedProject, commitData.get(i).getId());
            for (Diff code : newCode) {
                String difference = code.getDiff().trim();
                commit = commit + ("Diff size: " + difference.length() + newline);
                commit = commit + ("New code: " + code.getDiff() + newline);
            }
        }

        return commit;
    }

}
