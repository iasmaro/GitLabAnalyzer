package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.Report;
import com.haumea.gitanalyzer.service.ReportService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/reports")
@Validated
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    @Autowired
    public ReportController(ReportService reportService, UserService userService) {

        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping
    public Report getRepositoryReport(@NotBlank @RequestParam String userId,
                                      @NotNull @RequestParam int projectId) {


        Optional<Report> databaseReport = reportService.checkIfInDb(userId, projectId);

        if(databaseReport.isPresent()) {
            return databaseReport.get();
        }
        else {
            Report report = reportService.getReportForRepository(userId, projectId);
            reportService.saveReport(report);

            for(String currentUser : report.getUserList()) {
                userService.addReport(currentUser, report.getReportName());
            }


            return report;
        }

    }

}
