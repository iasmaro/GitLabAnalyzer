import React, { useState, useEffect } from 'react';
import { Table, Spinner } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import ConfigDetails from 'Components/Configurations/ConfigDetails';
import { configs } from 'Mocks/mockConfigs.js'
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

    useEffect(() => {
        if (configs.length > 0) {
            setSelectedConfig(configs?.[0].configName);
            setIsLoading(false);
            setConfigInfo(configs[0]);
        }
      }, []);

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