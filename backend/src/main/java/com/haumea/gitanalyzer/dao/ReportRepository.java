package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.MergeRequestGraphDTO;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.ReportDTO;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class ReportRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReportRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveReportToDatabase(ReportDTO newReport) {
        mongoTemplate.save(newReport);

    }

    public Optional<ReportDTO> findReportInDb(int projectId, Date start, Date end, String configName) {

        Query query = setQuery(projectId, start, end, configName);

        ReportDTO databaseReport = mongoTemplate.findOne(query, ReportDTO.class);

        return Optional.ofNullable(databaseReport);

    }

    public Optional<ReportDTO> findReportInDbViaName(String reportName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        ReportDTO databaseReport = mongoTemplate.findOne(query, ReportDTO.class);

        return Optional.ofNullable(databaseReport);
    }

    private Query setQuery(int projectId, Date start, Date end, String configName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        query.addCriteria(Criteria.where("start").is(start));
        query.addCriteria(Criteria.where("end").is(end));
        query.addCriteria(Criteria.where("configName").is(configName));

        return query;
    }

    public List<ReportDTO> getAllReportsInDb() {
        return mongoTemplate.findAll(ReportDTO.class);
    }


    public void deleteReportDTO(String reportName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        mongoTemplate.findAndRemove(query, ReportDTO.class);

    }

    // taken from: https://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-java
    public static long betweenDates(Date firstDate, Date secondDate) {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    public void updateCommitGraph(String reportName, String memberId, Date commitDate, Date start, double oldScore, double difference) {

        long commitGraphDTOIndex = betweenDates(start, commitDate);

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        Update update = new Update();
        update.set("commitGraphListByMemberId."
                        + memberId
                        + "."
                        + commitGraphDTOIndex
                        + ".totalCommitScore",
                oldScore + difference);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);


    }

    public void updateMRGraph(String reportName, String memberId, Date MRDate, Date start, double oldScore, double difference) {

        long MRGraphDTOIndex = betweenDates(start, MRDate);

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        Update update = new Update();
        update.set("MRGraphListByMemberId."
                        + memberId
                        + "."
                        + MRGraphDTOIndex
                        + ".totalMergeRequestScore",
                oldScore + difference);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);

    }
}
