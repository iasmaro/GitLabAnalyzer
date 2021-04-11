package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.ReportDTO;
import com.haumea.gitanalyzer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/PastReports")
public class PastReportController {

    private final ReportService reportService;

    @Autowired
    public PastReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @GetMapping("/getReport")
    public ReportDTO getReport(@RequestParam @NotBlank String reportName)  {

        Optional<ReportDTO> databaseReport = reportService.checkIfInDbViaName(reportName);
        ReportDTO report;

        try {
            report = databaseReport.get();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("Report is not in database");
        }

        return report;
    }

    // TODO: Once reports are linked to users make it return all the reports for a user
    // returns whats in the database
    @GetMapping("/allReports")
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }

    @DeleteMapping("/deleteReport")
    public void deleteReport(@RequestParam @NotBlank String reportName) {

        reportService.deleteReport(reportName);

    }

    @PutMapping("/updateScore/mr/mrDiff")
    public void updateScoreForMRDiff(@RequestParam @NotBlank String reportName,
                                     @RequestParam @NotBlank String memberId,
                                     @RequestParam @NotNull int mergeIndex,
                                     @RequestParam @NotNull int diffIndex,
                                     @RequestParam @NotNull double newScore) {

        reportService.modifyDiffScoreOfMRDiff(reportName, memberId, mergeIndex, diffIndex, newScore);

    }

    @PutMapping("/updateScore/mr/commitDiff")
    public void updateScoreForCommitDiffInOneMR(@RequestParam @NotBlank String reportName,
                                                @RequestParam @NotBlank String memberId,
                                                @RequestParam @NotNull int mergeIndex,
                                                @RequestParam @NotNull int diffIndex,
                                                @RequestParam @NotNull int commitIndex,
                                                @RequestParam @NotNull double newScore) {

        reportService.modifyDiffScoreOfCommitInOneMR(reportName, memberId, mergeIndex, commitIndex, diffIndex, newScore);

    }

    @PutMapping("updateScore/commit/commitDiff")
    public void updateScoreForCommitDiff(@RequestParam @NotBlank String reportName,
                                         @RequestParam @NotBlank String memberId,
                                         @RequestParam @NotNull int commitIndex,
                                         @RequestParam @NotNull int diffIndex,
                                         @RequestParam @NotNull double newScore) {

        reportService.modifyDiffScoreOfCommit(reportName, memberId, commitIndex, diffIndex, newScore);

    }
}
