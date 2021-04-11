import { initialConfigState } from 'Constants/constants';

const setInitialState = (configInfo) => {
    if (!configInfo || !configInfo.editFactor || !configInfo.fileFactor) {
        return initialConfigState;
    }

    const initialState = {
        CONFIGURATION_NAME: configInfo.fileName,
        ADD_NEW_LINE : configInfo.editFactor.addLine,
        DELETE_LINE : configInfo.editFactor.delLine,
        SYNTAX : configInfo.editFactor.syntax,
        MOVE_LINE : configInfo.editFactor.movLine,
        JAVA: configInfo.fileFactor.JAVA,
        JS: configInfo.fileFactor.JS,
        TS: configInfo.fileFactor.TS,
        PY: configInfo.fileFactor.PY,
        HTML: configInfo.fileFactor.HTML,
        CSS: configInfo.fileFactor.CSS,
        XML: configInfo.fileFactor.XML,
        CPP: configInfo.fileFactor.CPP,
        C: configInfo.fileFactor.C,
        FILE_EXTENSION: '',
        SINGLE_COMMENT: '',
        MULTI_START_COMMENT: '',
        MULTI_END_COMMENT: '',
        WEIGHT: ''
    };

    return initialState;
}

export default setInitialState;