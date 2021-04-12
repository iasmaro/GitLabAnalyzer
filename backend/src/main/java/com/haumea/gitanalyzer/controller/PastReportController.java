package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.ReportMetadataDTO;
import com.haumea.gitanalyzer.model.Report;
import com.haumea.gitanalyzer.service.ReportService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/PastReports")
public class PastReportController {

    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public PastReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;

        this.userService = userService;
    }

    @GetMapping("/getReport")
    public Report getReport(@RequestParam @NotBlank String reportName)  {

       return reportService.checkIfInDbViaName(reportName);
    }

    @GetMapping("/allReports")
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    @DeleteMapping("/deleteReport")
    public void deleteReport(@RequestParam @NotBlank String reportName) {

        Report report = reportService.checkIfInDbViaName(reportName);
        reportService.deleteReport(reportName);

        for(String user : report.getUserList()) {
            userService.deleteReport(user, reportName);
        }
    }

    @PutMapping("/addReportAccess")
    public void addReportAccess(@RequestParam @NotBlank String userId, @RequestParam @NotBlank String reportName) {
        reportService.giveUserAccessToReport(userId, reportName);
        userService.addReport(userId, reportName);

    }

    @DeleteMapping("/revokeReportAccess")
    public void revokeReportAccess(@RequestParam @NotBlank String userId, @RequestParam @NotBlank String reportName) {
        userService.deleteReport(userId, reportName);
        reportService.revokeUserAccessToReport(userId, reportName);
    }

    @GetMapping("/userReports")
    public List<ReportMetadataDTO> getUserReports(@RequestParam @NotBlank String userId) {
        return reportService.getReportsForUser(userId);
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
