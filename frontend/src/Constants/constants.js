const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    AUTHENTICATION_API_URL: 'http://localhost:8080/api/v1/users/userId?url=http://localhost:3000/',
    REPOS_API_URL: 'http://localhost:8080/api/v1/projects',
    ANALYSIS_API_URL: 'http://localhost:8080/api/v1/reports',
    USERS_API_URL: 'http://localhost:8080/api/v1/users',
    USERS_TOKEN_API_URL: 'http://localhost:8080/api/v1/users/token',
    PROJECT_MEMBERS_API_URL: 'http://localhost:8080/api/v1/members',
    PAST_REPORTS_API_URL: 'http://localhost:8080/api/v1/PastReports',
    SAVE_CONFIG_URL: 'http://localhost:8080/api/v1/users/configuration',
    PROJECT_ALIAS_API_URL: 'http://localhost:8080/api/v1/members/alias',
    PROJECT_MAPPING_API_URL: 'http://localhost:8080/api/v1/members/mapping',
    CONFIGURATION_FILES_URL: 'http://localhost:8080/api/v1/users/configuration',
    CONFIGURATION_FILE_INFO_URL: 'http://localhost:8080/api/v1/users/configuration/',
    START_URL: 'http://localhost:8080/api/v1/users/start/',
    END_URL: 'http://localhost:8080/api/v1/users/end/',
};

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    AUTHENTICATION_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/userId?url=http://cmpt373-1211-11.cmpt.sfu.ca/',
    REPOS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/projects',
    ANALYSIS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/reports',
    USERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users',
    USERS_TOKEN_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/token',
    PROJECT_MEMBERS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members',
    PAST_REPORTS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/PastReports',
    SAVE_CONFIG_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/configuration',
    PROJECT_ALIAS_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members/alias',
    PROJECT_MAPPING_API_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/members/mapping',
    CONFIGURATION_FILES_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/configuration',
    CONFIGURATION_FILE_INFO_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/configuration/',
    START_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/start/',
    END_URL: 'http://cmpt373-1211-11.cmpt.sfu.ca:8080/api/v1/users/end/',
};

export const config = process.env.NODE_ENV === 'development' ? dev : prod;

export const modal = {
    CONFIG : "Configuration:",
    DATES : "Dates:",
    CONFIG_OPTION : "Default Configuration",
    START_DATE : "Start Date:",
    END_DATE : "End Date:"
};

export const date = {
    FULL_DATE_TIME : "yyyy/MM/dd HH:mm"
};

export const TABS = {
    SCORES: 'Scores',
    SUMMARY: 'Summary',
    GRAPHS: 'Graphs',
    MERGE_REQUESTS: 'Merge Requests',
    COMMITS: 'Commits',
    COMMENTS: 'Comments'
};

export const message = {
    NO_MERGE_REQUEST: 'No merge requests found for this user',
    NO_COMMITS: 'No commits found for this user',
    TOKEN_NOT_SET: 'Please set your gitlab token in the Profile page',
    NO_REPOS: 'It seems that you do not have any repositories at this moment',
    NO_ALIASES: 'No aliases found',
    NO_COMMENTS: 'No comments found for this user',
    NO_REPORTS: 'It seems that you have not generated any reports yet',
};

export const ConfigLabels = {
    CONFIGURATION_NAME: "Configuration Name:",
    ADD_NEW_LINE: "Add New Line:",
    DELETE_LINE: "Delete Line:",
    SPACING: "Spacing Change:",
    SYNTAX: "Syntax Only:",
    MOVE_LINE: "Move Line:",
    SCORE_WEIGHT: "Enter Code Weight",
    CONFIGURATION_FORM: "Configuration Form",
    CODE_SCORE: "Code Score Values",
    DEFAULT_FILE: "Default File Types",
    NEW_FILE: "New File Types",
    JAVA: ".java:",
    JS: ".js:",
    TS: ".ts:",
    PY: ".py:",
    HTML: ".html:",
    CSS: ".css:",
    XML: ".xml:",
    CPP: ".cpp:",
    C: ".c:",
    FILE_WEIGHT: "Enter File Weight",
    FILE_EXTENSION: "File Extension:",
    SINGLE_LINE_COMMENT: "Single Line Comment:",
    MULTI_LINE_COMMENT_START: "Multi-line Comment - START:",
    MULTI_LINE_COMMENT_END: "Multi-line Comment - END:",
    WEIGHT: "Weight:",
    NEW_FILE_EXTENSION: "Enter New File Extension",
    SINGLE_COMMENT: "Enter Single Line Comment",
    MULTI_START_COMMENT: "Enter Start of Multi-line Comment",
    MULTI_END_COMMENT: "Enter End of Multi-line Comment",
}


export const initialConfigState = {
    CONFIGURATION_NAME: '',
    ADD_NEW_LINE : '1.0',
    DELETE_LINE : '0.2',
    SYNTAX : '0.2',
    MOVE_LINE : '0.5',
    JAVA: '1.0',
    JS: '0.3',
    TS: '0.2',
    PY: '0.5',
    HTML: '1.0',
    CSS: '0.4',
    XML: '0.6',
    CPP: '0.1',
    C: '0.3',
};

export const initialFileInputList = {
    FILE_EXTENSION: '',
    SINGLE_COMMENT: '',
    MULTI_START_COMMENT: '',
    MULTI_END_COMMENT: '',
    WEIGHT: ''
}

export const DEFAULT_FILES = ['C', 'CPP', 'CSS', 'HTML', 'JAVA', 'JS', 'PY', 'TS', 'XML'];
    

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