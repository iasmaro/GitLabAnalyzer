import React from "react";
import './RepoList.css';

const Repo = ({repo}) => {
    return (
        <div className="repo">
            <li className="repo-name">{repo.name}</li>
            <li className="repo-date">{repo.date}</li>
        </div>
    );
};

export default Repo