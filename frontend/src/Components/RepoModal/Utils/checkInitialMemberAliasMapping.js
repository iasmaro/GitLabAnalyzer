export function allMembersHaveAliases(databaseMapping) {
    for (let mapping of databaseMapping) {
        if (mapping.alias.length === 0) {
            return false;
        } 
    }
    return true;
}

export function noMembersHaveAliases(databaseMapping) {
    for (let mapping of databaseMapping) {
        if (mapping.alias.length !== 0) {
            return false;
        } 
    }
    return true;
}