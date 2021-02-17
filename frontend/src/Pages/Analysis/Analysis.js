import React from 'react';

import CommitsList from 'Components/CommitsList/CommitsList';

import{commits} from './mockCommits';

const Analysis = (props) => {
    return (
        <div>
            <CommitsList commits={commits}/>
        </div>
    )
}

export default Analysis;