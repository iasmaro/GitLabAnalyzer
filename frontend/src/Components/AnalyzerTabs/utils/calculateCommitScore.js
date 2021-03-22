const calculateCommitScore = (commits) => {
    let sumOfCommits = 0;
    if(!commits){
        return sumOfCommits;
    }

    for (let commit of commits) {
        sumOfCommits += commit.commitScore;
    }
    sumOfCommits = Math.round(sumOfCommits * 10) / 10;
    return sumOfCommits;
}

export default calculateCommitScore;