package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.UserRepository;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) throws Exception{
        try {
            userRepository.saveUser(user);
            return user;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public User updateUser(User user) throws Exception{
        try {
            userRepository.updateUser(user);
            return user;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String getPersonalAccessToken(String userId) throws Exception{
        String token;

        try{
            token = userRepository.getPersonalAccessToken(userId);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return token;
    }
}
