package com.haumea.gitanalyzer.model;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Reports")
public class Report {

    @Id
    private String id;

    public Report() {super();}

    @PersistenceConstructor
    public Report(String id) {
        this.id = id;
    }
}
