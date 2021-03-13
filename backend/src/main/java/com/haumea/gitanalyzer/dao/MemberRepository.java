package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.gitlab.MemberWrapper;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MemberRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;

    }

    public Optional<Member> findMemberByMemberId(String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Member.class));
    }

    public void mapAliasToMember(List<MemberDTO> membersAndAliases) throws ResourceAlredyExistException {

        for(MemberDTO memberDTO : membersAndAliases) {
            Member member = new Member(memberDTO.getMemberId(), memberDTO.getAlias());

            if(!findMemberByMemberId(member.getMemberId()).isPresent()) {

                // need to make sure Alias is not empty so we default to memberId if none is provided
                if(member.getAlias().isEmpty()) {
                    List<String> alias = new ArrayList<>();
                    alias.add(member.getMemberId());
                    member.setAlias(alias);
                }

                mongoTemplate.save(member);
            }
        }
    }


    public List<Member> getMembersAndAliasesFromDatabase(List<String> memberIds) throws ResourceNotFoundException {

        List<Member> members = new ArrayList<>();

        for(String memberId : memberIds) {
            if(!findMemberByMemberId(memberId).isPresent()) {

                // If a member should be in the database but isn't, return a dummy member
                // with an empty alias to let user know they need to add the member to the database.

                List<String> emptyAlias = new ArrayList<>();
                Member member = new Member(memberId, emptyAlias);
                members.add(member);
            }
            else {
                Member member = findMemberByMemberId(memberId).get();
                members.add(member);
            }
        }
        return members;
    }

    // TODO: add member to database

    // TODO: update member alias list

    // TODO: return member by memberId
}
