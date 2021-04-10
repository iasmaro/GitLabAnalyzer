package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.ReportDTO;
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
    public ReportDTO getRepositoryReport(@NotBlank @RequestParam String userId,
                                         @NotNull @RequestParam int projectId) {


        Optional<ReportDTO> databaseReport = reportService.checkIfInDb(userId, projectId);

        if(databaseReport.isPresent()) {
            return databaseReport.get();
        }
        else {
            ReportDTO report = reportService.getReportForRepository(userId, projectId);
            reportService.saveReport(report);
            userService.addReport(userId, report.getReportName());


            return report;
        }

    }

}
