import React from 'react';
import Table from 'react-bootstrap';


const ConfigurationPage = () => {
    <div className = 'configs-list-container'>
        <div className="left">
            <Table striped bordered hover variant="light">
                <thead>
                    <tr>
                        <th colSpan='8'className='configTitle'>Configuartion Titles</th>
                    </tr>
                </thead>

            </Table>
        </div>
    </div>
}

export default ConfigurationPage;