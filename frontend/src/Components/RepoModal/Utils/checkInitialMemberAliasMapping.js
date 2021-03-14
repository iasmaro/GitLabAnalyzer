export function allMembersHaveAliases(databaseMapping) {
    for (var i = 0; i < databaseMapping.length; i++) {
        if (databaseMapping[i].alias.length === 0) {
            return false;
        } 
    }
    return true;
}

export function noMembersHaveAliases(databaseMapping) {
    for (var i = 0; i < databaseMapping.length; i++) {
        if (databaseMapping[i].alias.length !== 0) {
            return false;
        } 
    }
    return true;
}