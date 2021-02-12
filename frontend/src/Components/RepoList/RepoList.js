import React from 'react';
import Repo from './Repo';
import './RepoList.css';
import Table from 'react-bootstrap/Table';

const RepoList = ({repos}) => {
        return (
            <div className = 'list-container'>
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Repo Name</th>
                            <th>Last Modified</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {repos.map((repo) => (
                            <Repo key={repo.id} repo={repo}/>
                        ))}
                    </tbody>
                </Table>
            </div>
        );
    };

// const RepoList = ({repos}) => {
//     return (
//         <div className='repo-container'>
//             <ul className='repo-list'>
//                 {repos.map((repo) => (
//                     <Repo key={repo.id} repo={repo}/>
//                 ))}
//             </ul>
//         </div>
//     );
// };

export default RepoList