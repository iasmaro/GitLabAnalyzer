import React from 'react';

const SearchBar = (props) => {
    const { searchWord, setSearchWord } = props || {};
    return (
        <input
            className = "search-bar"
            value={searchWord}
            placeholder={"Search..."}
            onChange={(e) => setSearchWord(e.target.value)}
        />
    );
}

export default SearchBar