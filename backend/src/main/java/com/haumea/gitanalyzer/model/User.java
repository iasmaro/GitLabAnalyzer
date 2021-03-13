package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "user")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @NotBlank
    private String userId;
    private String personalAccessToken;
    private List<Configuration> configurations;
    private String gitlabServer;
    private String activeConfig;

    public User() {
        super();
    }

    @PersistenceConstructor
    public User(String userId, String personalAccessToken, String gitlabServer, String activeConfig) {
        this.userId = userId;
        this.personalAccessToken = personalAccessToken;
        this.configurations = new ArrayList<>();
        this.gitlabServer = gitlabServer;
        this.activeConfig = activeConfig;
    }

    public String getUserId() {
        return userId;
    }

    public String getPersonalAccessToken() {
        return personalAccessToken;
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public String getGitlabServer() { return gitlabServer; }

    public String getActiveConfig() { return activeConfig; }
}
