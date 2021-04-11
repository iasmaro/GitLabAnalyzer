package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class ReportMetadataDTO {

    private String reportName;
    private String projectName;
    private Date start;
    private Date end;
    private String creator;
    private String configName;
    private int projectId;

    public ReportMetadataDTO(String reportName, String projectName, Date start, Date end, String creator, String configName, int projectId) {
        this.reportName = reportName;
        this.projectName = projectName;
        this.start = start;
        this.end = end;
        this.creator = creator;
        this.configName = configName;
        this.projectId = projectId;
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

    public String getConfigName() {
        return configName;
    }

    public int getProjectId() {
        return projectId;
    }


}
