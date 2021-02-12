package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private User findUserByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public User saveUser(User user) throws Exception{

        if(user.getUserId() == null){
            throw new Exception("userID cannot be empty");
        }

        if(findUserByUserId(user.getUserId()) == null){
            mongoTemplate.save(user);
        } else {
            throw new Exception("User already exist!");
        }

        return user;
    }

    public User updateUser(User user) throws Exception{

        if(user.getUserId() == null || user.getPersonalAccessToken() == null){
            throw new Exception("userID and personalAccessToken cannot be empty");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        Update update = new Update();
        update.set("personalAccessToken", user.getPersonalAccessToken());
        if(mongoTemplate.findAndModify(query, update, User.class) == null){
            throw new Exception("User not found!");
        }

        return user;
    }

    public String getPersonalAccessToken(String userId) throws Exception{

        User user = findUserByUserId(userId);

        if(user == null){
            throw new Exception("User not found!");
        }

        return user.getPersonalAccessToken();
    }
}
