package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.dao.MemberDAL;
import com.haumea.gitanalyzer.dao.MemberRepository;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private BeanFactory beanFactory;

    private final MemberRepository memberRepository;
    private final MemberDAL memberDAL;

    @Autowired
    public MemberService(MemberRepository memberRepository, MemberDAL memberDAL) {

        this.memberRepository = memberRepository;
        this.memberDAL = memberDAL;
    }

    public List<Member> getMember(){

        return memberRepository.findAll();

    }

    public void addMember(Member member){

        memberRepository.save(member);
    }

    public List<Member> getMemberDAL(){

        return memberDAL.getAllMembers();

    }

    public void addMemberDAL(Member member){

        memberDAL.addNewMember(member);
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
