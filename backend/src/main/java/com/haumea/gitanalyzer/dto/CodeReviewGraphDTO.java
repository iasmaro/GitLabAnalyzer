package com.haumea.gitanalyzer.dto;

import java.util.Date;

public class CodeReviewGraphDTO {
    private Date date;
    private int wordsPerDay;

    public CodeReviewGraphDTO(Date date, int wordsPerDay) {
        this.date = date;
        this.wordsPerDay = wordsPerDay;
    }

    public Date getDate() {
        return date;
    }

    public int getWordsPerDay() {
        return wordsPerDay;
    }

}
