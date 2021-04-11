package com.haumea.gitanalyzer.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {

    public double roundScore(double score) {

        BigDecimal roundedScore = new BigDecimal(Double.toString(score));
        roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

        return roundedScore.doubleValue();
    }

}
