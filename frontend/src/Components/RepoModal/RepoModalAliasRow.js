import React, {useState} from 'react';
import Checkbox from '@material-ui/core/Checkbox';

const RepoModalAliasRow = (props) => {
    const { member, aliases } = props || {};
    const aliasCheckboxes = aliases.map((alias) =>
        <td>
            <Checkbox/>
        </td>
    );

    return (
        <tr>
            <td>{member}</td>
            {aliasCheckboxes}
        </tr>   
    );
};

export default RepoModalAliasRow;