export function sameAliasIdPairs(aliasIdPairs, databaseAliasIdPairs) {
    for (var i = 0; i < aliasIdPairs.length; i++) {
        if (aliasIdPairs[i].memberIndex !== databaseAliasIdPairs[i].memberIndex) {
            return false;
        } 
    }
    return true;
}