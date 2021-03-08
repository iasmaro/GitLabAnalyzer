import { PROGRAMMING_LANGUAGES } from 'Constants/constants';

const getLanguageFromFile = (file) => {
    const fileName = file.split('/').pop();
    const fileExtension =  fileName.split('.').pop();
    const language = PROGRAMMING_LANGUAGES[fileExtension.toLowerCase()] || 'markdown';
    return language;
};

export default getLanguageFromFile;