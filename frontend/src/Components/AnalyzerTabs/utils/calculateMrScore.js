const calculateMrScore = (mergerequests) => {
    let sumOfMRs = 0;
    if (!mergerequests)
        return sumOfMRs;
    for (let mergeRequest of mergerequests) {
        sumOfMRs += mergeRequest?.mrscore;
    }
    sumOfMRs = Math.round(sumOfMRs * 10) / 10;
    return sumOfMRs;
}

export default calculateMrScore;