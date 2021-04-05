import React, { useState } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const CommitsGraph = (props) => {
    const { commitsGraph } = props || {};

    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'Number', value: '1' },
        { name: 'Scores', value: '2' },
    ];

    const numbers = [ ["Date", "Number of Commits"] ];
    const scores = [ ["Date", "Commits Score"] ];

    for (let commit of commitsGraph) {
        numbers.push([graphDateFormatter(commit.date), commit.numberOfCommits]);
        scores.push([graphDateFormatter(commit.date), commit.totalCommitScore]);
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

    const title = 'Commits';

    return (
        <Graph data={data} title={title} handleAxisChange={handleAxisChange} radios={radios} radioValue={radioValue} />
    )
}

export default CommitsGraph;