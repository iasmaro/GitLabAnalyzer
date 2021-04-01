import React from 'react';

const RepoSearchBar = (props) => {
    const { searchWord, setSearchWord } = props || {};
    return (
        <input
            className = "repo-search"
            value={searchWord}
            placeholder={"Search..."}
            onChange={(e) => setSearchWord(e.target.value)}
        />
    );
}

export default RepoSearchBar