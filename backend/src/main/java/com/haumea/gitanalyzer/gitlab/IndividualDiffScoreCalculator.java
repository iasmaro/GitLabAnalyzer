package com.haumea.gitanalyzer.gitlab;


import com.haumea.gitanalyzer.dto.DiffScoreDTO;
import com.haumea.gitanalyzer.dto.LineChangeDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IndividualDiffScoreCalculator {

    private double addLineWeight;
    private double deleteLineWeight;
    private double syntaxLineWeight;
    private double moveLineWeight;
    private List<CommentType> commentTypes;

    private boolean isLongComment; /* need to save state between line calls as a comment could go
                                                   over multiple lines like this comment*/

    private boolean lineMovedAfterCode; // checking whether a line move is significant within the code
    String lastLineAdded;
    String lastLineSeen;

    private String longCommentEndBrace;

    private List<String> removedLines;
    private List<String> addedLines;
    private List<LineChangeDTO> lineChangeDTOS;

    private int numberOfLinesAdded;
    private int numberOfLinesRemoved;



    public IndividualDiffScoreCalculator() {

        this.isLongComment = false;
        this.lineMovedAfterCode = false;

        this.removedLines = new ArrayList<>(); // reset when finished
        this.addedLines = new ArrayList<>();
        this.lineChangeDTOS = new ArrayList<>();


    }

    private void clearMoveLineLists() {
        removedLines.clear();
        addedLines.clear();
    }


    private void setTypes(double addLineMultiplier, double deleteLineMultiplier, double syntaxLineMultiplier,
                           double movedLineWeight, List<CommentType> commentTypes) {
        this.addLineWeight = addLineMultiplier;
        this.deleteLineWeight = deleteLineMultiplier;
        this.syntaxLineWeight = syntaxLineMultiplier;
        this.moveLineWeight = movedLineWeight;
        this.commentTypes = commentTypes;
    }

    // check file type and configs in calling code
    public DiffScoreDTO calculateDiffScore(String diff,
                                           boolean isFileDeleted,
                                           double addLineWeight,
                                           double deleteLineWeight,
                                           double syntaxLineWeight,
                                           double movedLineWeight,
                                           double fileTypeMultiplier,
                                           List<CommentType> commentTypes) {

        setTypes(addLineWeight, deleteLineWeight, syntaxLineWeight, movedLineWeight, commentTypes);

        this.numberOfLinesAdded = 0;
        this.numberOfLinesRemoved = 0;

        if(isFileDeleted) {
            return new DiffScoreDTO(0, 0, 0.0, lineChangeDTOS);
        }
        else {
            double score;
            try {
                score = analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new IllegalArgumentException("input error");
            }

            this.removedLines.clear();
            this.addedLines.clear();
            lineMovedAfterCode = false;
//
//            for(LineChangeDTO current : lineChangeDTOS) {
//                System.out.println("Line Change is: " + current.getLine() + " " + current.getLineScore());
//
//            }


            BigDecimal roundedScore = new BigDecimal(Double.toString(score));
            roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

            return new DiffScoreDTO(numberOfLinesAdded, numberOfLinesRemoved, fileTypeMultiplier * roundedScore.doubleValue(), lineChangeDTOS);
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

            LineChangeDTO lineChangeDTO;

            double lineScore = 0.0;

            if(line.charAt(0) == '+' && (line.trim().length() > 0) && !line.trim().equals("+")) {
                lineScore = analyzeAddedLine(line);
                diffScore = diffScore + lineScore;
                this.numberOfLinesAdded++;

                System.out.println("added line: " + line + " " + lineScore);

                lineChangeDTO = new LineChangeDTO(originalLine, lineScore);
                lineChangeDTOS.add(lineChangeDTO);

            }
            else if(line.charAt(0) == '-' && (line.trim().length() > 0) && !line.trim().equals("-")) {

                line = line.substring(1); // cutting out the -
                line = line.trim();

                lineScore = analyzeRemovedLine(line);
                diffScore = diffScore + lineScore;
                this.numberOfLinesRemoved++;

                removedLines.add(line);

                System.out.println("removed line: " + line + " " + lineScore);


                lineChangeDTO = new LineChangeDTO(originalLine, lineScore);
                lineChangeDTOS.add(lineChangeDTO);

                lastLineSeen = line;

            }
            else { // still need to process unchanged line to check whether a long comment was changed

                line = line.trim();

                if(line.length() > 1) {
                    if(isComment(line) == false) {
                        lineMovedAfterCode = true;
                        lastLineSeen = line;
                    }

                }

                if(isLongComment == true) {
                    checkForEndBrace(line);
                }

            }

        }



        return diffScore;
    }
    private double analyzeRemovedLine(String line) {
        double lineScore = 0.0;

        if(isComment(line) == false && isLongComment == false) {
            // line was added earlier and removed in this position
            if(lineWasAdded(line) == true && isSyntax(line) == false && lineMovedAfterCode == true
            && lastLineSeen.equals(line) == false) {
                lineScore = calculatePointsForLineMovedUpOrDown(addLineWeight);
                addedLines.remove(line);

                System.out.println(line + " was moved on remove side " + lineMovedAfterCode);
                lineMovedAfterCode = false;

            }
            // line was added earlier but the move did not jump over any code
            else if(lineWasAdded(line) == true && isSyntax(line) == false
            && lastLineSeen.equals(line) == true) {

                if(isSyntax(line) == true) {
                    lineScore = -syntaxLineWeight;
                }
                else {
                    lineScore = -addLineWeight;
                }

                System.out.println("taking away points");
                lineMovedAfterCode = false;
            }
            else if(lineWasAdded(line) == false && isSyntax(line) == true) {
                lineScore = syntaxLineWeight;
                lineMovedAfterCode = false;

            }
            else {
                lineScore = deleteLineWeight;

                System.out.println(line + " was removed " + lineMovedAfterCode + " " + lastLineSeen);

                lineMovedAfterCode = false;
            }
        }

        return lineScore;
    }


    private double calculatePointsForLineMovedUpOrDown(double weightFromPrevOccurance) {
        double score = 0.0;

        if(weightFromPrevOccurance > moveLineWeight) {
            score = moveLineWeight - weightFromPrevOccurance; // taking back points
        }
        else if(moveLineWeight >  weightFromPrevOccurance) {
            score = moveLineWeight - weightFromPrevOccurance;
        }


        return score;
    }

    private double analyzeAddedLine(String line) {
        double lineScore = 0.0;

        line = line.trim();

        if(line.length() > 1 && line.equals("+") == false) {
            line = line.substring(1); // cutting out the +
            line = line.trim();


            if(isComment(line)) {
                lineScore = 0.0;
            }
            else if(isLongComment == false && lineWasRemoved(line) == true && isSyntax(line) == false
                    && lineMovedAfterCode == true) { // case where line is moved from one area to another

                // Making sure that points are not givin out for deletion and moving
                lineScore = calculatePointsForLineMovedUpOrDown(deleteLineWeight);

                addedLines.add(line);
                removedLines.remove(line);

                System.out.println(line + " was moved on add side ");
                lineMovedAfterCode = false;

            }
            // case where line is moved but is not moved before or after code
            else if(isLongComment == false && lineWasRemoved(line) == true && lineMovedAfterCode == false) {

                System.out.println("Line: " + line + " was a false remove");
                if(isSyntax(line) == true) {
                    lineScore = -syntaxLineWeight;
                }
                else {
                    lineScore = -deleteLineWeight;
                }

            }
            // line is a syntax line
            else if(isLongComment == false && isSyntax(line) == true && lineWasRemoved(line) == false) {
                lineScore = syntaxLineWeight;

                lineMovedAfterCode = true;
                lastLineAdded = line;

                System.out.println("line is a legit syntax line");
            }
            // normal line added
            else if(isLongComment == false && lineWasRemoved(line) == false) {
                lineScore = addLineWeight;

                addedLines.add(line);

                System.out.println(line + " was added " + lineScore);
                lineMovedAfterCode = true;
                lastLineAdded = line;

            }
            else {
                System.out.println("special case " + lineMovedAfterCode);
            }

            if(isLongComment == true) {
                checkForEndBrace(line);
            }

            lastLineSeen = line;




        }


        return lineScore;
    }

    private boolean lineWasRemoved(String line) {

        return removedLines.contains(line) && (Collections.frequency(removedLines,line) >= 1); // making sure a simple moving of line doesnt get points
    }

    private boolean lineWasAdded(String line) {

        return addedLines.contains(line) && (Collections.frequency(addedLines,line) >= 1); // making sure a simple moving of line doesnt get points
    }

    private void checkForEndBrace(String line) {
        String potentialEndOfComment = "";

        if(line.length() > longCommentEndBrace.length()) {

            potentialEndOfComment = line.substring(line.length()-longCommentEndBrace.length(), line.length());
        }
        else if(line.length() == longCommentEndBrace.length()) {
            potentialEndOfComment = line;

        }

        endOfLongComment(potentialEndOfComment);

    }

    private void endOfLongComment(String potentialEndOfComment) {

        if(potentialEndOfComment.equals(longCommentEndBrace)) {
            isLongComment = false;
        }
    }


    private boolean isSyntax(String line) {
        return line.equals("{") || line.equals("}") || line.equals("(") || line.equals(")") || line.equals(";");
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

            if(result == true) {
                break;
            }
        }

        return result;
    }

    private boolean isLongComment(String line, CommentType commentType) {
        boolean result = true;

        for(int i=0; i < commentType.getStartType().length(); i++) {
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
        for(int i=0; i < commentType.getStartType().length(); i++) {
            if (line.charAt(i) != commentType.getStartType().charAt(i)) {
                result = false;
                break;
            }
        }

        return result;
    }
}
