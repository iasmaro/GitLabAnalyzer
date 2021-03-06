package com.haumea.gitanalyzer.gitlab;

/*

Models a student and encapsulates his/her gitlab data
 */

import java.util.ArrayList;
import java.util.List;

public class MemberWrapper {
    private String studentName;
    private String email;
    private String username;
    private int memberId;

    private int codeScore;
    private int codeReviewScore;

    private List<String> aliases;

    public String getStudentName() {
        return studentName;
    }

    public int getMemberId() {
        return memberId;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getCodeScore() {
        return codeScore;
    }

    public int getCodeReviewScore() {
        return codeReviewScore;
    }

    public void addAlias(String name) {
        aliases.add(name);
    }

    public MemberWrapper(String name, String email, String username, int memberId) {
        this.studentName = name;
        this.email = email;
        this.username = username;
        this.memberId = memberId;

        this.aliases = new ArrayList<>();
    }


    /*
    TODO: Calculate the student code score by adding up all the code differences from their commits and merge requests

     */
    public void calculateCodeScore() {
        codeScore = 11;
    }
    /*
    TODO: Calculate the student code review score by adding up the number of comments

     */
    public void calculateCodeReviewScore() {
        codeReviewScore = 131;
    }
}
