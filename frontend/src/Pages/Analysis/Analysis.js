import React from 'react';

import CommitsList from 'Components/CommitsList/CommitsList';
import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';

import { commits } from 'Mocks/mockCommits';
import { mergerequests } from 'Mocks/mockMergeRequests'

const Analysis = (props) => {
    return (
        <div>
            <MergeRequestList mergerequests={mergerequests}/>
        </div>
    )
}

export default Analysis;

