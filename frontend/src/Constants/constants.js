const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    SFU_AUTHENTICATION_URL: 'https://cas.sfu.ca/cas/serviceValidate?service=http://localhost:3000/',
    REPOS_API_URL: 'http://localhost:8080/api/v1/projects',
    USERS_API_URL: 'http://localhost:8080/api/v1/users',
}

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    SFU_AUTHENTICATION_URL: 'https://cas.sfu.ca/cas/serviceValidate?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/projects',
    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca/api/v1/users',
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod;

export const message = {
    NO_COMMITS: 'No commits found for this user',
};
