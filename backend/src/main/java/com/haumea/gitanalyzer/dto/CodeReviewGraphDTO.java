package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class CodeReviewGraphDTO {
    private Date date;
    private int wordsPerDay;
    private int wordsPerDayOnOwn;
    private int wordsPerDayOnOthers;

    public CodeReviewGraphDTO(Date date, int wordsPerDay, int wordsPerDayOnOwn, int wordsPerDayOnOthers) {
        this.date = date;
        this.wordsPerDay = wordsPerDay;
        this.wordsPerDayOnOwn = wordsPerDayOnOwn;
        this.wordsPerDayOnOthers = wordsPerDayOnOthers;
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

    public int getWordsPerDayOnOthers() {
        return wordsPerDayOnOthers;
    }

}
