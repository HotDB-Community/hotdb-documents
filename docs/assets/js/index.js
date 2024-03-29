window.$docsify = {
  version: "1.0.0",
  locales: ["zh", "en"],
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

  properties: {
    defaultVersion: "2.5.6.1",
    fileName: null,
    filePath: null,
    fileUrl: null,
    language: null,
    version: null
  },

  vueGlobalOptions: {
    data(){
      return {

      }
    },
    computed(){
      return {
        ...window.$docsify.properties
      }
    },
    methods(){
      return {
        fileUrlWithLanguage(language){
          return this.fileUrl.replace(this.language, language)
        },
        fileUrlWithVersion(version){
          return this.fileUrl.replace(this.version, version)
        }
      }
    }
  },

  search: {
    path: "auto",
    noData: {
      "/zh/": "没有结果！",
      "/en/": "No results!",
      "/": "没有结果！"
    },
    placeholder: {
      "/zh/": "搜索文档",
      "/en/": "Search Document",
      "/": "搜索文档"
    },
    depth: 6,
    hideOtherSidebarContent: false
  },
  pagination: {
    previousText: "Prev",
    nextText: "Next",
    crossChapter: true,
    crossChapterText: true
  },
  copyCode: {
    buttonText: "Copy Code",
    errorText: "Error",
    successText: "Copied"
  },
  "flexible-alerts": {
    style: 'callout', //flat, callout
    note: {
      label: {
        "/zh/": "注意",
        "/en/": "Note",
        "/": "注意"
      }
    },
    tip: {
      label: {
        "/zh/": "提示",
        "/en/": "Tip",
        "/": "提示"
      }
    },
    warning: {
      label: {
        "/zh/": "警告",
        "/en/": "Warning",
        "/": "警告"
      }
    },
    info: {
      label: {
        "/zh/": "说明",
        "/en/": "Information",
        "/": "说明"
      },
      icon: "fa fa-info-circle",
      className: "info"
    },
    important: {
      label: {
        "/zh/": "特别说明",
        "/en/": "Important Information",
        "/": "特别说明"
      },
      icon: "fa fa-info-circle",
      className: "important"
    }
  }
}