import React, { useState, useEffect } from 'react';
import { Table, Spinner, Button } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import ConfigDetails from 'Components/Configurations/ConfigDetails';
import ConfigModal from 'Components/Configurations/ConfigurationModal/ConfigModal'
import getConfigurations from 'Utils/getConfigurations';
import getConfigurationInfo from 'Utils/getConfigurationInfo';
import { useUserState } from 'UserContext';
import deleteConfig from 'Utils/deleteConfig';

import ConfigDefault from 'Components/Configurations/ConfigDefault';
import { useSortableDataArray, getClassNamesFor } from 'Utils/sortTables';
import { defaultConfig } from 'Mocks/mockConfigs.js';
import './Configuration.css';

const ConfigurationPage = () => {

    const [selectedConfig, setSelectedConfig] = useState("");
    const [isLoadingConfigs, setIsLoadingConfigs] = useState(true);
    const [isLoadingConfigInfo, setIsLoadingConfigInfo] = useState(true);
    const [updateConfigs, setUpdateConfigs] = useState(false);
    const [configInfo, setConfigInfo] = useState();
    const [configs, setConfigs] = useState([]);
    const { items, requestSortArray, sortConfig  } = useSortableDataArray(configs);
    const [message, setMessage] = useState("");
    const [show, setShow] = useState(false);
    const [edit, setEdit] = useState(false);
    const username = useUserState();

    const handleClick = (config) => {
        getConfigurationInfo(username, config).then((data) => {
            setConfigInfo(data);
            setSelectedConfig(config);
            setIsLoadingConfigInfo(false);
        });
    }

    const handleDelete = (config) => {
        deleteConfig(username, config);
        setConfigInfo("");
        setSelectedConfig("");
        setTimeout(() => {
            setUpdateConfigs(!updateConfigs);
        }, 200);
    }

    const handleShow = () => setShow(true);
    const handleEdit = () => {
        setEdit(true);
        setShow(true);
    }
    const handleClose = () => {
        setShow(false);
        if (edit) {
            handleClick(selectedConfig);
        }
        setEdit(false);
        setTimeout(() => {
            setUpdateConfigs(!updateConfigs);
        }, 200);
    }

    useEffect(() => {
        getConfigurations(username).then((data) => {
            setConfigs(data);
            setIsLoadingConfigs(false);
        });
    }, [username, updateConfigs]);

    return (
        <div className='configs-list-container'>
            <div id="config-snackbar">{message}</div>
            <div className="configs-left">
                <Table striped bordered hover variant="light">
                    <thead>
                        <tr>
                            <th colSpan='3' className={getClassNamesFor(sortConfig)} onClick={() => requestSortArray(configs)}>
                                Configurations
                            <Button variant="info" onClick={handleShow} className="new-config-button">+</Button>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <ConfigDefault defaultConfig={defaultConfig} handleClick={handleClick} />
                        {!isLoadingConfigs && configs?.length > 0 && items.map((config) => (
                            <Config key={config} config={config} handleClick={handleClick} handleDelete={handleDelete} />
                        ))}
                    </tbody>
                </Table>
            </div>
            {show && <ConfigModal status={show} toggleModal={handleClose} setMessage={setMessage} configInfo={edit && configInfo} />}
            <div className="configs-right">
                {selectedConfig && isLoadingConfigInfo && <Spinner animation="border" className="right-spinner" />}
                {selectedConfig && !isLoadingConfigInfo && <ConfigDetails configInfo={configInfo} handleEdit={handleEdit} />}
            </div>
        </div>
    )
}

export default ConfigurationPage;