package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.model.ReportDTO;
import com.haumea.gitanalyzer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/reports")
@Validated
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {

        this.reportService = reportService;
    }

    @GetMapping("updateCommitGraph")
    public ReportDTO getRepositoryReport(@NotBlank @RequestParam String userId,
                                         @NotNull @RequestParam int projectId) {


        Optional<ReportDTO> databaseReport = reportService.checkIfInDb(userId, projectId);

        if(databaseReport.isPresent()) {
            return databaseReport.get();
        }
        else {
            ReportDTO report = reportService.getReportForRepository(userId, projectId);
            reportService.saveReport(report);

            return report;
        }

    }

    @PutMapping("updateCommitGraph")
    public void updateCommitGraph(@NotBlank @RequestParam String reportName,
                                  @NotBlank @RequestParam String memberId,
                                  @NotNull @RequestParam int commitGraphDTOIndex,
                                  @NotNull @RequestParam double oldScore,
                                  @NotNull @RequestParam double difference) {

        reportService.updateCommitGraph(reportName, memberId, commitGraphDTOIndex, oldScore, difference);
    }

    @PutMapping("updateMRGraph")
    public void updateMR(@NotBlank @RequestParam String reportName,
                         @NotBlank @RequestParam String memberId,
                         @NotNull @RequestParam int MRGraphDTOIndex,
                         @NotNull @RequestParam double oldScore,
                         @NotNull @RequestParam double difference) {

        reportService.updateMRGraph(reportName, memberId, MRGraphDTOIndex, oldScore, difference);
    }


}
