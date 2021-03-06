import React, { useState, useEffect } from 'react';

import { graphDateFormatter } from 'Components/Utils/graphDateFormatter';
import Graph from 'Components/Graph/Graph';

const IssuesGraph = (props) => {
    const { issueCommentsGraph } = props || {};
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
        const ownIssues = [ ['Date', 'Words/Day', { role: 'style' }] ];
        const othersIssues = [ ['Date', 'Words/Day', { role: 'style' }] ];

        for (let issue of issueCommentsGraph) {
            wordsPerDay.push([graphDateFormatter(issue?.date), issue?.wordsPerDay, '#F6B636']);
            ownIssues.push([graphDateFormatter(issue?.date), issue?.wordsPerDayOnOwn, '#F6B636']);
            othersIssues.push([graphDateFormatter(issue?.date), issue?.wordsPerDayOnOtherIssues, '#F6B636']);
        }
        setWords(wordsPerDay);
        setOwn(ownIssues);
        setOthers(othersIssues);
        setData(wordsPerDay);
        setRadioValue('1');
    }, [issueCommentsGraph])


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

    const title = 'Comments On Issues';

    return (
        <Graph data={data} title={title} handleAxisChange={handleAxisChange} radios={radios} radioValue={radioValue} />
    )
}

export default IssuesGraph;