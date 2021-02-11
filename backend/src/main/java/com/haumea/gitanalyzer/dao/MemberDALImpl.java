package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDALImpl implements MemberDAL {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MemberDALImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Member> getAllMembers() {
        return mongoTemplate.findAll(Member.class);
    }

    @Override
    public Member addNewMember(Member member){
        // with mongoTemplte, ID is auto generated
        mongoTemplate.save(member);
        return member;
    }

}
