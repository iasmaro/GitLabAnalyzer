import { initialFileInputList, DEFAULT_FILES } from 'Constants/constants';

const setInitialFileInputList = (configInfo) => {
    if (!configInfo || !configInfo.commentTypes || !configInfo.fileFactor) {
        return [initialFileInputList];
    }

    const newFiles = [];

    for (let file in configInfo.fileFactor) {
        if (!DEFAULT_FILES.includes(file)) {
            const newFile = { FILE_EXTENSION: file, WEIGHT: configInfo.fileFactor[file] };
            for (let commentType of configInfo.commentTypes[file]) {
                if (commentType.startType && commentType.endType.length === 0 && !newFile.SINGLE_COMMENT) {
                    newFile.SINGLE_COMMENT = commentType.startType;
                } else {
                    newFile.MULTI_START_COMMENT = commentType.startType;
                    newFile.MULTI_END_COMMENT = commentType.endType;
                }
            }
            newFiles.push(newFile);
        }
    }

    for (let file in configInfo.commentTypes) {
        if (!configInfo.fileFactor[file]) {
            const newFile = { FILE_EXTENSION: file, WEIGHT: 0 };
            for (let commentType of configInfo.commentTypes[file]) {
                if (commentType.startType && commentType.endType.length === 0 && !newFile.SINGLE_COMMENT) {
                    newFile.SINGLE_COMMENT = commentType.startType;
                } else {
                    newFile.MULTI_START_COMMENT = commentType.startType;
                    newFile.MULTI_END_COMMENT = commentType.endType;
                }
            }
            newFiles.push(newFile);
        }
    }

    return newFiles;
}

export default setInitialFileInputList;