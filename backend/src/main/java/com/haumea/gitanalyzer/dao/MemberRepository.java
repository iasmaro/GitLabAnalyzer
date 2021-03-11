package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.exception.GitLabRuntimeException;
import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
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

    public Optional<Member> findMemberByMemberId(String memberId){
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        return Optional.ofNullable(mongoTemplate.findOne(query, Member.class));
    }

    public void mapAliasToMember(List<MemberDTO> membersAndAliases) throws ResourceAlredyExistException {
        for(MemberDTO memberDTO : membersAndAliases) {
            Member member = new Member(memberDTO.getMemberId(), memberDTO.getAlias());
            if(!findMemberByMemberId(member.getMemberId()).isPresent()){
                mongoTemplate.save(member);
            } else {
                throw new ResourceAlredyExistException("Member " + memberDTO.getMemberId() + " already exists!");
            }
        }
    }


    public List<Member> getMembersAndAliases(List<String> memberIds) throws ResourceNotFoundException {

        List<Member> members = new ArrayList<>();

        for(String memberId : memberIds){
            if(!findMemberByMemberId(memberId).isPresent()){
                throw new ResourceNotFoundException("Member not found!");
            } else {
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
