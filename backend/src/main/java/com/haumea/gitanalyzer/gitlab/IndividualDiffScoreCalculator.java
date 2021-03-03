package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class IndividualDiffScoreCalculator {
    public IndividualDiffScoreCalculator() {
    }

    private int analyzeLine(String line) {
        int lineScore = 0;

        line = line.trim();

        if(line.equals("+")) {
            System.out.println("line is a space");
        }
        else {
            // check for comments
        }

        return lineScore;
    }

    private int analyzeDiff(String diff) throws IOException {

        // https://stackoverflow.com/questions/9259411/what-is-the-best-way-to-iterate-over-the-lines-of-a-java-string
        BufferedReader bufReader = new BufferedReader(new StringReader(diff));
        String line;
        while((line=bufReader.readLine()) != null )
        {
            if(line.charAt(0) == '+') {
                System.out.println("line is: " + line);
                analyzeLine(line);

            }
        }

        return 1;
    }


    // check file type and configs in calling code
    public int calculateDiffScore(String diff, boolean isFileDeleted) {
        if(isFileDeleted == true) {
            return 0;
        }
        else {
            try {
                analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new GitLabRuntimeException("input error");
            }

            return 1;
        }
    }
}
