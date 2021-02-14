import React from 'react';

const Repo = (props) => {
    return (
        <tr>
            <td>{props.repo.name}</td>
            <td>{props.repo.date}</td>
            <button>Analyze</button>
        </tr>
    );
};

export default Repo