package com.haumea.gitanalyzer.controller;

import com.haumea.gitanalyzer.dto.CommitDTO;
import com.haumea.gitanalyzer.dto.ReportDTO;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.model.Configuration;
import com.haumea.gitanalyzer.service.ReportService;
import com.haumea.gitanalyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/PastReports")
public class PastReportController {

    private final ReportService reportService;
    private final UserService userService;

    private int idNumber;

    @Autowired
    public PastReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
        idNumber = 0;
    }


    @GetMapping("/testdb")
    public String testDB() throws ParseException {
        idNumber++;

        GitlabService gitlabService = new GitlabService("https://csil-git1.cs.surrey.sfu.ca/", "gYLtys_E24PNBWmG_i86");
        List<String> aliases = new ArrayList<>();
        aliases.add("Andrew Ursu");

        Configuration active = userService.getConfiguration("aursu");
        Date end = new Date();


       List<CommitWrapper> wrappers = gitlabService.getFilteredCommitsWithDiffByAuthor(27200, "master",
                userService.getStart("aursu"),
                userService.getEnd("aursu"),
                      aliases);
       List<CommitDTO> DTOS = new ArrayList<>();

       for(CommitWrapper current : wrappers) {

           DTOS.add(new CommitDTO(current.getCommitData().getMessage(),
                   current.getCommitData().getCommittedDate(),
                   current.getCommitData().getAuthorName(),
                   current.getCommitData().getWebUrl(),
                   99.90,
                   null,
                   null,
                   1000,
                   1001));
       }

        Map<String, List<CommitDTO>> commitListByMemberId = new HashMap<>();
       commitListByMemberId.put("aursu", DTOS);


        ReportDTO reportDTO = new ReportDTO(null, commitListByMemberId, null,
                null, null, idNumber);

        reportService.saveReport(reportDTO);
        return "test";
    }
}
