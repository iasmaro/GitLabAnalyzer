import React from 'react';

const Repo = ({repo}) => {
    return (
        <tr>
            <td>{repo.name}</td>
            <td>{repo.date}</td>
            <button>Analyze</button>
        </tr>
    );
};

export default Repo