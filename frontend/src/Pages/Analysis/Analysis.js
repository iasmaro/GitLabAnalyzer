import React from 'react';

import CommitsList from 'Components/CommitsList/CommitsList';

import { commits } from 'Mocks/mockCommits';

const Analysis = (props) => {
    return (
        <div>
            <CommitsList commits={commits}/>
        </div>
    )
}

export default Analysis;