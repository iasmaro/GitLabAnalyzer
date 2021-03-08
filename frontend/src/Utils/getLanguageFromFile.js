import { PROGRAMMING_LANGUAGES } from 'Constants/constants';

const getLanguageFromFile = (file) => {
    const fileExtension =  file.split('.').pop();
    const language = PROGRAMMING_LANGUAGES[fileExtension] || 'markdown';
    return language;
};

export default getLanguageFromFile;