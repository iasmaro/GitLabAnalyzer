import React, { useState, useEffect } from 'react';
import { Table, Spinner } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import ConfigDetails from 'Components/Configurations/ConfigDetails';
import getConfigurations from 'Utils/getConfigurations';
import getConfigurationInfo from 'Utils/getConfigurationInfo';
import { useUserState } from 'UserContext';
import './Configuration.css';

const ConfigurationPage = () => {

    const [selectedConfig, setSelectedConfig] = useState();
    const [isLoading, setIsLoading] = useState(true);
    const [configInfo, setConfigInfo] = useState();
    const [configs, setConfigs] = useState([]);
    const username = useUserState();

    const handleClick = (config) => {
        setSelectedConfig(config);
        setIsLoading(false);

        getConfigurationInfo(username, selectedConfig).then((data) => {
            console.log(data)
            setConfigInfo(data);
        });
    }

    useEffect(() => {
        getConfigurations(username).then((data) => {
            setConfigs(data);
            setIsLoading(false);
        });
    }, [username]);

    useEffect(() => {
        if (configs.length > 0) {
            setSelectedConfig(configs[0]);
            setIsLoading(false);
        }
      }, [configs]);




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
                        <Config key={config} config={config} handleClick={handleClick}/>
                    ))}
                </tbody>
            </Table>
        </div>
        <div className="right">
            {selectedConfig && isLoading && <Spinner animation="border" className="right-spinner" />}
            {/* {selectedConfig && !isLoading && <ConfigDetails configInfo={configInfo} />} */}
        </div>
    </div>
    )
}

export default ConfigurationPage;