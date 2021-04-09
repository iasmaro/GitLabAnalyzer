package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.CommitGraphDTO;
import com.haumea.gitanalyzer.dto.MergeRequestGraphDTO;
import com.haumea.gitanalyzer.model.Member;
import com.haumea.gitanalyzer.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public void findCommitGraphDTOInDb(String userId, int projectId, Date start, Date end, String configName, Date commitDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        query.addCriteria(Criteria.where("start").is(start));
        query.addCriteria(Criteria.where("end").is(end));
        query.addCriteria(Criteria.where("configName").is(configName));
    }

    public void updateCommitGraph(String userId, int projectId, Date start, Date end, String configName, Date commitDate, double difference) {

        if(findReportInDb(projectId, start, end, configName).isPresent()) {

            ReportDTO reportDTO = findReportInDb(projectId, start, end, configName).get();
            Map<String, List<CommitGraphDTO>> commitGraphs = reportDTO.getCommitGraphListByMemberId();
            List<CommitGraphDTO> commitGraph = commitGraphs.get(userId);

        }

    }

    public void findMRGraphDTOInDb(String userId, int projectId, Date start, Date end, String configName, Date commitDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("projectId").is(projectId));
        query.addCriteria(Criteria.where("start").is(start));
        query.addCriteria(Criteria.where("end").is(end));
        query.addCriteria(Criteria.where("configName").is(configName));
    }
    public void updateMRGraph(String userId, int projectId, Date start, Date end, String configName, Date commitDate, double difference) {

        if(findReportInDb(projectId, start, end, configName).isPresent()) {

            ReportDTO reportDTO = findReportInDb(projectId, start, end, configName).get();
            Map<String, List<MergeRequestGraphDTO>> MRGraphs = reportDTO.getMRGraphListByMemberId();
            List<MergeRequestGraphDTO> MRGraph = MRGraphs.get(userId);

        }

    }
}
