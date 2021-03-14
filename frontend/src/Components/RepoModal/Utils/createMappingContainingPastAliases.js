// Return a new version of the databaseMapping with mapping of aliases that are not part of the current repo
export function createMappingContainingPastAliases(aliases, members, databaseMapping) {
    const mapping = members.map((member) => ({alias:[], memberId:member}));
    for (var i = 0; i < databaseMapping.length; i++) {
        for (var j = 0; j < databaseMapping[i].alias.length; j++) {
            if (aliases.indexOf(databaseMapping[i].alias[j]) === -1) {
                mapping[i].alias.push(databaseMapping[i].alias[j]);
            }
        }
    }
    return mapping;
}