package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/PastReports")
public class PastReportController {

    private final ReportService reportService;

    private int idNumber;

    @Autowired
    public PastReportController(ReportService reportService) {
        this.reportService = reportService;
        idNumber = 0;
    }


    @GetMapping("/testdb")
    public String testDB() {
        idNumber++;

        GitlabService gitlabService = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");



        ReportDTO reportDTO = new ReportDTO(null,null, null,
                null, null, idNumber);

        reportService.saveReport(reportDTO);
        return "test";
    }
}
