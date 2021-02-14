package com.haumea.gitanalyzer.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @NotNull
    @NotBlank
    @NotEmpty
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
