import React from 'react';
import {Button} from 'react-bootstrap'

const Repo = (props) => {
    const { repo } = props || {};
    return (
        <tr>
            <td>{repo?.projectName}</td>
            <td>{repo?.updatedAt}</td>
            <td>
                <Button variant="light"> Analyze </Button>
            </td>
        </tr>
    );
};

export default Repo