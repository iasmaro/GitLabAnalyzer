const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/userId?url=http://localhost:3000/',
    REPOS_API_URL: 'http://localhost:8080/api/v1/projects',
    MR_API_URL: 'http://localhost:8080/api/v1/mergeRequests/member/',
    MR_COMMITS_API_URL: 'http://localhost:8080/api/v1/commits/mergeRequests/',
    COMMITS_API_URL: 'http://localhost:8080/api/v1/commits/members/',
    USERS_TOKEN_API_URL: 'http://localhost:8080/api/v1/users/token',
    USERS_API_URL: 'http://localhost:8080/api/v1/users',
    PROJECT_MEMBERS_API_URL: 'http://localhost:8080/api/v1/members',
    CONFIGURATION_FILES_URL: 'http://localhost:8080/api/v1/users/configuration',
    CONFIGURATION_FILE_INFO_URL: 'http://localhost:8080/api/v1/users/configuration/',
}

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/userId?url=http://cmpt373-1211-11.cmpt.sfu.ca/',
    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/projects',
    MR_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/mergeRequests/member/',
    MR_COMMITS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/commits/mergeRequests/',
    COMMITS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/commits/members/',
    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users',
    USERS_TOKEN_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/token',
    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members',
    CONFIGURATION_FILES_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/configuration',
    CONFIGURATION_FILE_INFO_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/configuration/',
}


export const config = process.env.NODE_ENV === 'development' ? dev : prod;

export const modal = {
    CONFIG : "Configuration:",
    CONFIG_OPTION : "Default Configuration",
    STUDENT : "Student:",
    START_DATE : "Start Date:",
    END_DATE : "End Date:"
};

export const TABS = {
    SCORES: 'Scores',
    GRAPHS: 'Graphs',
    MERGE_REQUESTS: 'Merge Requests',
    COMMITS: 'Commits'
};

export const message = {
    NO_MERGE_REQUEST: 'No merge requests found for this user',
    NO_COMMITS: 'No commits found for this user',
    TOKEN_NOT_SET: 'Please set your gitlab token in the Profile page',
    NO_REPOS: 'It seems that you do not have any repositories at this moment',
};

export const SCHEME = {
    HTTP: 'http://',
    HTTPS: 'https://',
};

export const PROGRAMMING_LANGUAGES = {
    // full list of supported languages can be found here
    // https://github.com/wooorm/refractor#syntaxes
    // Not all supported languages have had their file extensions added
    c: 'c',
    cake: 'csharp',
    cc: 'cpp',
    cmake: 'cmake',
    cp: 'cpp',
    cpp: 'cpp',
    cs: 'csharp',
    css: 'css',
    csx: 'csharp',
    cxx: 'cpp',
    'c++': 'cpp',
    docker: 'docker',
    dockerfile: 'docker',
    git: 'git',
    go: 'go',
    graphql: 'graphql',
    groovy: 'groovy',
    grt: 'groovy',
    gtpl: 'groovy',
    gvy: 'groovy',
    h: 'clike',
    html: 'markup',
    java: 'java',
    jl: 'julia',
    js: 'javascript',
    json: 'json',
    jsx: 'jsx',
    kt: 'kotlin',
    ktm: 'kotlin',
    kts: 'kotlin',
    lisp: 'lisp',
    lsp: 'lisp',
    lua: 'lua',
    m: 'matlab',
    makefile: 'makefile',
    matlab: 'matlab',
    md: 'markdown',
    php: 'php',
    py: 'python',
    r: 'r',
    rb: 'ruby',
    ru: 'ruby',
    ruby: 'ruby',
    rs: 'rust',
    sc: 'scala',
    scala: 'scala',
    sh: 'bash',
    svg: 'markup',
    swift: 'swift',
    ts: 'typescript',
    tsx: 'tsx',
    txt: 'markdown',
    xml: 'markup',
    yaml: 'yaml',
    yml: 'yaml',
};
