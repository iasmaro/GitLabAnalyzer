// Return array of alias and memberIndex pairs which are used to track the user's mapping of members and aliases in current repo
export function createInitialAliasIdPairs(aliases, members, databaseMapping) {
    const aliasIdPairs = aliases.map((alias) => ({alias:alias, memberIndex: -1}));
    for (var i = 0; i < databaseMapping.length; i++) {
        for (var j = 0; j < databaseMapping[i].alias.length; j++) {
            if (aliases.indexOf(databaseMapping[i].alias[j]) !== -1) {
                aliasIdPairs[aliases.indexOf(databaseMapping[i].alias[j])].memberIndex = members.indexOf(databaseMapping[i].memberId);
            }
        }
    }

    // Need to automatically map aliases to members that are the same (if no previous mapping to that alias exists)
    for (i = 0; i < aliases.length; i++) {
        for (j = 0; j < members.length; j++) {
            if (aliases[i] === members[j] && aliasIdPairs[i].memberIndex === -1) {
                aliasIdPairs[i].memberIndex = j;
            } 
        }
    }
    return aliasIdPairs;
}