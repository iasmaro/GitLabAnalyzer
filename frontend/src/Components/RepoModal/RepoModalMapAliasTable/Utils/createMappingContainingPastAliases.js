// Return a new version of the databaseMapping with mapping of aliases that are not part of the current repo
export function createMappingContainingPastAliases(aliases, members, databaseMapping) {
    const mapping = members.map((member) => ({alias:[], memberId:member}));
    for (let [mappingIndex, databaseMap] of databaseMapping.entries()) {
        for (let alias of databaseMap.alias) {
            if (aliases.indexOf(alias) === -1) {
                mapping[mappingIndex].alias.push(alias);
            }
        }
    }
    return mapping;
}