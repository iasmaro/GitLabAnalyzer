import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import RepoModalMemberIdRow from './RepoModalMemberIdRow';

import './RepoModal.css';

const RepoModalMatchAlias = (props) => {
    const {aliases, memberIds, mappedAliases, setMappedAliases} = props || {};
    const tableWidth = memberIds.length + 1;

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
                        {memberIds.map((memberId) => <td key={memberId} className='sticky-title'>{memberId}</td>)}
                    </tr>
                </thead>
                <tbody>
                    { !aliases.length ? (
                        <tr>
                            <td colSpan={tableWidth}>{message.NO_ALIASES}</td>
                        </tr>
                    )
                    : aliases.map((alias, index) => (
                        <RepoModalMemberIdRow key={alias} alias={alias} memberIds={memberIds} aliasIndex={index} mappedAliases={mappedAliases} setMappedAliases={setMappedAliases}/>
                    ))
                    }
                </tbody>
            </Table>
        </div>
    );
};

export default RepoModalMatchAlias;