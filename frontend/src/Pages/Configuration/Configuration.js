import React, { useState } from 'react';
import { Table, Spinner } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import ConfigDetails from 'Components/Configurations/ConfigDetails';
import { configs } from '../../Mocks/mockConfigs'
import './Configuration.css';

const ConfigurationPage = () => {

    const [selectedConfig, setSelectedConfig] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const [configInfo, setConfigInfo] = useState();

    const handleClick = (config) => {
        setSelectedConfig(config?.configName);
        setIsLoading(false);
        setConfigInfo(config)
    }

    return (
    <div className = 'configs-list-container'>
        <div className="left">
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='4' className='configTitle'>Configuration Titles</th>
                    </tr>
                </thead>
                <tbody>
                    {configs.map((config) => (
                        <Config key={config?.configName} config={config} handleClick={handleClick}/>
                    ))}
                </tbody>
            </Table>
        </div>
        <div className="right">
            {selectedConfig && isLoading && <Spinner animation="border" className="right-spinner" />}
            {selectedConfig && !isLoading && <ConfigDetails configInfo={configInfo} />}
        </div>
    </div>
    )
}

export default ConfigurationPage;