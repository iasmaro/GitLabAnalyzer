export function sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs) {
    for (let [aliasIndex, alias] of aliasIdPairs.entries()) {
        if (alias.memberIndex !== databaseAliasIdPairs[aliasIndex].memberIndex) {
            return false;
        } 
    }
    return true;
}