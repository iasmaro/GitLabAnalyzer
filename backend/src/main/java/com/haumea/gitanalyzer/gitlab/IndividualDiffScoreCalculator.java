package com.haumea.gitanalyzer.gitlab;


import com.haumea.gitanalyzer.dto.ScoreDTO;
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
    private boolean addition;
    private boolean removal;

    String lastLineSeen;

    private String longCommentEndBrace;

    private List<String> removedLines;
    private List<String> addedLines;
    private List<LineChangeDTO> lineChangeDTOS;

    private int numberOfLinesAdded;
    private int numberOfLinesRemoved;
    private int numberOfLinesMoved;
    private int numberOfSpaceLinesAdded;

    public IndividualDiffScoreCalculator() {

        this.isLongComment = false;
        this.addition = false;

        this.removedLines = new ArrayList<>(); // reset when finished
        this.addedLines = new ArrayList<>();
        this.lineChangeDTOS = new ArrayList<>();
    }

    public void clearMoveLineLists() {
        removedLines.clear();
        addedLines.clear();

        addition = false;
        removal = false;

        lastLineSeen = "";
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
    public ScoreDTO calculateDiffScore(String diff,
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
        this.numberOfLinesMoved = 0;
        this.numberOfSpaceLinesAdded = 0;


        if(isFileDeleted) {
            return new ScoreDTO(0, 0, 0.0, 0, 0);
        }
        else {
            double score;
            try {
                score = analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new IllegalArgumentException("input error");
            }

            BigDecimal roundedScore = new BigDecimal(Double.toString(score));
            roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);

            return new ScoreDTO(
                    numberOfLinesAdded,
                    numberOfLinesRemoved,
                    fileTypeMultiplier * roundedScore.doubleValue(),
                    numberOfLinesMoved,
                    numberOfSpaceLinesAdded);
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

            if(line.charAt(0) == '+') {
                lineScore = analyzeAddedLine(line);
                diffScore = diffScore + lineScore;
                this.numberOfLinesAdded++;


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

                lineChangeDTO = new LineChangeDTO(originalLine, lineScore);
                lineChangeDTOS.add(lineChangeDTO);

                lastLineSeen = line;

            }
            else { // still need to process unchanged line to check whether a long comment was changed

                line = line.trim();

                if((line.length() > 1 || isSyntax(line)) && isComment(line) == false) {
                    removal = false;
                    addition = false;
                    lastLineSeen = line;


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
            // line was moved
            if(lineWasAdded(line) == true) {
                // move went over non identical code
                if(lastLineSeen.equals(line) == false) {
                    lineScore = calcMovedLineScoreOnRemoveSide(line);
                }
                // move went over code where the prev line scanned was identical
                else if(lastLineSeen.equals(line) == true && addition == false) {
                    lineScore = calcMovedLineScoreOnRemoveSide(line);

                }
                // false move, the move didn't change the order of the code
                else if(lastLineSeen.equals(line) == true && addition == true) {
                    lineScore = calcFalseMoveLineScore(line);
                }
            }
            else if(lineWasAdded(line) == false && isSyntax(line) == true) {
                lineScore = syntaxLineWeight;
            }
            else {
                lineScore = deleteLineWeight;

            }

            removal = true;
            addition = false;
        }

        return lineScore;
    }

    private double calcFalseMoveLineScore(String line) {
        double lineScore = 0.0;

        if(isSyntax(line) == true) {
            lineScore = -syntaxLineWeight;
        }
        else {
            lineScore = -addLineWeight;
        }

        return lineScore;
    }

    private double calcMovedLineScoreOnRemoveSide(String line) {
        double lineScore = 0.0;
        if(isSyntax(line) == false) {
            lineScore = calculatePointsForLineMovedUpOrDown(addLineWeight);
        }

        addedLines.remove(line);

        numberOfLinesMoved++;

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

    private double calcMovedLineScoreOnAddSide(String line) {
        double lineScore = 0.0;

        // Making sure that points are not givin out for deletion and moving
        if(isSyntax(line) == false) {
            lineScore = calculatePointsForLineMovedUpOrDown(deleteLineWeight);
        }

        addedLines.add(line);
        removedLines.remove(line);

        addition = true;
        removal = false;

        numberOfLinesMoved++;

        return lineScore;
    }

    private double analyzeAddedLine(String line) {
        double lineScore = 0.0;

        line = line.trim();

        // making sure line is not a space
        if(line.length() > 1 && line.equals("+") == false) {
            line = line.substring(1); // cutting out the +
            line = line.trim();

            if(isComment(line)) {
                lineScore = 0.0;
            }
            // moved line
            else if(isLongComment == false && lineWasRemoved(line) == true) {
                /// normal case where line is moved from one area to another over different code
                if(lastLineSeen.equals(line) == false) {
                    lineScore = calcMovedLineScoreOnAddSide(line);
                }
                // case where line is moved from one area to another where prev line scanned was the same
                else if(lastLineSeen.equals(line) == true && removal == false) {
                    lineScore = calcMovedLineScoreOnAddSide(line);
                }
                else if(lastLineSeen.equals(line) == true && removal == true) {
                    lineScore = undoAddLineScore(line);

                    addition = true;
                    removal = false;
                }
            }
            // line is a syntax line
            else if(isLongComment == false && isSyntax(line) == true && lineWasRemoved(line) == false) {
                lineScore = syntaxLineWeight;

                addedLines.add(line);

                addition = true;
                removal = false;
                lastLineSeen = line;
            }
            // normal line added
            else if(isLongComment == false && lineWasRemoved(line) == false) {
                lineScore = addLineWeight;

                addedLines.add(line);

                addition = true;
                removal = false;
                lastLineSeen = line;

            }


            if(isLongComment == true) {
                checkForEndBrace(line);
            }

            lastLineSeen = line;
        }
        else if(line.equals("+")){
            numberOfSpaceLinesAdded++;
        }

        return lineScore;
    }
    private double undoAddLineScore(String line) {
        double lineScore = 0.0;
        if(isSyntax(line) == true) {
            lineScore = -syntaxLineWeight;
        }
        else {
            lineScore = -deleteLineWeight;
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
