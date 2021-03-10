export const configsEmpty = [];
export const configs = [
    {
        configName: 'CMPT276 Iteration 1',
        startDate: 'February 20, 2021 @ 12:54:32',
        endDate: 'March 19, 2021 @ 17:30:00',
        codeWeightings: [
            {
                addLine: 1.0,
                deleteLine: 0.2,
                comment: 0,
                spacingChange: 0.0,
                syntax: 0.2
            }
        ],
        fileWeightings: [
            {
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
        ]
    },
    {
        configName: 'CMPT353 Iteration 1',
        startDate: 'February 12, 2021 @ 10:30:00',
        endDate: 'March 1, 2021 @ 18:30:00',
        codeWeightings: [
            {
                addLine: 1.0,
                deleteLine: 0.25,
                comment: 0.1,
                spacingChange: 0.0,
                syntax: 0.25
            }
        ],
        fileWeightings: [
            {
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
                python: {
                    weight: 1.0,
                    startComment: '"""',
                    endComment: '"""'
                },
                c: {
                    weight: 1.0,
                    startComment: '/*',
                    endComment: '*/'
                },
                cpp: {
                    weight: 1.0,
                    startComment: '<!--',
                    endComment: '-->'
                },
                hs: {
                    weight: 1.0,
                    startComment: '/*',
                    endComment: '*/'
                },
                ruby: {
                    weight: 1.0,
                    startComment: '/*',
                    endComment: '*/'
                },
                R: {
                    weight: 1.0,
                    startComment: '"""',
                    endComment: '"""'
                }
            }
        ]
    },
]
