package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReportRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ReportRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveReportToDatabase(ReportDTO newReport) {


//        Report newDbReport = convertDTOToReport(newReport);
//        modifyReport(newDbReport);

        findReportInDb(newReport);

//        mongoTemplate.save(newDbReport);

    }
    private Report convertDTOToReport(ReportDTO dtoReport) {
        Report convertedReport = new Report(
                                dtoReport.getProjectId(),
                                dtoReport.getStart(),
                                dtoReport.getEnd(),
                                dtoReport.getMergeRequestListByMemberId(),
                                dtoReport.getCommitListByMemberId(),
                                dtoReport.getMRCommentListByMemberId(),
                                dtoReport.getIssueCommentListByMemberId(),
                                dtoReport.getCommitGraphListByMemberId(),
                                dtoReport.getMRGraphListByMemberId(),
                                dtoReport.getCodeReviewGraphListByMemberId(),
                                dtoReport.getIssueGraphListByMemberId(),
                                dtoReport.getUserList());

        return convertedReport;
    }

    private void modifyReport(Report newReport) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(newReport.getProjectId()));
        query.addCriteria(Criteria.where("start").is(newReport.getStart()));
        query.addCriteria(Criteria.where("end").is(newReport.getEnd()));

        Report databaseReport = mongoTemplate.findOne(query, Report.class);

        if(databaseReport == null) {
            System.out.println("report is null");
        }


        if(Optional.ofNullable(databaseReport).isPresent()) {
            System.out.println("in database");
        }

//        if(areReportsSame(databaseReport, newReport)) {
//            System.out.println("The same");
//        }

    }

    private Optional<ReportDTO> findReportInDb(ReportDTO report) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(report.getProjectId()));
        query.addCriteria(Criteria.where("start").is(report.getStart()));
        query.addCriteria(Criteria.where("end").is(report.getEnd()));

        ReportDTO databaseReport = mongoTemplate.findOne(query, ReportDTO.class);

        if(Optional.ofNullable(databaseReport).isPresent()) {
            System.out.println("in database");
        }

        return Optional.ofNullable(databaseReport);

    }

    boolean areReportsSame(ReportDTO firstReport, ReportDTO secondReport) {
        return firstReport.getProjectId() == secondReport.getProjectId();
    }

}
