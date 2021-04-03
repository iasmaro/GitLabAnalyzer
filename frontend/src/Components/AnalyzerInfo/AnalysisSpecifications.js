import React from 'react';

import { utcToLocal } from 'Components/Utils/formatDates';

import './AnalysisSpecifications.css';

const AnalysisSpecifications = (props) => {

    const { startDate, endDate, configuration, namespace, projectName } = props || {};

    return (
        <div className='specifications'>
            <div className='individual-specification'>
                <span>Start Date: </span>
                <span>{utcToLocal(startDate)}</span>
            </div>
            <div className='individual-specification'>
                <span>End Date: </span>
                <span>{utcToLocal(endDate)}</span>
            </div>
            <div className='individual-specification'>
                <span>Configuration: </span>
                <span>{configuration}</span>
            </div>
            <div className='individual-specification'>
                <span>Namespace: </span>
                <span>{namespace}</span>
            </div>
            <div className='individual-specification'>
                <span>Repository: </span>
                <span>{projectName}</span>
            </div>
        </div>
    );
};

export default AnalysisSpecifications;