import React, { useState, useEffect } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const MergeRequestsGraph = (props) => {
    const { MRsGraph } = props || {};
    const [data, setData] = useState();
    const [numbers, setNumbers] = useState();
    const [scores, setScores] = useState();
    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'Number', value: '1' },
        { name: 'Scores', value: '2' },
    ];
    

    useEffect(() => {
        const numberOfMRs = [ ['Date', 'Number of MRs', { role: 'style' }] ];
        const scoresOfMRs = [ ['Date', 'MR Score', { role: 'style' }] ];
        for (let MR of MRsGraph) {
            numberOfMRs.push([graphDateFormatter(MR?.date), MR?.numberOfMergeRequests, '#F6B636']);
            scoresOfMRs.push([graphDateFormatter(MR?.date), MR?.mergeRequestScore, '#F6B636']);
        }
        setNumbers(numberOfMRs);
        setScores(scoresOfMRs);
        setData(numberOfMRs);
        setRadioValue('1')
    }, [MRsGraph]);

    const handleAxisChange = (e) => {
        setRadioValue(e.currentTarget.value);
        if (e.currentTarget.value === '1') {
            setData(numbers);
        } else {
            setData(scores);
        }
    }

    const title = 'Merge Requests';

    return (
        <Graph data={data} title={title} handleAxisChange={handleAxisChange} radios={radios} radioValue={radioValue} />
    )
}

export default MergeRequestsGraph;