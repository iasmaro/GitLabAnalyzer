// PASSED IN MOCK VALUES:
// PARAMETERS:
// members = ['anne', 'billy', 'chris', 'dan', 'emily', 'fred', 'k', 'h'];
// aliases = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'];
// databaseMapping = [ {alias:['f', 'l'], memberId:'fred'}, {alias:['a', 'm'], memberId:'anne'} ];

// RETURNS:
// aliasIdPairs = [{alias:'a', memberIndex:0}, 
//                     {alias:'b', memberIndex:-1}, 
//                     {alias:'c', memberIndex:-1}, 
//                     {alias:'d', memberIndex:-1}, 
//                     {alias:'e', memberIndex:-1}, 
//                     {alias:'f', memberIndex:5}, 
//                     {alias:'g', memberIndex:-1},
//                     {alias:'h', memberIndex:7}, 
//                     {alias:'i', memberIndex:-1},
//                     {alias:'j', memberIndex:-1},
//                     {alias:'k', memberIndex:6},
//                     {alias:'l', memberIndex:5},
//                     {alias:'m', memberIndex:0}]

export function createInitialAliasIdPairs(aliases, members, databaseMapping) {
    const aliasIdPairs = aliases.map((alias) => ({alias:alias, memberIndex: -1}))
    for (var i = 0; i < databaseMapping.length; i++) {
        for (var j = 0; j < databaseMapping[i].alias.length; j++) {
            aliasIdPairs[aliases.indexOf(databaseMapping[i].alias[j])].memberIndex = members.indexOf(databaseMapping[i].memberId);
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