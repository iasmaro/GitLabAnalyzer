package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.Report;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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


//        Report newDbReport = convertDTOToReport(newReport);
//        findReportInDb(newDbReport);


        mongoTemplate.save(newReport);

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

    public Optional<ReportDTO> findReportInDb(int projectId, Date start, Date end) {

        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        query.addCriteria(Criteria.where("start").is(start));
        query.addCriteria(Criteria.where("end").is(end));

        ReportDTO databaseReport = mongoTemplate.findOne(query, ReportDTO.class);

        if(databaseReport == null) {
            System.out.println("report is null");
        }


        if(Optional.ofNullable(databaseReport).isPresent()) {
            System.out.println("in database");
        }

        return Optional.ofNullable(databaseReport);


    }


}
