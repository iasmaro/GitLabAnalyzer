package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class CodeReviewGraphDTO {
    private Date date;
    private int wordsPerDay;
    private int wordsPerDayOnOwn;
    private int wordsPerDayOnOtherMergeRequests;

    public CodeReviewGraphDTO(Date date, int wordsPerDay, int wordsPerDayOnOwn, int wordsPerDayOnOtherMergeRequests) {
        this.date = date;
        this.wordsPerDay = wordsPerDay;
        this.wordsPerDayOnOwn = wordsPerDayOnOwn;
        this.wordsPerDayOnOtherMergeRequests = wordsPerDayOnOtherMergeRequests;
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

    public int getWordsPerDayOnOtherMergeRequests() {
        return wordsPerDayOnOtherMergeRequests;
    }

}
