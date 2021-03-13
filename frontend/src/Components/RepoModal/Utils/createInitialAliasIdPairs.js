// PASSED IN MOCK VALUES:
// PARAMETERS:
// members = ['anne', 'billy', 'chris', 'dan', 'emily', 'fred'];
// aliases = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm'];
// databaseMapping = [ {alias:['f', 'l'], memberId:'fred'}, {alias:['a', 'm'], memberId:'anne'} ];

// RETURNS:
// const mockAliasIdPairs = [{alias:'a', memberIndex:0}, 
//                     {alias:'b', memberIndex:-1}, 
//                     {alias:'c', memberIndex:-1}, 
//                     {alias:'d', memberIndex:-1}, 
//                     {alias:'e', memberIndex:-1}, 
//                     {alias:'f', memberIndex:5}, 
//                     {alias:'g', memberIndex:-1},
//                     {alias:'h', memberIndex:-1}, 
//                     {alias:'i', memberIndex:-1},
//                     {alias:'j', memberIndex:-1},
//                     {alias:'k', memberIndex:-1},
//                     {alias:'l', memberIndex:5},
//                     {alias:'m', memberIndex:0}]

export function createInitialAliasIdPairs(aliases, members, databaseMapping) {
    const aliasIdPairs = aliases.map((alias) => ({alias:alias, memberIndex: -1}))
    for (var i = 0; i < databaseMapping.length; i++) {
        for (var j = 0; j < databaseMapping[i].alias.length; j++) {
            aliasIdPairs[aliases.indexOf(databaseMapping[i].alias[j])].memberIndex = members.indexOf(databaseMapping[i].memberId);
        }
    }
    return aliasIdPairs;
}