package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "member")
public class Member {

    @Id
    private String id;
    private String userId;
    private ArrayList<String> alias;

    public Member(String id, String userId, ArrayList<String> alias) {
        this.id = id;
        this.userId = userId;
        this.alias = alias;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }
}
