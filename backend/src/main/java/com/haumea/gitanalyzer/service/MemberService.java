package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.MemberRepository;
import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.dto.MemberRRDTO;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MemberWrapper;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.utility.GlobalConstants;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserService userService;

    @Autowired
    public MemberService(MemberRepository memberRepository, UserService userService) {

        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    public List<String> getMembers(String userId, Integer projectId) {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, token);

        List<String> members = new ArrayList<>();

        List<MemberWrapper> gitlabMembers = gitlabService.getMembers(projectId);
        for(MemberWrapper current: gitlabMembers){
            members.add(current.getUsername());
        }

            return members;
    }

    public void mapAliasToMember(List<MemberDTO> membersAndAliases){

        memberRepository.mapAliasToMember(membersAndAliases);

    }

    public MemberRRDTO getMembersAndAliases(String userId, Integer projectId) {

        String token = userService.getPersonalAccessToken(userId);

        GitlabService gitlabService = new GitlabService(GlobalConstants.gitlabURL, token);

        List<String> members = getMembers(userId, projectId);

        List<String> aliases = new ArrayList<>();

        List<Commit> commits = gitlabService.getAllCommitsNoDiff(projectId);

        for (Commit commit: commits){
            String alias = commit.getAuthorName();
            if(!aliases.contains(alias)){
                aliases.add(alias);
            }
        }

        MemberRRDTO memberRRDTO = new MemberRRDTO(members, aliases);

        return memberRRDTO;
    }

    public List<String> getAliasesForSelectedMember(String memberId) {

        Member member = memberRepository.findMemberByMemberId(memberId).get();

        return member.getAlias();
    }
}
