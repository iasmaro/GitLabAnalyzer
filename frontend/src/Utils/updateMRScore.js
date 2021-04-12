import { config } from 'Constants/constants';

const updateMRScore = async (mergeIndex, diffIndex, memberId, newScore, reportName) => {
    const URL = `${config.PAST_REPORTS_API_URL}/updateScore/mr/mrDiff?reportName=${reportName}&mergeIndex=${mergeIndex}&diffIndex=${diffIndex}&memberId=${memberId}&newScore=${newScore}`;
    const response = await fetch(URL, {method: 'PUT'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default updateMRScore;