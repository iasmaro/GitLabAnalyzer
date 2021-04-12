import { config } from 'Constants/constants';

const deleteReport = async (reportName) => {
    const response = await fetch(`${config.PAST_REPORTS_API_URL}/deleteReport?reportName=${reportName}`, {method: 'DELETE'});
    const data = await response.text();
    if (response.ok) {
        return data;
    }
    return null;
}

export default deleteReport;