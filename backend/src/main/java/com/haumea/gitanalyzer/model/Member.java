package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "member")
public class Member {

    @Id
    private String id;
    private String username;
    private ArrayList<String> alias;

    public Member(String id, String username, ArrayList<String> alias) {
        this.id = id;
        this.username = username;
        this.alias = alias;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }
}
