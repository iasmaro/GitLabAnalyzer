package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/api/v1/reports")
@Validated
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {

        this.reportService = reportService;
    }

    @GetMapping
    public ReportDTO getRepositoryReport(@NotBlank @RequestParam String userId,
                                         @NotNull @RequestParam int projectId) {

        return reportService.getReportForRepository(userId, projectId);
    }
}
