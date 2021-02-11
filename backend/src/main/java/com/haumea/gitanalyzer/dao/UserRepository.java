package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User saveUser(User user) throws Exception{

        if(user.getUserId() == null){
            throw new Exception("userID cannot be empty");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        if(mongoTemplate.findOne(query, User.class) == null){
            mongoTemplate.save(user);
            return user;
        } else {
            throw new Exception("User already exist!");
        }
    }
}
