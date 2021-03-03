import React, { useState } from 'react';
import { Table, Spinner } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import ConfigDetails from 'Components/Configurations/ConfigDetails';
import { message } from 'Constants/constants';
import { configs } from './mockConfigs'

const ConfigurationPage = () => {

    const [selectedConfig, setSelectedConfig] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const [configInfo, setConfigInfo] = useState();

    const handleClick = (configName) => {
        setSelectedConfig(configName);
        setIsLoading(false);

        var filterObj = configs.filter(function(e) {
            return e.configName === configName;
          });
        setConfigInfo(filterObj)
    }

    return (
    <div className = 'configs-list-container'>
        <div className="left">
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='8'className='configTitle'>Configuartion Titles</th>
                    </tr>
                </thead>
                <tbody>
                    {!configs?.length ? (
                        <td colSpan={4} >{message.NO_CONFIGS}</td>
                    )
                    :
                    configs.map((config) => (
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