export function allMembersHaveAliases(databaseMapping) {
    if (databaseMapping) {
        for (let mapping of databaseMapping) {
            if (mapping.alias.length === 0) {
                return false;
            } 
        }
    }
    return true;
}

export function noMembersHaveAliases(databaseMapping) {
    if (databaseMapping) {
        for (let mapping of databaseMapping) {
            if (mapping.alias.length !== 0) {
                return false;
            } 
        }
    }
    return true;
}