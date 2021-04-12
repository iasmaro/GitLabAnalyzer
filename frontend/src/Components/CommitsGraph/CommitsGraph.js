import React, { useState, useEffect } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const CommitsGraph = (props) => {
    const { commitsGraph } = props || {};
    const [data, setData] = useState();
    const [numbers, setNumbers] = useState();
    const [scores, setScores] = useState();
    const [radioValue, setRadioValue] = useState('1');
    
    const radios = [
        { name: 'Number', value: '1' },
        { name: 'Scores', value: '2' },
    ];

    useEffect(() => {
        const numberOfCommits = [ ['Date', 'Number of Commits', { role: 'style' }] ];
        const scoresOfCommits = [ ['Date', 'Commits Score', { role: 'style' }] ];
        for (let commit of commitsGraph) {
            numberOfCommits.push([graphDateFormatter(commit.date), commit.numberOfCommits, '#F6B636']);
            scoresOfCommits.push([graphDateFormatter(commit.date), commit.totalCommitScore, '#F6B636']);
        }
        setNumbers(numberOfCommits);
        setScores(scoresOfCommits);
        setData(numberOfCommits);
        setRadioValue('1')
    }, [commitsGraph]);


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