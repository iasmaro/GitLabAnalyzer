package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.model.Report;
import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.MergeRequestGraphDTO;
import com.haumea.gitanalyzer.model.Member;
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

    public void saveReportToDatabase(Report newReport) {
        mongoTemplate.save(newReport);

    }

    public Optional<Report> findReportInDb(int projectId, Date start, Date end, String configName) {

        Query query = setQuery(projectId, start, end, configName);

        Report databaseReport = mongoTemplate.findOne(query, Report.class);

        return Optional.ofNullable(databaseReport);

    }

    public Optional<Report> findReportInDbViaName(String reportName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        Report databaseReport = mongoTemplate.findOne(query, Report.class);

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

    public List<Report> getAllReportsInDb() {
        return mongoTemplate.findAll(Report.class);
    }


    public void deleteReportDTO(String reportName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        mongoTemplate.findAndRemove(query, Report.class);
    }

    public void giveUserAccess(String userId, String reportName) {
        Report report = findReportInDbViaName(reportName).orElseThrow(() -> new ResourceNotFoundException("Report not found in Database"));
        List<String> userList = report.getUserList();

        if(userList.contains(userId)) {
            throw new IllegalArgumentException("User already has access");
        }
        else {
            userList.add(userId);
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(report.getReportName()));
        Update update = new Update();

        update.set("userList", userList);

        if(mongoTemplate.findAndModify(query, update, Report.class) == null) {
            throw new ResourceNotFoundException("User access not given");
        }
    }

    public void revokeUserAccess(String userId, String reportName) {
        Report report = findReportInDbViaName(reportName).orElseThrow(() -> new ResourceNotFoundException("Report not found in Database"));
        List<String> userList = report.getUserList();

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(report.getReportName()));
        Update update = new Update();

        if(userList.remove(userId)) {
            update.set("userList", userList);
        }
        else {
            throw new ResourceNotFoundException("User doesn't exist in report access list");
        }

        if(mongoTemplate.findAndModify(query, update, Report.class) == null) {
            throw new ResourceNotFoundException("User access not revoked");
        }
    }

    // taken from: https://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-java
    public static long betweenDates(Date firstDate, Date secondDate) {
        return ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    }

    public void updateCommitGraph(String reportName, String memberId, Date commitDate, Date start, double oldScore, double difference) {

        long commitGraphDTOIndex = betweenDates(start, commitDate);

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        double newScore = oldScore + difference;
        newScore = Math.round(newScore * 10.0) / 10.0;

        Update update = new Update();
        update.set("commitGraphListByMemberId."
                        + memberId
                        + "."
                        + commitGraphDTOIndex
                        + ".totalCommitScore",
                        newScore);

        mongoTemplate.updateFirst(query, update, Report.class);


    }

    public void updateMRGraph(String reportName, String memberId, Date MRDate, Date start, double oldScore, double difference) {

        long MRGraphDTOIndex = betweenDates(start, MRDate);

        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        double newScore = oldScore + difference;
        newScore = Math.round(newScore * 10.0) / 10.0;

        Update update = new Update();
        update.set("MRGraphListByMemberId."
                        + memberId
                        + "."
                        + MRGraphDTOIndex
                        + ".totalMergeRequestScore",
                        newScore);

        mongoTemplate.updateFirst(query, update, Report.class);

    }
}
