package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.CommitDTO;
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
import java.time.temporal.ChronoUnit;

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

        mongoTemplate.updateFirst(query, update, ReportDTO.class);


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

        mongoTemplate.updateFirst(query, update, ReportDTO.class);

    }

    public MergeRequestDTO getModifiedMergeRequestByMemberId(String reportName, String memberId, int mergeIndex) {

        Optional<ReportDTO> reportDTO = findReportInDbViaName(reportName);

        if(reportDTO.isPresent()) {
            ReportDTO modifiedReport = reportDTO.get();

            return modifiedReport.getMergeRequestListByMemberId().get(memberId).get(mergeIndex);
        }
        else {
            throw new NoSuchElementException("Report is not found!");
        }
    }

    public CommitDTO getModifiedCommitByMemberId(String reportName, String memberId, int commitIndex) {
        Optional<ReportDTO> reportDTO = findReportInDbViaName(reportName);

        if(reportDTO.isPresent()) {
            ReportDTO modifiedReport = reportDTO.get();

            return modifiedReport.getCommitListByMemberId().get(memberId).get(commitIndex);
        }
        else {
            throw new NoSuchElementException("Report is not found!");
        }
    }

    private String getPathToModifiedMR(String memberId, int mergeIndex) {
        return ("mergeRequestListByMemberId."
                + memberId
                + "."
                + mergeIndex);
    }

    private void updateDiffScoreOfMRDiff(String reportName, String mrPath, int diffIndex, double newDiffScore) {
        Query query = getReportNameQuery(reportName);

        String diffScorePath = mrPath
                + ".mergeRequestDiffs."
                + diffIndex
                + ".scoreDTO.modifiedScore";

        Update update = new Update();
        update.set(diffScorePath, newDiffScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private void updateMRScore(String reportName, String mrPath, double newMRScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = mrPath
                + ".MRScore";

        Update update = new Update();
        update.set(MRScorePath, newMRScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private void updateExtensionScoreOfMR(String reportName, String mrPath, String extension, double newExtensionScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = mrPath
                + ".scoreByFileTypes"
                + "."
                + extension;

        Update update = new Update();
        update.set(MRScorePath, newExtensionScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    public void updateDBWithNewDiffScoreOfMR(String reportName,
                                             String memberId,
                                             int mergeIndex,
                                             int diffIndex,
                                             double newDiffScore,
                                             double newMRScore,
                                             String modifiedExtension,
                                             double newExtensionScore) {

        String mrPath = getPathToModifiedMR(memberId, mergeIndex);

        updateDiffScoreOfMRDiff(reportName, mrPath, diffIndex, newDiffScore);

        updateMRScore(reportName, mrPath, newMRScore);

        updateExtensionScoreOfMR(reportName, mrPath, modifiedExtension, newExtensionScore);

    }

    private void updateDiffScoreOfCommitDiff(String reportName, String path, int diffIndex, double newDiffScore) {
        Query query = getReportNameQuery(reportName);

        String diffScorePath = path
                + ".commitDiffs."
                + diffIndex
                + ".scoreDTO.modifiedScore";

        Update update = new Update();
        update.set(diffScorePath, newDiffScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private void updateCommitScore(String reportName, String path, double newCommitScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = path + ".commitScore";

        Update update = new Update();
        update.set(MRScorePath, newCommitScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private void updateExtensionScoreOfCommit(String reportName, String path, String extension, double newExtensionScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = path
                + ".scoreByFileTypes"
                + "."
                + extension;

        Update update = new Update();
        update.set(MRScorePath, newExtensionScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private void updateSumOfCommitScore(String reportName, String mrPath, double newSumOfCommitScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = mrPath
                + ".sumOfCommitScore";

        Update update = new Update();
        update.set(MRScorePath, newSumOfCommitScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    public void updateSumOfCommitScoreOnSharedMR(String reportName, String memberId, int mergeIndex, double newSumOfCommitScore) {
        String mrPath = getPathToModifiedMR(memberId, mergeIndex);

        Query query = getReportNameQuery(reportName);

        String MRScorePath = mrPath
                + ".sumOfCommitScoreOnSharedMR";

        Update update = new Update();
        update.set(MRScorePath, newSumOfCommitScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    public void updateDBWithNewDiffScoreOfOneCommitInMR(String reportName,
                                                        String memberId,
                                                        int mergeIndex,
                                                        int commitIndex,
                                                        int diffIndex,
                                                        double newDiffScore,
                                                        double newCommitScore,
                                                        String modifiedExtension,
                                                        double newExtensionScore,
                                                        double newSumOfCommitScore) {

        String mrPath = getPathToModifiedMR(memberId, mergeIndex);
        String commitPath = mrPath + ".commitDTOList." + commitIndex;

        updateDiffScoreOfCommitDiff(reportName, commitPath, diffIndex, newDiffScore);

        updateCommitScore(reportName, commitPath + ".commitDTOList", newCommitScore);

        updateExtensionScoreOfCommit(reportName, commitPath, modifiedExtension, newExtensionScore);

        updateSumOfCommitScore(reportName, mrPath, newSumOfCommitScore);
    }

    public void updateDBWithNewDiffScoreOfCommit(String reportName,
                                                 String memberId,
                                                 int commitIndex,
                                                 int diffIndex,
                                                 double newDiffScore,
                                                 double newCommitScore,
                                                 String modifiedExtension,
                                                 double newExtensionScore) {

        String commitPath = "commitListByMemberId." + memberId + "." + commitIndex;

        updateDiffScoreOfCommitDiff(reportName, commitPath, diffIndex, newDiffScore);

        updateCommitScore(reportName, commitPath, newCommitScore);

        updateExtensionScoreOfCommit(reportName, commitPath, modifiedExtension, newExtensionScore);
    }
}
