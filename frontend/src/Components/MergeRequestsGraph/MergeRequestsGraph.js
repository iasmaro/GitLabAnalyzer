import React, { useState } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const MergeRequestsGraph = (props) => {
    const { MRsGraph } = props || {};

    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'Number', value: '1' },
        { name: 'Scores', value: '2' },
    ];
    
    const numbers = [ ["Date", "Number of MRs"] ];
    const scores = [ ["Date", "MR Score"] ];

    for (let MR of MRsGraph) {
        numbers.push([graphDateFormatter(MR?.date), MR?.numberOfMergeRequests]);
        scores.push([graphDateFormatter(MR?.date), MR?.mergeRequestScore]);
    }

    const [data, setData] = useState(numbers);

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