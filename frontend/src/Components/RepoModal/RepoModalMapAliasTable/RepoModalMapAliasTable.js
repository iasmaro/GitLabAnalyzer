import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import RepoModalMapAliasTableRow from './RepoModalMapAliasTableRow';

import './RepoModalMapAliasTable.css';

const RepoModalMapAliasTable = (props) => {
    const { aliases, members, aliasIdPairs, setAliasIdPairs } = props || {};
    const tableWidth = members.length + 1;

    return (
        <div className='mapping'>
            <Table striped bordered variant="light">
                <thead>
                    <tr>
                        <th colSpan={tableWidth} className='sticky-title'>Member IDs</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th className='sticky-column'>Aliases</th>
                        {members.map((member) => <td key={member} className='sticky-title'>{member}</td>)}
                    </tr>
                </thead>
                <tbody>
                    { !aliases?.length ? (
                        <tr>
                            <td colSpan={tableWidth}>{message.NO_ALIASES}</td>
                        </tr>
                    )
                    : aliases.map((alias, index) => (
                        <RepoModalMapAliasTableRow 
                            key={alias} 
                            alias={alias} 
                            members={members} 
                            aliasIndex={index} 
                            aliasIdPairs={aliasIdPairs} 
                            setAliasIdPairs={setAliasIdPairs}/>
                    ))
                    }
                </tbody>
            </Table>
        </div>
    );
};

export default RepoModalMapAliasTable;