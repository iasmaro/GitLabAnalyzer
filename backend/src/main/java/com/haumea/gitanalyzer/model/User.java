package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {

    @Id
    String id;
    String userName;
    String password;
    String access_token;

    public User(String id, String userName, String password, String access_token) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.access_token = access_token;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getAccess_token() {
        return access_token;
    }
}
