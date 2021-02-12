import React from 'react';

const Repo = ({repo}) => {
    return (
        <tr>
            <td>{repo.name}</td>
            <td>{repo.date}</td>
            <td>Analyze</td>
        </tr>
    );
};

// const Repo = ({repo}) => {
//     return (
//         <div className='repo'>
//             <li className='repo-name'>{repo.name}</li>
//             <li className='repo-date'>{repo.date}</li>
//         </div>
//     );
// };

export default Repo