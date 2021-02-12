package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "member")
public class Member {

    @Id
    private String id;
    private String memberId;
    private ArrayList<String> alias;

    public Member(String id, String memberId, ArrayList<String> alias) {
        this.id = id;
        this.memberId = memberId;
        this.alias = alias;
    }

    public String getMemberId() {
        return memberId;
    }

    public ArrayList<String> getAlias() {
        return alias;
    }
}
