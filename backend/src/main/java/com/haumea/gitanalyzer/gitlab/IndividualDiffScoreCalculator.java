package com.haumea.gitanalyzer.gitlab;

import com.haumea.gitanalyzer.exception.GitLabRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class IndividualDiffScoreCalculator {

    private double addLineWeight;
    private double deleteLineWeight;
    private double syntaxLineWeight;
    private List<CommentType> commentTypes;

    private boolean isLongComment; /* need to save state between line calls as a comment could go
                                                   over multiple lines like this comment*/
    private String longCommentEndBrace;


    public IndividualDiffScoreCalculator() {

        isLongComment = false;
    }

    private double analyzeLine(String line) {
        double lineScore = 0.0;

        line = line.trim();

        if(line.equals("+")) {
            System.out.println("line is a space");
        }


        else if(line.length() > 1) {
            line = line.substring(1); // cutting out the +
            line = line.trim();

            if(isSyntax(line)) {
                lineScore = syntaxLineWeight;
            }
            else if(isComment(line)) {
//                System.out.println("line is a comment");
                lineScore = 0.0;
            }

            if(isLongComment == true) {
                checkForEndBrace(line);

                System.out.println("checking for end of comment");
            }


        }

        return lineScore;
    }

    private void checkForEndBrace(String line) {
        String potentialEndOfComment = "";

        if(line.length() > longCommentEndBrace.length()) {

            potentialEndOfComment = line.substring(line.length()-longCommentEndBrace.length(), line.length());

            System.out.println("substring is: " + potentialEndOfComment);


        }
        else if(line.length() == longCommentEndBrace.length()) {
            System.out.println("equal");
            potentialEndOfComment = line;

        }

        if(potentialEndOfComment.equals(longCommentEndBrace)) {
            isLongComment = false;

            System.out.println("comment is over");
        }


    }

    private boolean isComment(String line) {

        boolean result = true;


        /* dont worry about efficiency as there are likely to be at most 2 types of comments in this list.
           List passed in will be based on file type
         */
        for(CommentType commentType : commentTypes) {
          if(commentType.getEndType().equals("")) {
              result = isStartOnlyComment(line, commentType);

          }
          else {

              result = isStartAndEndComment(line, commentType);
          }
        }

        return result;
    }

    private boolean isStartAndEndComment(String line, CommentType commentType) {
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

            System.out.println("Long comment");

        }

        return result;
    }

    private boolean isStartOnlyComment(String line, CommentType commentType) {
        boolean result = true;
        for(int i=0; i<commentType.getStartType().length(); i++) {
            if (line.charAt(i) != commentType.getStartType().charAt(i)) {
                result = false;
                break;
            }
        }

        if(result == true) {
            System.out.println("short comment");
        }

        return result;
    }


    private boolean isSyntax(String line) {
        return line.equals("{") || line.equals("}");
    }

    private void setTypes( Double addLineMultiplier, Double deleteLineMultiplier, Double syntaxLineMultiplier,
                      List<CommentType> commentTypes) {
        this.addLineWeight = addLineMultiplier;
        this.deleteLineWeight = deleteLineMultiplier;
        this.syntaxLineWeight = syntaxLineMultiplier;
        this.commentTypes = commentTypes;
    }


    private double analyzeDiff(String diff) throws IOException {

        double diffScore = 0.0;

        // https://stackoverflow.com/questions/9259411/what-is-the-best-way-to-iterate-over-the-lines-of-a-java-string
        BufferedReader bufReader = new BufferedReader(new StringReader(diff));
        String line;
        while((line=bufReader.readLine()) != null )
        {
            if(line.charAt(0) == '+') {
                System.out.println("line is: " + line);
                diffScore = analyzeLine(line);
            }
            else if(line.charAt(0) == '-') {
                diffScore = deleteLineWeight;
            }
        }

        return diffScore;
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

            return score;
        }
    }
}
