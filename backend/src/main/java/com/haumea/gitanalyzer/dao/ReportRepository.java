package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.exception.ResourceNotFoundException;
import com.haumea.gitanalyzer.model.ReportDTO;
import com.haumea.gitanalyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public void giveUserAccess(String userId, String reportName) {
        ReportDTO report = findReportInDbViaName(reportName).orElseThrow(() -> new ResourceNotFoundException("Report not found in Database"));
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

        if(mongoTemplate.findAndModify(query, update, ReportDTO.class) == null) {
            throw new ResourceNotFoundException("User access not given");
        }
    }

    public void revokeUserAccess(String userId, String reportName) {
        ReportDTO report = findReportInDbViaName(reportName).orElseThrow(() -> new ResourceNotFoundException("Report not found in Database"));
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

        if(mongoTemplate.findAndModify(query, update, ReportDTO.class) == null) {
            throw new ResourceNotFoundException("User access not revoked");
        }
    }


}
