package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private Optional<User> findUserByUserId(String userId){
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    public User saveUser(User user) throws ResourceAlredyExistException {

        if(!findUserByUserId(user.getUserId()).isPresent()){
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
        if(!(user.getPersonalAccessToken() == null) && !user.getPersonalAccessToken().trim().isEmpty()){
            update.set("personalAccessToken", user.getPersonalAccessToken());
        }
        if(!(user.getGitlabServer() == null) && !user.getGitlabServer().trim().isEmpty()){
            update.set("gitlabServer", user.getGitlabServer());
        }

        if(mongoTemplate.findAndModify(query, update, User.class) == null){
            throw new ResourceNotFoundException("User not found!");
        }

        return user;
    }

    public String getPersonalAccessToken(String userId) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()){
            throw new ResourceNotFoundException("User not found!");
        }

        String token = user.get().getPersonalAccessToken();

        if(token == null){
            throw new ResourceNotFoundException("Token not found!");
        }

        return token;
    }

    public String getGitlabServer(String userId) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()){
            throw new ResourceNotFoundException("User not found!");
        }

        String gitlabServer = user.get().getGitlabServer();

        if(gitlabServer == null){
            throw new ResourceNotFoundException("Gitlab Server not found!");
        }

        return gitlabServer;
    }

}
