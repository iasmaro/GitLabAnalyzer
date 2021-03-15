const calculateMrScore = (commits) => {
    let sumOfCommits = 0;
    for (let commit of commits) {
        sumOfCommits += commit.commitScore;
    }
    sumOfCommits = Math.round(sumOfCommits * 10) / 10;
    return sumOfCommits;
}

export default calculateMrScore;