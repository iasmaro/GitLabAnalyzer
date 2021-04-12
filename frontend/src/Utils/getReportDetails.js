import { config } from 'Constants/constants';

const getReportDetails = async (reportName) => {
    const response = await fetch(`${config.PAST_REPORTS_API_URL}/getReport?reportName=${reportName}`);
    const data = await response.json();
    if (response.ok) {
        return data;
    }
    return null
}

export default getReportDetails;