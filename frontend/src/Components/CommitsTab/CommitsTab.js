import React, { useState } from 'react';

import CommitsList from 'Components/CommitsList/CommitsList';
import CodeDifferenceList from 'Components/CodeDifferenceList/CodeDifferenceList';

import './CommitsTab.css'

const MergeRequestTab = (props) => {
    const [diffs, setDiffs] = useState();

    const setCodeDiffs = (diffsList) => {
        setDiffs(diffsList);
    }

    return (
        <div className="merge-request-tab">
            <div className="commits-left">
                <CommitsList {...props} setCodeDiffs={setCodeDiffs} />
            </div>
            <div className="commits-right">
               {diffs && <CodeDifferenceList diffs={diffs} />}
            </div>
        </div>
    );
}

export default MergeRequestTab;