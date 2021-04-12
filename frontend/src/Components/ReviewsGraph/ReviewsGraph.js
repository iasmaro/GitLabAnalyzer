import React, { useState, useEffect } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const ReviewsGraph = (props) => {
    const { codeReviewsGraph } = props || {};
    const [data, setData] = useState();
    const [words, setWords] = useState();
    const [own, setOwn] = useState();
    const [others, setOthers] = useState();
    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'All', value: '1' },
        { name: 'Own', value: '2' },
        { name: "Others", value: '3' },
    ];

    useEffect(() => {
        const wordsPerDay = [ ['Date', 'Words/Day', { role: 'style' }] ];
        const ownMRs = [ ['Date', 'Words/Day', { role: 'style' }] ];
        const othersMRs = [ ['Date', 'Words/Day',{ role: 'style' }] ];
        for (let review of codeReviewsGraph) {
            wordsPerDay.push([graphDateFormatter(review?.date), review?.wordsPerDay, '#F6B636']);
            ownMRs.push([graphDateFormatter(review?.date), review?.wordsPerDayOnOwn, '#F6B636']);
            othersMRs.push([graphDateFormatter(review?.date), review?.wordsPerDayOnOtherMergeRequests, '#F6B636']);
        }
        setWords(wordsPerDay);
        setOwn(ownMRs);
        setOthers(othersMRs);
        setData(wordsPerDay);
        setRadioValue('1')
    }, [codeReviewsGraph]);

    const handleAxisChange = (e) => {
        setRadioValue(e.currentTarget.value);
        if (e.currentTarget.value === '1') {
            setData(words);
        } else if (e.currentTarget.value === '2') {
            setData(own);
        } else {
            setData(others);
        }
    }

    const title = 'Comments on Merge Requests';

    return (
        <Graph data={data} title={title} handleAxisChange={handleAxisChange} radios={radios} radioValue={radioValue} />
    )
}

export default ReviewsGraph;