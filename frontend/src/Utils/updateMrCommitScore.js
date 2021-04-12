import { config } from 'Constants/constants';

const updateMrCommitScore = async (mergeIndex, commitIndex, diffIndex, memberId, newScore, reportName) => {
    const URL = `${config.PAST_REPORTS_API_URL}/updateScore/mr/commitDiff?reportName=${reportName}&mergeIndex=${mergeIndex}&commitIndex=${commitIndex}&diffIndex=${diffIndex}&memberId=${memberId}&newScore=${newScore}`;
    const response = await fetch(URL, {method: 'PUT'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default updateMrCommitScore;