import React from 'react';

import CommitsGraph from 'Components/CommitsGraph/CommitsGraph';
import MergeRequestsGraph from 'Components/MergeRequestsGraph/MergeRequestsGraph';
import IssuesGraph from 'Components/IssuesGraph/IssuesGraph';
import ReviewsGraph from 'Components/ReviewsGraph/ReviewsGraph';

import './SummaryTab.css'

const SummaryTab = (props) => {
    const { commitsGraph, MRsGraph, codeReviewsGraph, issueCommentsGraph } = props || {};

    return (
        <div className="summary-tab">
            <div className="top-left">
                <CommitsGraph commitsGraph={commitsGraph} />
            </div>
            <div className="top-right">
                <MergeRequestsGraph MRsGraph={MRsGraph} />
            </div>
            <div className="bottom-left">
                <ReviewsGraph codeReviewsGraph={codeReviewsGraph} />
            </div>
            <div className="bottom-right">
                <IssuesGraph issueCommentsGraph={issueCommentsGraph} />
            </div>
        </div>
    );
}

export default SummaryTab;