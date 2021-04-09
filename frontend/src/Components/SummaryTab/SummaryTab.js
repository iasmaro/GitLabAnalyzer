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
                {commitsGraph && <CommitsGraph commitsGraph={commitsGraph} />}
            </div>
            <div className="top-right">
                {MRsGraph && <MergeRequestsGraph MRsGraph={MRsGraph} />}
            </div>
            <div className="bottom-left">
                {codeReviewsGraph && <ReviewsGraph codeReviewsGraph={codeReviewsGraph} />}
            </div>
            <div className="bottom-right">
                {issueCommentsGraph && <IssuesGraph issueCommentsGraph={issueCommentsGraph} />}
            </div>
        </div>
    );
}

export default SummaryTab;