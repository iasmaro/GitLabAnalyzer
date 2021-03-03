const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    AUTHENTICATION_API_URL: 'http://localhost:8080/api/v1/users/userId?url=http://localhost:3000/',
    REPOS_API_URL: 'http://localhost:8080/api/v1/projects',
    MR_API_URL: 'http://localhost:8080/api/v1/mrs',
    MR_COMMITS_API_URL: 'http://localhost:8080/api/v1/commits/mergeRequests/',
    COMMITS_API_URL: 'http://localhost:8080/api/v1/commits/members/',
    USERS_API_URL: 'http://localhost:8080/api/v1/users',
    PROJECT_MEMBERS_API_URL: 'http://localhost:8080/api/v1/members',
}

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/userId?url=http://cmpt373-1211-11.cmpt.sfu.ca/',
    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/projects',
    MR_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/mrs',
    MR_COMMITS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/commits/mergeRequests/',
    COMMITS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/commits/members/',
    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users',
    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members',
}


export const config = process.env.NODE_ENV === 'development' ? dev : prod;

export const modal = {
    CONFIG : "Configuration:",
    CONFIG_OPTION : "Default Configuration",
    STUDENT : "Student:",
    START_DATE : "Start Date:",
    END_DATE : "End Date:"
}
export const TABS = {
    SCORES: 'Scores',
    GRAPHS: 'Graphs',
    MERGE_REQUESTS: 'Merge Requests',
    COMMITS: 'Commits'
}
export const message = {
    NO_MERGE_REQUEST: 'No merge requests found for this user',
    NO_COMMITS: 'No commits found for this user',
    TOKEN_NOT_SET: 'Please set your gitlab token in the Profile page',
    NO_REPOS: 'It seems that you do not have any repositories at this moment',
};
