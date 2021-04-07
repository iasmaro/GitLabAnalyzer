package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dto.*;
import com.haumea.gitanalyzer.gitlab.CommentWrapper;
import com.haumea.gitanalyzer.gitlab.CommitWrapper;
import com.haumea.gitanalyzer.gitlab.GitlabService;
import com.haumea.gitanalyzer.gitlab.MergeRequestWrapper;
import com.haumea.gitanalyzer.model.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class GraphService {

    private final UserService userService;
    private final MemberService memberService;
    private final CommitService commitService;
    private final MergeRequestService mergeRequestService;

    @Autowired
    public GraphService(UserService userService, MemberService memberService, CommitService commitService, MergeRequestService mergeRequestService) {
        this.userService = userService;
        this.memberService = memberService;
        this.commitService = commitService;
        this.mergeRequestService = mergeRequestService;
    }

    // Counting number of words in string function from https://www.java67.com/2016/09/3-ways-to-count-words-in-java-string.html
    public static int countWordsUsingSplit(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    // checking if two dates are the same day function from https://www.baeldung.com/java-check-two-dates-on-same-day
    public static boolean isSameDay(Date firstDate, Date secondDate) {
        LocalDate firstLocalDate = firstDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate secondLocalDate = secondDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return firstLocalDate.isEqual(secondLocalDate);
    }

    public List<CommitGraphDTO> getCommitGraphDetails(String userId, String memberId, int projectId) {

        List<CommitGraphDTO> returnList = new ArrayList<>();

        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration userConfig = userService.getConfiguration(userId);
        List<String> aliases = memberService.getAliasesForSelectedMember(memberId);
        List<CommitWrapper> allCommits =  gitlabService.getFilteredCommitsWithDiffByAuthor(projectId, userConfig.getTargetBranch(),
                                                                                  userService.getStart(userId), userService.getEnd(userId), aliases);

        //date iterator from https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        Calendar start = Calendar.getInstance();
        start.setTime(userService.getStart(userId));
        Calendar end = Calendar.getInstance();
        end.setTime(userService.getEnd(userId));

        // set start date to 23:59:59 of previous day. This will guarantee the start day will be iterated through
        // but the previous day will not.
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);
        start.add(Calendar.DAY_OF_MONTH,-1);

        // set end time to avoid problems with daylight savings. This will also make sure all dates are correct in PST.
        end.set(Calendar.HOUR_OF_DAY, 15);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        int currentCount = 0;

        // loop from end date to start date because gitlab Service returns them sorted descending
        for(Date date = end.getTime(); end.after(start); end.add(Calendar.DATE, -1), date = end.getTime()) {

            int numberOfCommits = 0;
            double totalScore = 0.0;

            // take advantage of the fact that commits are all sorted in descending order and only iterate through them once
            while(currentCount != allCommits.size() && isSameDay(allCommits.get(currentCount).getCommitData().getCommittedDate(), date)) {
                numberOfCommits++;
                List<DiffDTO> commitDiffs = commitService.getCommitDiffs(allCommits.get(currentCount).getNewCode(), userConfig);
                ScoreDTO commitStats = commitService.getCommitStats(commitDiffs);
                totalScore = totalScore + commitStats.getScore();
                currentCount++;
            }

            // round to 1 decimal place to avoid weird floating point bug
            totalScore = Math.round(totalScore * 10.0) / 10.0;

            CommitGraphDTO commitGraphDTO = new CommitGraphDTO (date,numberOfCommits, totalScore);
            returnList.add(0, commitGraphDTO);

        }
        return returnList;
    }

    public List<MergeRequestGraphDTO> getMergeRequestGraphDetails(String userId, String memberId, int projectId) {

        List<MergeRequestGraphDTO> returnList = new ArrayList<>();

        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration userConfig = userService.getConfiguration(userId);
        List<String> aliases = memberService.getAliasesForSelectedMember(memberId);
        List<MergeRequestWrapper> allMergeRequests =  gitlabService.getFilteredMergeRequestsWithDiffByAuthor(projectId, userConfig.getTargetBranch(),
                                                                                                             userService.getStart(userId), userService.getEnd(userId), aliases);

        //date iterator from https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        Calendar start = Calendar.getInstance();
        start.setTime(userService.getStart(userId));
        Calendar end = Calendar.getInstance();
        end.setTime(userService.getEnd(userId));

        // set start date to 23:59:59 of previous day. This will guarantee the start day will be iterated through
        // but the previous day will not.
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);
        start.add(Calendar.DAY_OF_MONTH,-1);

        // set end time to avoid problems with daylight savings. This will also make sure all dates are correct in PST.
        end.set(Calendar.HOUR_OF_DAY, 15);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        int currentCount = 0;

        // loop from end date to start date because gitlab Service returns them sorted descending
        for(Date date = end.getTime(); end.after(start); end.add(Calendar.DATE, -1), date = end.getTime()) {

            int numberOfMergeRequests= 0;
            double totalScore = 0.0;

            // take advantage of the fact that mergeRequests are all sorted in descending order and only iterate through them once
            while(currentCount != allMergeRequests.size() && isSameDay(allMergeRequests.get(currentCount).getMergeRequestData().getMergedAt(), date)) {
                numberOfMergeRequests++;
                List<DiffDTO> mergeRequestDiffs = mergeRequestService.getMergeRequestDiffs(allMergeRequests.get(currentCount).getMergeRequestDiff(), userConfig);
                ScoreDTO mergeRequestStats = mergeRequestService.getMergeRequestStats(mergeRequestDiffs );
                totalScore = totalScore + mergeRequestStats.getScore();
                currentCount++;
            }

            // round to 1 decimal place to avoid weird floating point bug
            totalScore = Math.round(totalScore * 10.0) / 10.0;

            MergeRequestGraphDTO mergeRequestGraphDTO = new MergeRequestGraphDTO(date, numberOfMergeRequests, totalScore);
            returnList.add(0, mergeRequestGraphDTO);
        }

        return returnList;
    }

    public List<CodeReviewGraphDTO> getCodeReviewGraphDetails(String userId, String memberId, int projectId) {

        List<CodeReviewGraphDTO> returnList = new ArrayList<>();

        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration userConfig = userService.getConfiguration(userId);
        String targetBranch = userConfig.getTargetBranch();
        List<String> aliases = memberService.getAliasesForSelectedMember(memberId);
        List<CommentWrapper> MRComments = gitlabService.getMRCommentsByAuthor(projectId, targetBranch, userService.getStart(userId),
                                                                                 userService.getEnd(userId), aliases);

        //date iterator from https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        Calendar start = Calendar.getInstance();
        start.setTime(userService.getStart(userId));
        Calendar end = Calendar.getInstance();
        end.setTime(userService.getEnd(userId));

        // set end date to 00:00:00 of next day. This will guarantee the end day will be iterated through
        // but the next day will not.
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.add(Calendar.DAY_OF_MONTH,1);

        // set start time to avoid problems with daylight savings. This will also make sure all dates are correct in PST.
        start.set(Calendar.HOUR_OF_DAY, 15);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);

        int currentCount = 0;

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int wordsPerDay = 0;
            int wordsPerDayOnOwn = 0;
            int wordsPerDayOnOthers = 0;

            while(currentCount != MRComments.size() && isSameDay(MRComments.get(currentCount).getNote().getCreatedAt(), date)) {
                wordsPerDay = wordsPerDay + countWordsUsingSplit(MRComments.get(currentCount).getNote().getBody());

                if(MRComments.get(currentCount).getIsSameAuthor()) {
                    wordsPerDayOnOwn = wordsPerDayOnOwn + countWordsUsingSplit(MRComments.get(currentCount).getNote().getBody());
                }
                else {
                    wordsPerDayOnOthers = wordsPerDayOnOthers + countWordsUsingSplit(MRComments.get(currentCount).getNote().getBody());
                }

                currentCount++;
            }

            CodeReviewGraphDTO codeReviewGraphDTO = new CodeReviewGraphDTO(date, wordsPerDay, wordsPerDayOnOwn, wordsPerDayOnOthers);
            returnList.add(codeReviewGraphDTO);
        }

        return returnList;
    }

    public List<IssueGraphDTO> getIssueGraphDetails(String userId, String memberId, int projectId) {

        List<IssueGraphDTO> returnList = new ArrayList<>();

        GitlabService gitlabService = userService.createGitlabService(userId);
        Configuration userConfig = userService.getConfiguration(userId);
        String targetBranch = userConfig.getTargetBranch();
        List<String> aliases = memberService.getAliasesForSelectedMember(memberId);
        List<CommentWrapper> IssueComments = gitlabService.getIssueCommentsByAuthor(projectId, userService.getStart(userId),
                                                                                    userService.getEnd(userId), aliases);

        //date iterator from https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        Calendar start = Calendar.getInstance();
        start.setTime(userService.getStart(userId));
        Calendar end = Calendar.getInstance();
        end.setTime(userService.getEnd(userId));

        // set end date to 00:00:00 of next day. This will guarantee the end day will be iterated through
        // but the next day will not.
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.add(Calendar.DAY_OF_MONTH,1);

        // set start time to avoid problems with daylight savings. This will also make sure all dates are correct in PST.
        start.set(Calendar.HOUR_OF_DAY, 15);
        start.set(Calendar.MINUTE, 59);
        start.set(Calendar.SECOND, 59);

        int currentCount = 0;

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int wordsPerDay = 0;
            int wordsPerDayOnOwn = 0;
            int wordsPerDayOnOthers = 0;

            while(currentCount != IssueComments.size() && isSameDay(IssueComments.get(currentCount).getNote().getCreatedAt(), date)) {
                wordsPerDay = wordsPerDay + countWordsUsingSplit(IssueComments.get(currentCount).getNote().getBody());

                if (IssueComments.get(currentCount).getIsSameAuthor()) {
                    wordsPerDayOnOwn = wordsPerDayOnOwn + countWordsUsingSplit(IssueComments.get(currentCount).getNote().getBody());
                }
                else {
                    wordsPerDayOnOthers = wordsPerDayOnOthers + countWordsUsingSplit(IssueComments.get(currentCount).getNote().getBody());
                }

                currentCount++;
            }

            IssueGraphDTO issueGraphDTO = new IssueGraphDTO(date, wordsPerDay, wordsPerDayOnOwn, wordsPerDayOnOthers);
            returnList.add(issueGraphDTO);
        }

        return returnList;
    }
}
