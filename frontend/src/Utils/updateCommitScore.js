import { config } from 'Constants/constants';

const updateCommitScore = async (commitIndex, diffIndex, memberId, newScore, reportName) => {
    const URL = `${config.PAST_REPORTS_API_URL}/updateScore/commit/commitDiff?reportName=${reportName}&commitIndex=${commitIndex}&diffIndex=${diffIndex}&memberId=${memberId}&newScore=${newScore}`;
    const response = await fetch(URL, {method: 'PUT'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default updateCommitScore;