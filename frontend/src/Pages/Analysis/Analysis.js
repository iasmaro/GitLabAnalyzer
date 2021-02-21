import React, { useState, useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import { useUserState } from 'UserContext';
import getMergeRequests from 'Utils/getMergeRequests';
import MergeRequestList from 'Components/MergeRequestList/MergeRequestList';

const Analysis = (props) => {
    const { location } = props || {};
    const { state } = location || {};
    const { data } = state || {};
    const [isLoading, setIsLoading] = useState(true);
    const [mergeRequests, setMergeRequests] = useState();
    const username = useUserState();
    useEffect(() => {
        getMergeRequests(username, data.memberId, data.start, data.end, data.projectId).then((data) => {
            setMergeRequests(data);
            setIsLoading(false);
        });
    }, [username]);
    
    if (!data) {
        return(<Redirect to={{ pathname: '/' }} />);
    }

    return (
        <div>
            {isLoading ? <Spinner animation="border" className="spinner" /> : <MergeRequestList mergerequests={mergeRequests}/>}
        </div>
    )
}

export default Analysis;

