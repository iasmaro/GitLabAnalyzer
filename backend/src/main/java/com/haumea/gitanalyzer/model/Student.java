package com.haumea.gitanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "student")
public class Student {

    @Id
    private String id;
    private String name;
    private String email;
    private String commitData;

    public Student(String id, String name, String email, String commitData) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.commitData = commitData;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCommitData() {
        return commitData;
    }
}
