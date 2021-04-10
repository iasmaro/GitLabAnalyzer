package com.haumea.gitanalyzer.dao;

import com.haumea.gitanalyzer.dto.DiffDTO;
import com.haumea.gitanalyzer.dto.MergeRequestDTO;
import com.haumea.gitanalyzer.dto.ScoreDTO;
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

    //Overriding score section
    private void modifyDiffScoreInDB(String reportName, String memberId, int mergeIndex, int diffIndex, double newDiffScore) {

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

    private DiffDTO getModifiedDiff(MergeRequestDTO modifiedMR, int diffIndex) {
        return modifiedMR.getMergeRequestDiffs().get(diffIndex);
    }

    private double getDiffScoreOfMR(DiffDTO modifiedDiff) {
        return modifiedDiff.getScoreDTO().getScore();
    }

    private double getNewScoresDifference(DiffDTO modifiedDiff, double newDiffScore) {

        double modifiedDiffScore = modifiedDiff.getScoreDTO().getModifiedScore();

        double originalDiffScore = (modifiedDiffScore != -1)?modifiedDiffScore:getDiffScoreOfMR(modifiedDiff);

        return (newDiffScore - originalDiffScore);
    }

    private double getNewMRScore(MergeRequestDTO modifiedMR, double difference) {
        double MRScore = modifiedMR.getMRScore();

        MRScore = MRScore + difference;

        ScoreDTO roundObject = new ScoreDTO();

        return roundObject.roundScore(MRScore);
    }

    private void updateMRScoreInDB(String reportName, String memberId, int mergeIndex, double newMRScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = "mergeRequestListByMemberId."
                + memberId
                + "."
                + mergeIndex
                + ".MRScore";

        Update update = new Update();
        update.set(MRScorePath, newMRScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    private double getExtensionScore(MergeRequestDTO modifiedMR, String extension) {
        return modifiedMR.getScoreByFileTypes().getOrDefault(extension, 0.0);
    }

    private double getNewExtensionScore(MergeRequestDTO modifiedMR, String extension, double difference) {
        double extensionScore = getExtensionScore(modifiedMR, extension);

        extensionScore = extensionScore + difference;

        ScoreDTO roundObject = new ScoreDTO();

        return roundObject.roundScore(extensionScore);
    }

    private void updateExtensionScoreInDB(String reportName, String memberId, int mergeIndex, String extension, double newExtensionScore) {
        Query query = getReportNameQuery(reportName);

        String MRScorePath = "mergeRequestListByMemberId."
                + memberId
                + "."
                + mergeIndex
                + ".scoreByFileTypes"
                + "."
                + extension;

        Update update = new Update();
        update.set(MRScorePath, newExtensionScore);

        mongoTemplate.updateFirst(query, update, ReportDTO.class);
    }

    public void updateDBWithNewDiffSCoreOfMR(String reportName, String memberId, int mergeIndex, int diffIndex, double newDiffScore) {

        Optional<ReportDTO> OptionalReportDTO = findReportInDbViaName(reportName);

        if(OptionalReportDTO.isPresent()) {

            modifyDiffScoreInDB(reportName, memberId, mergeIndex, diffIndex, newDiffScore);

            ReportDTO modifiedReport = OptionalReportDTO.get();
            MergeRequestDTO modifiedMR = getModifiedMergeRequestByMemberId(modifiedReport, memberId, mergeIndex);
            DiffDTO modifiedDiff = getModifiedDiff(modifiedMR, diffIndex);

            double newScoreDifference = getNewScoresDifference(modifiedDiff, newDiffScore);

            double newMRScore = getNewMRScore(modifiedMR, newScoreDifference);

            updateMRScoreInDB(reportName, memberId, mergeIndex, newMRScore);

            String modifiedExtension = modifiedDiff.getExtension();
            double newExtensionScore = getNewExtensionScore(modifiedMR, modifiedExtension, newScoreDifference);

            updateExtensionScoreInDB(reportName, memberId, mergeIndex, modifiedExtension, newExtensionScore);
        }
        else {
            throw new NoSuchElementException("Report is not found!");
        }

    }
}
