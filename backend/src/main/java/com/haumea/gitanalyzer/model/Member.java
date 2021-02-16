package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "member")
public class Member {

    @Id
    private String id;
    private String memberId;
    private List<String> alias;

    public Member() {
        super();
    }

    @PersistenceConstructor
    public Member(String id, String memberId, List<String> alias) {
        this.id = id;
        this.memberId = memberId;
        this.alias = alias;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<String> getAlias() {
        return alias;
    }
}
