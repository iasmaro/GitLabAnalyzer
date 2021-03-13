package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.exception.ResourceAlredyExistException;
import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.model.Configuration;
import com.haumea.gitanalyzer.model.User;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    private Optional<User> findUserByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    public User saveUser(User user) throws ResourceAlredyExistException {

        if(!findUserByUserId(user.getUserId()).isPresent()) {
            mongoTemplate.save(user);
        }
        else {
            throw new ResourceAlredyExistException("User already exist!");
        }

        return user;
    }

    public User updateUser(User user) throws ResourceNotFoundException {

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(user.getUserId()));
        Update update = new Update();
        if(!(user.getPersonalAccessToken() == null) && !user.getPersonalAccessToken().trim().isEmpty()) {
            update.set("personalAccessToken", user.getPersonalAccessToken());
        }
        if(!(user.getGitlabServer() == null) && !user.getGitlabServer().trim().isEmpty()) {
            update.set("gitlabServer", user.getGitlabServer());
        }
        if(!(user.getActiveConfig() == null) && !user.getActiveConfig().trim().isEmpty()) {
            if(!getConfigurationFileNames(user.getUserId()).contains(user.getActiveConfig())){
                throw new ResourceNotFoundException(user.getActiveConfig() + " configuration not found!");
            }
            update.set("activeConfig", user.getActiveConfig());
        }

        if(mongoTemplate.findAndModify(query, update, User.class) == null) {
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

        if(token == null) {
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

    public String getActiveConfig(String userId) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()){
            throw new ResourceNotFoundException("User not found!");
        }

        String activeConfig = user.get().getActiveConfig();

        if(activeConfig == null){
            throw new ResourceNotFoundException("Default Config not found!");
        }

        return activeConfig;
    }

    public List<String> getConfigurationFileNames(String userId) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found!");
        }

        List<Configuration> userConfigurations = user.get().getConfigurations();
        List<String> fileNames = new ArrayList<>();
        for(Configuration userConfiguration : userConfigurations) {
            String fileName = userConfiguration.getFileName();
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public User saveConfiguration(String userId, Configuration configuration) throws ResourceNotFoundException, ResourceAlredyExistException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found!");
        }

        List<String> fileNames = getConfigurationFileNames(user.get().getUserId());

        if(!fileNames.contains(configuration.getFileName())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(user.get().getUserId()));
            Update update = new Update();
            update.push("configurations", configuration);
            mongoTemplate.updateFirst(query, update, User.class);
        }
        else {
            throw new ResourceAlredyExistException("Configuration with this name already exist!");
        }

        return user.get();
    }

    public Configuration getConfigurationByFileName(String userId, String configFileName) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found!");
        }

        List<String> fileNames = getConfigurationFileNames(user.get().getUserId());

        if(!fileNames.contains(configFileName)) {
            throw new ResourceNotFoundException("Configuration with this name does not exist!");
        }

        Configuration requestedConfig = null;

        for(Configuration userConfig : user.get().getConfigurations()) {
            if(userConfig.getFileName() == configFileName) {
                requestedConfig = userConfig;
            }
        }

        return requestedConfig;
    }

    public User updateConfiguration(String userId, Configuration configuration) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found!");
        }

        List<String> fileNames = getConfigurationFileNames(user.get().getUserId());

        if(fileNames.contains(configuration.getFileName())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(user.get().getUserId())
                                        .and("configurations.fileName").is(configuration.getFileName()));
            Update update = new Update();
            update.set("configurations.$", configuration);
            mongoTemplate.updateFirst(query, update, User.class);
        }
        else {
            throw new ResourceNotFoundException("Configuration with this name does not exist!");
        }

        return user.get();

    }

    public User deleteConfiguration(String userId, String fileName) throws ResourceNotFoundException {

        Optional<User> user = findUserByUserId(userId);

        if(!user.isPresent()) {
            throw new ResourceNotFoundException("User not found!");
        }

        List<String> fileNames = getConfigurationFileNames(user.get().getUserId());

        if(fileNames.contains(fileName)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("userId").is(user.get().getUserId())
                    .and("configurations.fileName").is(fileName));
            Update update = new Update();
            update.pull("configurations", new BasicDBObject("fileName", fileName));
            mongoTemplate.updateFirst(query, update, User.class);
        }
        else {
            throw new ResourceNotFoundException("Configuration with this name does not exist!");
        }

        return user.get();
    }

}
