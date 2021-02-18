const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    SFU_AUTHENTICATION_URL: 'http://localhost:8080/api/v1/users/user?url=http://localhost:3000/',
    REPOS_API_URL: 'http://localhost:8080/api/v1/projects',
    USERS_API_URL: 'http://localhost:8080/api/v1/users',
    PROJECT_MEMBERS_API_URL: 'http://localhost:8080/api/v1/members',
}

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    SFU_AUTHENTICATION_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/users/user?url=http://cmpt373-1211-11.cmpt.sfu.ca/',
    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/projects',
    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/users',
    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/members',
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod;

export const TABS = {
    SCORES: 'Scores',
    GRAPHS: 'Graphs',
    MERGE_REQUESTS: 'Merge Requests',
    COMMITS: 'Commits'
}
export const message = {
    NO_COMMITS: 'No commits found for this user',
    TOKEN_NOT_SET: 'Please set your gitlab token in the Profile page',
    NO_REPOS: 'It seems that you do not have any repositories at this moment',
};
