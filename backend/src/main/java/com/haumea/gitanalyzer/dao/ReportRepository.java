package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReportRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveReportToDatabase(ReportDTO newReport) {

        mongoTemplate.save(newReport, "Reports");

    }
}
