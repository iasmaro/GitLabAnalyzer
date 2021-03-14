export const defaultConfig = [
    {
        configName: 'CMPT276 Iteration 1',
        startDate: 'February 20, 2021 @ 12:54:32',
        endDate: 'March 19, 2021 @ 17:30:00',
        codeWeightings: {
            addLine: 1.0,
            deleteLine: 0.2,
            comment: 0,
            spacingChange: 0.0,
            syntax: 0.2
        },
        fileTypes: {
            java: {
                weight: 1.0,
                startComment: '/*',
                endComment: '*/'
            },
            html: {
                weight: 1.0,
                startComment: '<!--',
                endComment: '-->'
            },
            css: {
                weight: 1.0,
                startComment: '/*',
                endComment: '*/'
            },
            js: {
                weight: 1.0,
                startComment: '/*',
                endComment: '*/'
            },
        }
    }
]
