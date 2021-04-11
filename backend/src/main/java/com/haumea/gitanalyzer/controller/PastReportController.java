package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.ReportMetadataDTO;
import com.haumea.gitanalyzer.model.ReportDTO;
import com.haumea.gitanalyzer.service.ReportService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    public ReportDTO getReport(@RequestParam @NotBlank String reportName)  {

       return reportService.checkIfInDbViaName(reportName);
    }

    // TODO: Once reports are linked to users make it return all the reports for a user
    // returns whats in the database
    @GetMapping("/allReports")
    public List<ReportDTO> getAllReports() {
        return reportService.getAllReports();
    }

    @DeleteMapping("/deleteReport")
    public void deleteReport(@RequestParam @NotBlank String reportName) {

        ReportDTO report = reportService.checkIfInDbViaName(reportName);
        reportService.deleteReport(reportName);

        for(String user : report.getUserList()) {
            userService.deleteReport(user, reportName);
        }
    }

    // make sure to pass in a correct report name as it will not check the report DB to see if it exists
    @PutMapping("/addReportAccess")
    public void addReportAccess(@RequestParam @NotBlank String userId, @RequestParam @NotBlank String reportName) {
        userService.addReport(userId, reportName);
        reportService.giveUserAccessToReport(userId, reportName);
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
}
