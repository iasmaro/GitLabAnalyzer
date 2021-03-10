import React from 'react';
import Table from 'react-bootstrap/Table';

import { message } from 'Constants/constants';
import RepoModalAliasRow from './RepoModalAliasRow';

const RepoModalMatchAlias = (props) => {

    const {aliases, memberIds} = props || {};
    const aliasHeadings = aliases.map((alias) =>
        <th>{alias}</th>
    );

    return (
        <div>
            <Table striped bordered variant="light">
                <thead>
                    <tr>
                    <th colSpan={aliases.length + 1}>Match Aliases with Member IDs</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th>User IDs</th>
                        <th colSpan={aliases.length + 1}>Aliases</th>
                    </tr>
                </thead>
                <thead>
                    <tr>
                        <th></th>
                        {aliasHeadings}
                    </tr>
                </thead>
                <tbody>
                    {!memberIds.length ? (
                        <tr>
                            <td colSpan={aliases.length + 1} >{message.NO_MEMBERIDS}</td>
                        </tr>
                    )
                    : !aliases.length ? (
                        <tr>
                            <td colSpan={aliases.length + 1} >{message.NO_ALIASES}</td>
                        </tr>
                    )
                    :
                    memberIds.map((member) => (
                        <RepoModalAliasRow key={member} member={member} aliases={aliases}/>
                    ))
                    }
                </tbody>
            </Table>
        </div>
    );
};

export default RepoModalMatchAlias;