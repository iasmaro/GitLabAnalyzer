package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class IndividualDiffScoreCalculator {

    private Double addLineMultiplier;
    private Double deleteLineMultiplier;
    private Double syntaxLineMultiplier;
    private List<CommentType> commentTypes;


    public IndividualDiffScoreCalculator() {
    }

    private Double analyzeLine(String line) {
        Double lineScore = 0.0;

        line = line.trim();

        if(line.equals("+")) {
            System.out.println("line is a space");
        }
//        else if() {
//            // check for comments
//        }

        return lineScore;
    }

    private void setTypes( Double addLineMultiplier, Double deleteLineMultiplier, Double syntaxLineMultiplier,
                      List<CommentType> commentTypes) {
        this.addLineMultiplier = addLineMultiplier;
        this.deleteLineMultiplier = deleteLineMultiplier;
        this.syntaxLineMultiplier = syntaxLineMultiplier;
        this.commentTypes = commentTypes;
    }


    private Double analyzeDiff(String diff) throws IOException {

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

        return 1.0;
    }


    // check file type and configs in calling code
    public Double calculateDiffScore(String diff, boolean isFileDeleted, Double addLineMultiplier, Double deleteLineMultiplier, Double syntaxLineMultiplier,
                                     List<CommentType> commentTypes) {


        if(isFileDeleted == true) {
            return 0.0;
        }
        else {
            Double score;
            try {
                analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new GitLabRuntimeException("input error");
            }

            return 1.1;
        }
    }
}
