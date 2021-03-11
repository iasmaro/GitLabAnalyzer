import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import RepoModalMemberIdRow from './RepoModalMemberIdRow';
import './RepoModal.css';

const RepoModalMatchAlias = (props) => {

    const {aliases, memberIds, mappedAliases, setMappedAliases} = props || {};
    const tableWidth = memberIds.length + 1;

    const memberIdHeadings = memberIds.map((memberId) =>
        <td key={memberId} className='sticky-title'>{memberId}</td>
    );

    return (
        <div className='mapping'>
            <Table striped bordered variant="light">
                <thead>
                    <tr>
                        <th className='sticky-title'></th>
                        <th colSpan={tableWidth} className='sticky-title'>Member IDs</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th className='sticky-column'>Aliases</th>
                        {memberIdHeadings}
                    </tr>
                </thead>
                <tbody>
                    {!memberIds.length ? (
                        <tr>
                            <td colSpan={tableWidth}>{message.NO_MEMBERIDS}</td>
                        </tr>
                    )
                    : !aliases.length ? (
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