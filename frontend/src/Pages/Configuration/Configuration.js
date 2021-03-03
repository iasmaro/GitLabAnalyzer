import React from 'react';
import { Table } from 'react-bootstrap';

import Config from 'Components/Configurations/Config';
import { message } from 'Constants/constants';
import { configs } from './mockConfigs'

const ConfigurationPage = () => {


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
                        <Config key={config?.configName} config={config}/>
                    ))}

                </tbody>
            </Table>
        </div>

    </div>
    )
}

export default ConfigurationPage;