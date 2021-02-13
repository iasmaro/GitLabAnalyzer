package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MemberRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    // TODO: add member to database

    // TODO: update member alias list

    // TODO: return member by memberId
}
