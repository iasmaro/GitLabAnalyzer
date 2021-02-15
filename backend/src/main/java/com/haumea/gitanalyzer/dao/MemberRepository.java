package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.MemberDTO;
import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MemberRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private Member findMemberByMemberId(String memberId){
        Query query = new Query();
        query.addCriteria(Criteria.where("memberId").is(memberId));
        return mongoTemplate.findOne(query, Member.class);
    }

    public List<MemberDTO> mapAliasToMember(List<MemberDTO> membersAndAliases) throws ResourceAlredyExistException {
        for(MemberDTO memberDTO : membersAndAliases) {
            if(findMemberByMemberId(memberDTO.getMemberId()) == null){
                mongoTemplate.save(memberDTO);
            } else {
                throw new ResourceAlredyExistException("Member" + memberDTO.getMemberId() + "already exists!");
            }
        }
        return membersAndAliases;
    }

    // TODO: add member to database

    // TODO: update member alias list

    // TODO: return member by memberId
}
