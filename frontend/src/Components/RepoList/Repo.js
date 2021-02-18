import React from 'react';
import Button from 'react-bootstrap/Button'

const Repo = (props) => {
    const { repo } = props || {};
    return (
        <tr>
<<<<<<< HEAD
            <td>{props.repo.name}</td>
            <td>{props.repo.date}</td>
            <td>
                <Button variant="light" className= 'Analyze' >Analyze</Button>
            </td>
=======
            <td>{repo?.projectName}</td>
            <td>{repo?.updatedAt}</td>
            <td><button>Analyze</button></td>
>>>>>>> master
        </tr>
    );
};

export default Repo