// Return array of alias and memberIndex pairs which are used to track the user's mapping of members and aliases in current repo
export function createInitialAliasIdPairs(aliases, members, databaseMapping) {
    const aliasIdPairs = aliases.map((alias) => ({alias:alias, memberIndex: -1}));
    // Fill array with initial databaseMapping pairs
    for (let mapping of databaseMapping) {
        for (let alias of mapping.alias) {
            const indexOfDatabaseAliasInAliases = aliases.indexOf(alias);
            if (indexOfDatabaseAliasInAliases !== -1) {
                aliasIdPairs[indexOfDatabaseAliasInAliases].memberIndex = members.indexOf(mapping.memberId);
            }
        }
    }

    // Need to automatically map aliases to members that are the same (if no previous mapping to that alias exists)
    for (let [aliasIndex, alias] of aliases.entries()) {
        for (let [memberIndex, member] of members.entries()) {
            if (alias === member && aliasIdPairs[aliasIndex].memberIndex === -1) {
                aliasIdPairs[aliasIndex].memberIndex = memberIndex;
            } 
        }
    }
    return aliasIdPairs;
}