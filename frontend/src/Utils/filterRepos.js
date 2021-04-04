import { utcToLocal } from 'Components/Utils/formatDates';

const filterRepos = ((repo, searchWord) => {
    if (searchWord === '') {
        return repo
    } else if (repo?.projectName && repo?.projectName.toLowerCase().includes(searchWord.toLowerCase())) {
        return repo
    }
    else if (repo?.namespace && repo?.namespace.toLowerCase().includes(searchWord.toLowerCase())) {
        return repo
    }
    else if (repo?.updatedAt && utcToLocal(repo?.updatedAt).toLowerCase().includes(searchWord.toLowerCase())) {
        return repo
    }
});

export default filterRepos;