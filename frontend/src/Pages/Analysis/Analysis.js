import React from 'react';

import CommitsList from 'Components/CommitsList/CommitsList';

import{commitsEmpty} from './mockCommits';

const Analysis = (props) => {
    return (
        <div>
            <CommitsList commits={commitsEmpty}/>
        </div>
    )
}

export default Analysis;