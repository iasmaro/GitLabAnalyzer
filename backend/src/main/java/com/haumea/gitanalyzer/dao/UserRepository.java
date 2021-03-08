package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.model.Configuration;
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

    public User saveUser(User user) throws ResourceAlredyExistException {

        if(findUserByUserId(user.getUserId()) == null){
            mongoTemplate.save(user);
        } else {
            throw new ResourceAlredyExistException("User already exist!");
        }

        return user;
    }

    public User updateUser(User user) throws ResourceNotFoundException{

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        Update update = new Update();
        update.set("personalAccessToken", user.getPersonalAccessToken());
        if(mongoTemplate.findAndModify(query, update, User.class) == null){
            throw new ResourceNotFoundException("User not found!");
        }

        return user;
    }

    public String getPersonalAccessToken(String userId) throws ResourceNotFoundException {

        User user = findUserByUserId(userId);

        if(user == null){
            throw new ResourceNotFoundException("User not found!");
        }

        String token = user.getPersonalAccessToken();

        if(token == null){
            throw new ResourceNotFoundException("Token not found!");
        }

        return token;
    }

    public User saveConfiguration(String userId, Configuration configuration) throws ResourceNotFoundException {

        User user = findUserByUserId(userId);

        if(user == null){
            throw new ResourceNotFoundException("User not found!");
        }

        System.out.println(user.getConfigurations().size());
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        Update update = new Update();
        update.push("configurations", configuration);
        mongoTemplate.updateFirst(query, update, User.class);
        System.out.println(user.getConfigurations().size());

        return user;
    }

}
