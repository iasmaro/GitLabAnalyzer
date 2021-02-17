import React from 'react';

const Commit = (props) => {
    return (
        <tr>
            <td>{props.commit.id}</td>
            <td>{props.commit.date}</td>
            <td>{props.commit.author}</td>
            <td>{props.commit.message}</td>
        </tr>
    );
};

export default Commit