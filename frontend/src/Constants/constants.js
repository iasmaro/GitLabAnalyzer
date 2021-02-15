const dev = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://localhost:3000/',
    SFU_AUTHENTICATION_URL: 'https://cas.sfu.ca/cas/serviceValidate?service=http://localhost:3000/'
}

const prod = {
    SFU_LOGIN_URL: 'https://cas.sfu.ca/cas/login?service=http://cmpt373-1211-11.cmpt.sfu.ca/',
    SFU_AUTHENTICATION_URL: 'https://cas.sfu.ca/cas/serviceValidate?service=http://cmpt373-1211-11.cmpt.sfu.ca/'
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod;