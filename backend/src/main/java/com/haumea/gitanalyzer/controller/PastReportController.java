package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.service.ReportService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

@RestController
@RequestMapping(path = "/api/v1/PastReports")
public class PastReportController {

    private final ReportService reportService;


    @Autowired
    public PastReportController(ReportService reportService) {
        this.reportService = reportService;

    }


    @GetMapping("/testdb")
    public ReportDTO testDB(@RequestParam @NotBlank String userId,
                            @RequestParam @NotNull int projectId) throws ParseException {

//        reportService.saveReport(reportService.getReportForRepository(userId, projectId));

        return reportService.checkIfInDb(userId, projectId);

    }
}
