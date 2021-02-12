package com.haumea.gitanalyzer.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String userId;
    private String personalAccessToken;

    @PersistenceConstructor
    public User(String userId, String personalAccessToken) {
        this.userId = userId;
        this.personalAccessToken = personalAccessToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }
}
