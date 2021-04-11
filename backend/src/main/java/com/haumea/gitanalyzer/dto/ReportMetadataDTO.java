package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class ReportMetadataDTO {

    private String reportName;
    private String projectName;
    private Date start;
    private Date end;
    private String creator;

    public ReportMetadataDTO(String reportName, String projectName, Date start, Date end, String creator) {
        this.reportName = reportName;
        this.projectName = projectName;
        this.start = start;
        this.end = end;
        this.creator = creator;
    }

    public String getReportName() {
        return reportName;
    }

    public String getProjectName() {
        return projectName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getCreator() {
        return creator;
    }

}
