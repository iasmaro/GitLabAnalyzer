package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class IndividualDiffScoreCalculator {

    private double addLineWeight;
    private double deleteLineWeight;
    private double syntaxLineWeight;
    private List<CommentType> commentTypes;

    private boolean isLongComment; /* need to save state between line calls as a comment could go
                                                   over multiple lines like this comment*/
    private String longCommentEndBrace;

    private List<String> removedLines;
    private List<String> addedLines;


    public IndividualDiffScoreCalculator() {

        isLongComment = false;

        removedLines = new ArrayList<>();
        addedLines = new ArrayList<>();
    }


    private void setTypes( Double addLineMultiplier, Double deleteLineMultiplier, Double syntaxLineMultiplier,
                      List<CommentType> commentTypes) {
        this.addLineWeight = addLineMultiplier;
        this.deleteLineWeight = deleteLineMultiplier;
        this.syntaxLineWeight = syntaxLineMultiplier;
        this.commentTypes = commentTypes;
    }

    // check file type and configs in calling code
    public double calculateDiffScore(String diff, boolean isFileDeleted, double addLineWeight, double deleteLineWeight, double syntaxLineWeight,
                                     List<CommentType> commentTypes) {

        setTypes(addLineWeight, deleteLineWeight, syntaxLineWeight, commentTypes);

        if(isFileDeleted) {
            return 0.0;
        }
        else {
            double score;
            try {
                score = analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new GitLabRuntimeException("input error");
            }

            BigDecimal roundedScore = new BigDecimal(Double.toString(score));
            roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

            return roundedScore.doubleValue();
        }
    }


    private double analyzeDiff(String diff) throws IOException {

        double diffScore = 0.0;

        // https://stackoverflow.com/questions/9259411/what-is-the-best-way-to-iterate-over-the-lines-of-a-java-string
        BufferedReader bufReader = new BufferedReader(new StringReader(diff));
        String line;
        while((line=bufReader.readLine()) != null )
        {
            String originalLine = line;

            if(line.charAt(0) == '+') {
                diffScore = diffScore + analyzeLine(line);
            }
            else if(line.charAt(0) == '-' && lineWasAdded(line) == false && (line.trim().length() > 0) && !line.trim().equals("-")) {
                diffScore = diffScore + deleteLineWeight;

                line = line.substring(1); // cutting out the -
                line = line.trim();


                removedLines.add(line);

            }
        }


        return diffScore;
    }
    private double analyzeLine(String line) {
        double lineScore = 0.0;

        line = line.trim();

        if(line.equals("+")) {

        }
        else if(line.length() > 1) {
            line = line.substring(1); // cutting out the +
            line = line.trim();

            if(isSyntax(line)) {
                lineScore = syntaxLineWeight;
            }
            else if(isComment(line)) {
                lineScore = 0.0;
            }
            else if(isLongComment == false && lineWasRemoved(line) == false) {
                lineScore = addLineWeight;

                addedLines.add(line);
            }

            if(isLongComment == true) {
                checkForEndBrace(line);

            }


        }

        return lineScore;
    }

    private boolean lineWasRemoved(String line) {
        return removedLines.contains(line);
    }
    private boolean lineWasAdded(String line) {
        return removedLines.contains(line);
    }

    private void checkForEndBrace(String line) {
        String potentialEndOfComment = "";

        if(line.length() > longCommentEndBrace.length()) {

            potentialEndOfComment = line.substring(line.length()-longCommentEndBrace.length(), line.length());
        }
        else if(line.length() == longCommentEndBrace.length()) {
            potentialEndOfComment = line;

        }

        if(potentialEndOfComment.equals(longCommentEndBrace)) {
            isLongComment = false;

        }


    }


    private boolean isSyntax(String line) {
        return line.equals("{") || line.equals("}");
    }

    private boolean isComment(String line) {

        boolean result = true;


        /* dont worry about efficiency as there are likely to be at most 2 types of comments in this list.
           The list passed in will be based on file type
         */
        for(CommentType commentType : commentTypes) {
            if(commentType.getEndType().equals("")) {
                result = isShortComment(line, commentType);

            }
            else {

                result = isLongComment(line, commentType);
            }
        }

        return result;
    }

    private boolean isLongComment(String line, CommentType commentType) {
        boolean result = true;

        for(int i=0; i<commentType.getStartType().length(); i++) {
            if (line.charAt(i) != commentType.getStartType().charAt(i)) {
                result = false;
                break;
            }
        }

        if(result == true) {
            isLongComment = true;
            longCommentEndBrace = commentType.getEndType();
        }

        return result;
    }

    private boolean isShortComment(String line, CommentType commentType) {
        boolean result = true;
        for(int i=0; i<commentType.getStartType().length(); i++) {
            if (line.charAt(i) != commentType.getStartType().charAt(i)) {
                result = false;
                break;
            }
        }

        return result;
    }
}
