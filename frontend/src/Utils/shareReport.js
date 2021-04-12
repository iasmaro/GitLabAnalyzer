import { config } from 'Constants/constants';

const shareReport = async (userId, reportName) => {
    const URL = `${config.PAST_REPORTS_API_URL}/addReportAccess?userId=${userId}&reportName=${reportName}`;

    const response = await fetch(URL, { 
        method: "PUT",
        headers: { "Content-type": "application/json"},
    });
    return response.ok;
}

export default shareReport;