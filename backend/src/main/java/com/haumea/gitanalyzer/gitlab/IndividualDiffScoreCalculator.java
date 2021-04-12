package com.haumea.gitanalyzer.gitlab;


import com.haumea.gitanalyzer.dto.ScoreDTO;

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

    private String lastLineSeen;

    private String longCommentEndBrace;

    private List<String> removedLines;
    private List<String> addedLines;

    private int numberOfLinesAdded;
    private int numberOfLinesRemoved;
    private int meaningFullLinesAdded;
    private int meaningFullLinesRemoved;
    private int numberOfLinesMoved;
    private int numberOfSpaceLinesAdded;
    private int numberOfSpaceLinesRemoved;
    private int numberOfSyntaxLinesAdded;
    private int numberOfSyntaxLinesRemoved;
    private int numberOfCommentLinesAdded;
    private int numberOfCommentLinesRemoved;

    // num of code lines added and removed
    // update removed and added lines to account for moves
    // add num of comment lines
    // account for the gitlab edge case

    public IndividualDiffScoreCalculator() {

        this.isLongComment = false;
        this.addition = false;

        this.removedLines = new ArrayList<>(); // reset when finished
        this.addedLines = new ArrayList<>();
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
                                       boolean isFileRenamed,
                                       double addLineWeight,
                                       double deleteLineWeight,
                                       double syntaxLineWeight,
                                       double movedLineWeight,
                                       double fileTypeMultiplier,
                                       List<CommentType> commentTypes) {

        setTypes(addLineWeight, deleteLineWeight, syntaxLineWeight, movedLineWeight, commentTypes);

        this.numberOfLinesAdded = 0;
        this.numberOfLinesRemoved = 0;

        this.meaningFullLinesAdded = 0;
        this.meaningFullLinesRemoved = 0;

        this.numberOfLinesMoved = 0;

        this.numberOfSpaceLinesAdded = 0;
        this.numberOfSpaceLinesRemoved = 0;

        this.numberOfSyntaxLinesAdded = 0;
        this.numberOfSyntaxLinesRemoved = 0;

        this.numberOfCommentLinesAdded = 0;
        this.numberOfCommentLinesRemoved = 0;


        if(isFileDeleted || isFileRenamed) {
            return new ScoreDTO(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0);
        }
        else {
            double score;
            try {
                score = analyzeDiff(diff);
            }
            catch (IOException e) {
                throw new IllegalArgumentException("input error");
            }

            score = score * fileTypeMultiplier;

            BigDecimal roundedScore = new BigDecimal(Double.toString(score));
            roundedScore = roundedScore.setScale(2, RoundingMode.HALF_UP);


            return new ScoreDTO(
                    numberOfLinesAdded,
                    numberOfLinesRemoved,
                    meaningFullLinesAdded,
                    meaningFullLinesRemoved,
                    numberOfSyntaxLinesAdded,
                    numberOfSyntaxLinesRemoved,
                    numberOfSpaceLinesAdded,
                    numberOfSpaceLinesRemoved,
                    numberOfLinesMoved,
                    numberOfCommentLinesAdded,
                    numberOfCommentLinesRemoved,
                    roundedScore.doubleValue()
                   );
        }
    }

    private double analyzeDiff(String diff) throws IOException {

        double diffScore = 0.0;

        // https://stackoverflow.com/questions/9259411/what-is-the-best-way-to-iterate-over-the-lines-of-a-java-string
        BufferedReader bufReader = new BufferedReader(new StringReader(diff));
        String line;
        while((line=bufReader.readLine()) != null ) {
            String newLine = removesSpacesInString(line);
            double lineScore = 0.0;

            try {
                if (newLine.charAt(0) == '+') {
                    lineScore = analyzeAddedLine(newLine);
                    diffScore = diffScore + lineScore;
                    this.numberOfLinesAdded++;
                } else if (newLine.charAt(0) == '-' && (newLine.trim().length() > 0) && !newLine.trim().equals("-")) {

                    newLine = newLine.substring(1); // cutting out the -
                    newLine = newLine.trim();

                    lineScore = analyzeRemovedLine(newLine);
                    diffScore = diffScore + lineScore;
                    this.numberOfLinesRemoved++;

                    removedLines.add(newLine);
                    lastLineSeen = newLine;

                } else if (newLine.trim().equals("-")) {
                    this.numberOfSpaceLinesRemoved++;
                    this.numberOfLinesRemoved++;
                } else { // still need to process unchanged line to check whether a long comment was changed
                    newLine = newLine.trim();

                    if ((newLine.length() > 1 || isSyntax(newLine)) && isComment(newLine) == false && isAutoGitlabLine(newLine) == false) {
                        removal = false;
                        addition = false;
                        lastLineSeen = newLine;
                    }

                    if (isLongComment == true) {
                        checkForEndBrace(newLine);
                    }
                }

            } catch (IndexOutOfBoundsException e) {
                // let the program keep running if "" is checked at index 0
            }
        }

        return diffScore;
    }

    private String removesSpacesInString(String line) {
        return line.replaceAll("\\s+","");

    }

    /*
      Gitlab adds a special line to the end of the files that doesn't show up in the syntax highlighting.
      Most of the time this doesn't matter but there are times where it may interfere with the move line system as the
      calculator views it as a piece of code and this tricks the calculator into thinking a piece of code moved

     */
    private boolean isAutoGitlabLine(String line) {
        return line.equals("\\Nonewlineatendoffile");
    }


    private double analyzeRemovedLine(String line) {
        double lineScore = 0.0;

        if(isComment(line) == false && isLongComment == false) {
            // line was moved
            if(lineWasAdded(line) == true) {
                // move went over non identical code
                if(lastLineSeen.equals(line) == false && lineWasAdded(lastLineSeen) == false) {
                    lineScore = calcMovedLineScoreOnRemoveSide(line);
                }
                // move went over code where the prev line scanned was identical
                else if(lastLineSeen.equals(line) == true && addition == false  && lineWasAdded(lastLineSeen) == false) {
                    lineScore = calcMovedLineScoreOnRemoveSide(line);

                }
                else {
                    lineScore = calcFalseMoveLineScore(line);
                }
                if(isSyntax(line) == false) {
                    this.meaningFullLinesAdded--;
                }
                else {
                    this.numberOfSyntaxLinesAdded--;
                }

            }
            else if(lineWasAdded(line) == false && isSyntax(line) == true) {
                lineScore = syntaxLineWeight;
                this.numberOfSyntaxLinesRemoved++;
            }
            else {
                lineScore = deleteLineWeight;
                this.meaningFullLinesRemoved++;
            }

            removal = true;
            addition = false;
        }
        else if(isComment(line) == true) {
            this.numberOfCommentLinesRemoved++;
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
                this.numberOfCommentLinesAdded++;
            }
            // moved line
            else if(isLongComment == false && lineWasRemoved(line) == true) {
                /// normal case where line is moved from one area to another over different code
                if(lastLineSeen.equals(line) == false && lineWasRemoved(lastLineSeen) == false) {

                    lineScore = calcMovedLineScoreOnAddSide(line);
                }
                // case where line is moved from one area to another where prev line scanned was the same
                else if(lastLineSeen.equals(line) == true && removal == false && lineWasRemoved(lastLineSeen) == false) {
                    lineScore = calcMovedLineScoreOnAddSide(line);
                }
                else if(lastLineSeen.equals(line) == false && removal == false && lineWasRemoved(lastLineSeen) == true) {
                    lineScore = calcMovedLineScoreOnAddSide(line);
                }
                else {

                    lineScore = undoRemoveLineScore(line);

                    addition = true;
                    removal = false;

                }

                if(isSyntax(line) == false) {
                    this.meaningFullLinesRemoved--;
                }
                else {
                    // syntax lines removed -- if we add that var in

                    this.numberOfSyntaxLinesRemoved--;
                }
            }
            // line is a syntax line
            else if(isLongComment == false && isSyntax(line) == true && lineWasRemoved(line) == false) {
                lineScore = syntaxLineWeight;

                addedLines.add(line);

                addition = true;
                removal = false;
                lastLineSeen = line;

                this.numberOfSyntaxLinesAdded++;
            }
            // normal line added
            else if(isLongComment == false && lineWasRemoved(line) == false) {
                lineScore = addLineWeight;

                addedLines.add(line);

                addition = true;
                removal = false;
                lastLineSeen = line;

                this.meaningFullLinesAdded++;

            }


            if(isLongComment == true) {
                checkForEndBrace(line);
            }

            lastLineSeen = line;
        }
        else if(line.equals("+")){
            this.numberOfSpaceLinesAdded++;
        }

        return lineScore;
    }
    private double undoRemoveLineScore(String line) {
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
