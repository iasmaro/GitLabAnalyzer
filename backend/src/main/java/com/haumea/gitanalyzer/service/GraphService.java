package com.haumea.gitanalyzer.service;

import com.haumea.gitanalyzer.dao.ReportRepository;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GraphService {

    private final UserService userService;
    private final MemberService memberService;
    private final CommitService commitService;
    private final MergeRequestService mergeRequestService;
    private final ReportService reportService;

    @Autowired
    public GraphService(UserService userService, MemberService memberService, CommitService commitService, MergeRequestService mergeRequestService, ReportService reportService) {
        this.userService = userService;
        this.memberService = memberService;
        this.commitService = commitService;
        this.mergeRequestService = mergeRequestService;
        this.reportService = reportService;
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

        // set end date to 00:00:00 of next day. This will guarantee the end day will be iterated through
        // but the next day will not.
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.add(Calendar.DAY_OF_MONTH,1);

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int numberOfCommits = 0;
            double totalScore = 0.0;

            // Keep track of a list of commits on this date to remove from the list of commits
            // because a commit can only be on one date. This will slightly improve efficiency.
            List<CommitWrapper> commitsOnThisDate = new ArrayList<CommitWrapper>();

            for(CommitWrapper commit : allCommits) {

                Date commitDate = commit.getCommitData().getCommittedDate();

                if(isSameDay(commitDate, date)) {

                    numberOfCommits++;
                    List<DiffDTO> commitDiffs = commitService.getCommitDiffs(commit.getNewCode(), userConfig);
                    ScoreDTO commitStats = commitService.getCommitStats(commitDiffs);
                    totalScore = totalScore + commitStats.getScore();
                    commitsOnThisDate.add(commit);

                }

            }

            allCommits.removeAll(commitsOnThisDate);
            CommitGraphDTO commitGraphDTO = new CommitGraphDTO(date, numberOfCommits, totalScore);
            returnList.add(commitGraphDTO);

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

        // set end date to 00:00:00 of next day. This will guarantee the end day will be iterated through
        // but the next day will not.
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.add(Calendar.DAY_OF_MONTH,1);

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int numberOfMergeRequests= 0;
            double totalScore = 0.0;

            // Keep track of a list of mergeRequests on this date to remove from the list of mergeRequests
            // because a mergeRequest can only be on one date. This will slightly improve efficiency.
            List<MergeRequestWrapper> mergeRequestsOnThisDate = new ArrayList<MergeRequestWrapper>();

            for(MergeRequestWrapper mergeRequest : allMergeRequests) {

                Date mergeRequestDate = mergeRequest.getMergeRequestData().getMergedAt();

                if(isSameDay(mergeRequestDate, date)) {

                    numberOfMergeRequests++;
                    List<DiffDTO> mergeRequestDiffs = mergeRequestService.getMergeRequestDiffs(mergeRequest.getMergeRequestDiff(), userConfig);
                    ScoreDTO mergeRequestStats = mergeRequestService.getMergeRequestStats(mergeRequestDiffs );
                    totalScore = totalScore + mergeRequestStats.getScore();
                    mergeRequestsOnThisDate.add(mergeRequest);

                }

            }

            allMergeRequests.removeAll(mergeRequestsOnThisDate);
            MergeRequestGraphDTO mergeRequestGraphDTO = new MergeRequestGraphDTO(date, numberOfMergeRequests, totalScore);
            returnList.add(mergeRequestGraphDTO);
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

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int wordsPerDay = 0;
            int wordsPerDayOnOwn = 0;
            int wordsPerDayOnOthers = 0;

            // Keep track of a list of MRComments on this date to remove from the list of mergeRequests
            // because a mergeRequest can only be on one date. This will slightly improve efficiency.
            List<CommentWrapper> MRCommentsOnThisDate = new ArrayList<CommentWrapper>();

            for(CommentWrapper MRComment : MRComments) {

                Date MRCommentDate = MRComment.getNote().getCreatedAt();

                if(isSameDay(MRCommentDate, date)) {

                    wordsPerDay = wordsPerDay + countWordsUsingSplit(MRComment.getNote().getBody());
                    if(MRComment.getIsSameAuthor()) {
                        wordsPerDayOnOwn = wordsPerDayOnOwn + countWordsUsingSplit(MRComment.getNote().getBody());
                    }
                    else {
                        wordsPerDayOnOthers = wordsPerDayOnOthers + countWordsUsingSplit(MRComment.getNote().getBody());
                    }
                    MRCommentsOnThisDate.add(MRComment);

                }

            }

            MRComments.removeAll(MRCommentsOnThisDate);
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

        for(Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            int wordsPerDay = 0;
            int wordsPerDayOnOwn = 0;
            int wordsPerDayOnOthers = 0;

            // Keep track of a list of IssueComments on this date to remove from the list of mergeRequests
            // because a mergeRequest can only be on one date. This will slightly improve efficiency.
            List<CommentWrapper> IssueCommentsOnThisDate = new ArrayList<CommentWrapper>();

            for(CommentWrapper IssueComment : IssueComments) {

                Date IssueCommentDate = IssueComment.getNote().getCreatedAt();

                if(isSameDay(IssueCommentDate, date)) {

                    wordsPerDay = wordsPerDay + countWordsUsingSplit(IssueComment.getNote().getBody());
                    if(IssueComment.getIsSameAuthor()) {
                        wordsPerDayOnOwn = wordsPerDayOnOwn + countWordsUsingSplit(IssueComment.getNote().getBody());
                    }
                    else {
                        wordsPerDayOnOthers = wordsPerDayOnOthers + countWordsUsingSplit(IssueComment.getNote().getBody());
                    }
                    IssueCommentsOnThisDate.add(IssueComment);

                }

            }

            IssueComments.removeAll(IssueCommentsOnThisDate);
            IssueGraphDTO issueGraphDTO = new IssueGraphDTO(date, wordsPerDay, wordsPerDayOnOwn, wordsPerDayOnOthers);
            returnList.add(issueGraphDTO);
        }

        return returnList;
    }

    public void updateCommitGraph(String userId, int projectId, Date start, Date end, String configName, Date commitDate, double difference) {
        reportService.updateCommitGraph(userId, projectId, start, end, configName, commitDate, difference);
    }
}
