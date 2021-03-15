import getMergeRequests from 'Utils/getMergeRequests';
import getAllCommits from 'Utils/getAllCommits';

const analyzeAll = (students, username, projectId) => {
    const analysis = {};
    for (let student of students) {
        analysis[student] = {
            commits: getAllCommits(username, student, projectId),
            mergeRequests: getMergeRequests(username, student, projectId),
        };
    }
    return analysis;
}

export default analyzeAll;