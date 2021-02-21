import React from 'react';

import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';

import { mergerequests } from 'Mocks/mockMergeRequests'

const Analysis = () => {
    return (
        <div>
            <MergeRequestList mergerequests={mergerequests}/>
        </div>
    )
}

export default Analysis;

