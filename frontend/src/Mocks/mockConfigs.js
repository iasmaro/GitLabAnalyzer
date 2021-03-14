export const defaultConfig = {
    fileName: "default",
    start: "Repo creation date",
    end: (new Date()).toString(),
    targetBranch: "master",
    editFactor: {
      addLine: 1,
      deleteLine: 0.2,
      moveLine: 0.5,
      syntaxLine: 0.2
    },
    fileFactor: {
      html: 1,
      css: 1,
      cpp: 1,
      cxx: 1,
      java: 1,
      hpp: 1,
      hxx: 1,
      ts: 1,
      c: 1,
      js: 1,
      py: 1,
      h: 1,
      xml: 1
    },
    ignoreFileExtension: [],
    commentTypes: {
      html: [
        {
          "startType": "<!--",
          "endType": "-->"
        }
      ],
      css: [
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      cpp: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      cxx: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      java: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      hpp: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      hxx: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      ts: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      c: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      js: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      py: [
        {
          "startType": "#",
          "endType": ""
        },
        {
          "startType": "\"\"\"",
          "endType": "\"\"\""
        },
        {
          "startType": "'''",
          "endType": "'''"
        }
      ],
      h: [
        {
          "startType": "//",
          "endType": ""
        },
        {
          "startType": "/*",
          "endType": "*/"
        }
      ],
      xml: [
        {
          "startType": "<!--",
          "endType": "-->"
        }
      ]
    }
  }