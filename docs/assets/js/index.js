window.$docsify = {
  version: "2.5.6.1",
  nameLink: {
    "/zh/": `#/zh/${this.version}/`,
    "/en/": `#/en/${this.version}/`
  },
  repo: "https://github.com/HotDB-Community/hotdb-documents",
  routeMode: "history",
  relativePath: true,
  auto2top: true,
  fallbackLanguages: ["zh"],
  //coverpage: ["/zh/", "/en/"], 
  //onlyCover: true,
  loadSidebar: true,
  loadNavbar: true,
  maxLevel: 4,
  subMaxLevel: 4,
  notFoundPage: true,
  topMargin: 80,

  search: {
    noData: {
      "/zh/": "没有结果！",
      "/en/": "No results!"
    },
    path: "auto",
    placeholder: {
      "/zh/": "搜索文档",
      "/en/": "Search Document"
    }
  },
  pagination: {
    previousText: "Prev",
    nextText: "Next",
    crossChapter: true,
    crossChapterText: true
  },
  copyCode: {
    buttonText: 'Copy Code',
    errorText: 'Error',
    successText: 'Copied'
  },
  "flexible-alerts": {
    style: 'callout', //flat, callout
    note: {
      label: {
        "/zh/": "注意",
        "/en/": "Note"
      }
    },
    tip: {
      label: {
        "/zh/": "提示",
        "/en/": "Tip"
      }
    },
    warning: {
      label: {
        "/zh/": "警告",
        "/en/": "Warning"
      }
    },
    info: {
      label: {
        "/zh/": "说明",
        "/en/": "Information"
      },
      icon: "fa fa-info-circle",
      className: "info"
    },
    important: {
      label: {
        "/zh/": "特别说明",
        "/en/": "Important Information"
      },
      icon: "fa fa-info-circle",
      className: "important"
    }
  }
}