import React, { useState } from 'react';
import Radio from '@material-ui/core/Radio';

const RepoModalMemberIdRow = (props) => {
    const { alias, members, aliasIndex, aliasIdPairs, setAliasIdPairs } = props || {};
    const [selectedMemberId, setSelectedMemberId] = useState( aliasIdPairs[aliasIndex].memberIndex === -1 ? '' : members[aliasIdPairs[aliasIndex].memberIndex] );

    const handleClick = (e) => {
        if (selectedMemberId === e.target.name) {
            setSelectedMemberId('');
            aliasIdPairs[aliasIndex].memberIndex = -1;
        } else {
            setSelectedMemberId(e.target.name);
            aliasIdPairs[aliasIndex].memberIndex = e.target.value;
        }
        setAliasIdPairs(aliasIdPairs);
    };

    return (
        <tr>
            <td className='sticky-column'>{alias}</td>
            {members.map((member, i) => 
                <td key={member} align={'center'}>
                    <Radio
                        checked={selectedMemberId === member}
                        onClick={handleClick}
                        value={i}
                        name={member}
                        color="default"
                    />
                </td>
            )}
        </tr>  
        
    );
};

export default RepoModalMemberIdRow;