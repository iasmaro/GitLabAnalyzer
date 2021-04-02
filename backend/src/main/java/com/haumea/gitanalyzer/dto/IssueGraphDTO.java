package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class IssueGraphDTO {
    private Date date;
    private int wordsPerDay;
    private int wordsPerDayOnOwn;
    private int wordsPerDayOnOtherIssues;

    public IssueGraphDTO(Date date, int wordsPerDay, int wordsPerDayOnOwn, int wordsPerDayOnOtherIssues) {
        this.date = date;
        this.wordsPerDay = wordsPerDay;
        this.wordsPerDayOnOwn = wordsPerDayOnOwn;
        this.wordsPerDayOnOtherIssues = wordsPerDayOnOtherIssues;
    }

    public Date getDate() {
        return date;
    }

    public int getWordsPerDay() {
        return wordsPerDay;
    }

    public int getWordsPerDayOnOwn() {
        return wordsPerDayOnOwn;
    }

    public int getWordsPerDayOnOtherIssues() {
        return wordsPerDayOnOtherIssues;
    }
}
