import React from 'react';
import Button from 'react-bootstrap/Button'

const Repo = (props) => {
    return (
        <tr>
            <td>{props.repo.name}</td>
            <td>{props.repo.date}</td>
            <td>
                <Button variant="light" className= 'Analyze' >Analyze</Button>{' '}
            </td>
        </tr>
    );
};

export default Repo