import React, { useState } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const ReviewsGraph = (props) => {
    const { codeReviewsGraph } = props || {};

    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'All', value: '1' },
        { name: 'Own', value: '2' },
        { name: "Others", value: '3' },
    ];
    
    const wordsPerDay = [ ["Date", "Words/Day"] ];
    const ownMRs = [ ["Date", "Words/Day"] ];
    const othersMRs = [ ["Date", "Words/Day"] ];

    for (let review of codeReviewsGraph) {
        wordsPerDay.push([graphDateFormatter(review?.date), review?.wordsPerDay]);
        ownMRs.push([graphDateFormatter(review?.date), review?.wordsPerDayOnOwn]);
        othersMRs.push([graphDateFormatter(review?.date), review?.wordsPerDayOnOtherMergeRequests]);
    }

    const [data, setData] = useState(wordsPerDay);

    const handleAxisChange = (e) => {
        setRadioValue(e.currentTarget.value);
        if (e.currentTarget.value === '1') {
            setData(wordsPerDay);
        } else if (e.currentTarget.value === '2') {
            setData(ownMRs);
        } else {
            setData(othersMRs);
        }
    }

    const title = 'Comments on Merge Requests';

    return (
        <Graph data={data} title={title} handleAxisChange={handleAxisChange} radios={radios} radioValue={radioValue} />
    )
}

export default ReviewsGraph;