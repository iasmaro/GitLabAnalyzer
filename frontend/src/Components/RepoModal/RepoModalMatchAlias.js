import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import RepoModalAliasRow from './RepoModalAliasRow';
import './RepoModal.css';

const RepoModalMatchAlias = (props) => {

    const {aliases, memberIds, mapping } = props || {};

    const aliasHeadings = aliases.map((alias) =>
        <td key={alias} className='title'>{alias}</td>
    );

    return (
        <div className='mapping'>
            <Table striped bordered variant="light">
                <thead>
                    <tr>
                    <th colSpan={aliases.length + 1} className='title'>Match Aliases with Member IDs</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th></th>
                        <th colSpan={aliases.length + 1} className='title'>Aliases</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th className='sticky'>Member IDs</th>
                        {aliasHeadings}
                    </tr>
                </thead>
                <tbody>
                    {!memberIds.length ? (
                        <tr>
                            <td colSpan={aliases.length + 1}>{message.NO_MEMBERIDS}</td>
                        </tr>
                    )
                    : !aliases.length ? (
                        <tr>
                            <td colSpan={aliases.length + 1}>{message.NO_ALIASES}</td>
                        </tr>
                    )
                    :
                    memberIds.map((member, index) => (
                        <RepoModalAliasRow key={member} member={member} aliases={aliases} mapping={mapping} memberIndex={index}/>
                    ))
                    }
                </tbody>
            </Table>
        </div>
    );
};

export default RepoModalMatchAlias;