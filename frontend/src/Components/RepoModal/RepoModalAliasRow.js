import React from 'react';
import Checkbox from '@material-ui/core/Checkbox';

const RepoModalAliasRow = (props) => {
    const { member, aliases, mapping, memberIndex } = props || {};

    function removeElement(array, element) {
        var index = array.indexOf(element);
        if (index > -1) {
            array.splice(index, 1);
        }
    }
    
    const selectAlias = (e) => {
        {mapping[memberIndex].alias.includes(e.target.name) 
            ? removeElement(mapping[memberIndex].alias, e.target.name)
            : mapping[memberIndex].alias.push(e.target.name)
        }
      };

    const aliasCheckboxes = aliases.map((alias) =>
        <td key={alias} align={'center'}>
            <Checkbox
                color="default"
                onChange={selectAlias}
                name={alias}
                disabled={false}
            />
        </td>
    );

    return (
        <tr>
            <td className='sticky'>{member}</td>
            {aliasCheckboxes}
        </tr>   
    );
};

export default RepoModalAliasRow;