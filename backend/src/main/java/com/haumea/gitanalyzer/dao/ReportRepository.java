package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.DiffDTO;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.model.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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

    private Query getReportNameQuery(String reportName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportName").is(reportName));

        return query;
    }

    public Optional<ReportDTO> findReportInDbViaName(String reportName) {
        Query query = getReportNameQuery(reportName);

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
        Query query = getReportNameQuery(reportName);

        mongoTemplate.findAndRemove(query, ReportDTO.class);

    }

    public void modifyDiffScoreInDB(String reportName, String memberId, int mergeIndex, int diffIndex, double newDiffScore) {

        Query query = getReportNameQuery(reportName);

        String diffScorePath = "mergeRequestListByMemberId."
                + memberId
                + "."
                + mergeIndex
                + ".mergeRequestDiffs."
                + diffIndex
                + ".scoreDTO.modifiedScore";

        Update update = new Update();
        update.set(diffScorePath, newDiffScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private MergeRequestDTO getModifiedMergeRequestByMemberId(ReportDTO reportDTO, String memberId, int mergeIndex) {
        return reportDTO.getMergeRequestListByMemberId().get(memberId).get(mergeIndex);
    }

    private double getDiffScoreOfMR(MergeRequestDTO modifiedMR, int diffIndex) {
        DiffDTO modifiedDiff = modifiedMR.getMergeRequestDiffs().get(diffIndex);

        return modifiedDiff.getScoreDTO().getScore();
    }

    private void updateMRScore(MergeRequestDTO modifiedMR, int diffIndex, double newDiffScore) {

        double MRScore = modifiedMR.getMRScore();
        double originalDiffScore = getDiffScoreOfMR(modifiedMR, diffIndex);
        double difference = newDiffScore - originalDiffScore;

        MRScore = MRScore + difference;

    }

    public void updateDBWithNewDiffSCoreOfMR(String reportName, String memberId, int mergeIndex, int diffIndex, double newDiffScore) {

        Optional<ReportDTO> OptionalReportDTO = findReportInDbViaName(reportName);

        if(OptionalReportDTO.isPresent()) {

            modifyDiffScoreInDB(reportName, memberId, mergeIndex, diffIndex, newDiffScore);

            ReportDTO modifiedReport = OptionalReportDTO.get();

            MergeRequestDTO modifiedMR = getModifiedMergeRequestByMemberId(modifiedReport, memberId, mergeIndex);

            updateMRScore(modifiedMR, diffIndex, newDiffScore);

        }
        else {
            throw new NoSuchElementException("Report is not found!");
        }

    }
}
