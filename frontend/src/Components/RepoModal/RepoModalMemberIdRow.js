import React, { useState } from 'react';
import Radio from '@material-ui/core/Radio';

const RepoModalMemberIdRow = (props) => {
    const { alias, memberIds, aliasIndex, mappedAliases, setMappedAliases} = props || {};
    const [selectedMemberId, setSelectedMemberId] = useState('');

    const handleClick = (e) => {
        if (selectedMemberId === e.target.name) {
            setSelectedMemberId('');
            mappedAliases[aliasIndex].memberIndex = -1;
        } else {
            setSelectedMemberId(e.target.name);
            mappedAliases[aliasIndex].memberIndex = e.target.value;
        }
        console.log(mappedAliases);
        setMappedAliases(mappedAliases);
      };

    return (
        <tr>
            <td className='sticky-column'>{alias}</td>
            {memberIds.map((memberId, i) => 
                <td key={memberId} align={'center'}>
                    <Radio
                        checked={selectedMemberId === memberId}
                        onClick={handleClick}
                        value={i}
                        name={memberId}
                        color="default"
                    />
                </td>
            )}
        </tr>  
        
    );
};

export default RepoModalMemberIdRow;