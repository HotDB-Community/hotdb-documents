const repositoryUrl = "https://github.com/HotDB-Community/hotdb-documents"
const officialWebsiteUrl = "https://www.hotdb.com"
const latestVersion = "2.5.6.1"

window.$docsify = {
  nameLink: {
    "/zh/": `#/zh/${latestVersion}/`,
    "/en/": `#/en/${latestVersion}/`
  },
  repo: repositoryUrl,
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
  },

  markdown: {
    renderer: {
      //rowspan和colspan的渲染器
      tablecell(content, flags) {
        if(content === "^") return `<td class="rowspan"></td>`
        else if(content === "<") return `<td class="colspan"></td>>`
        else return `<td>${content}</td>`
      }
    }
  },

  plugins: [
    function(hook, vm) {
      //console.log(vm)

      hook.init(function() {
        redirectLocation()
        bindServiceCssClass()
      })

      hook.beforeEach(function(html) {
        //console.log(html)

        //绑定window.$docsify.fileName，以斜线开始
        window.$docsify.fileName = `/${vm.route.file}`
        //绑定windows.$docsify.fileUrl，以#开始，没有文件后缀名
        window.$docsify.fileUrl = `#/${vm.route.path}`

        //通过postMessage向官网发送消息
        //const message = {
        //  fileName: window.$docsify.fileName,
        //  fileUrl: window.$docsify.fileUrl
        //} 
        //window.postMessage(message,officialWebsiteUrl)

        //预处理markdown
        return resolveFootNote(resolveAnchor(escapeCode(html)))
      })
      hook.doneEach(function() {
        $(document).ready(function() {
          bindFootNote()
        })
      })
    }
  ],

  fileName: "",
  fileUrl: "",
  isMobile: false
}

window.onload = function() {
  redirectLocation()
  bindServiceCssClass()
}

//推断语言区域
function inferLocale() {
  const locale = navigator.language
  if(locale.startsWith("zh")) {
    return "zh"
  } else if(locale.startsWith("en")) {
    return "en"
  } else {
    return "en"
  }
}

//地址重定向
function redirectLocation() {
  let url = window.location.href
  if(url.charAt(url.length - 1) === "/") url = url.substring(0, url.length - 1)
  if(url.indexOf("/#") === -1) {
    window.location.replace(`${url}/#/${inferLocale()}/${latestVersion}/`)
  } else if(url.endsWith("/#")) {
    window.location.replace(`${url}/${inferLocale()}/${latestVersion}/`)
  } else if(url.endsWith("/#/zh")) {
    window.location.replace(`${url}/${latestVersion}/`)
  } else if(url.endsWith("/#/zh/latest")) {
    window.location.replace(`${url.substring(0, url.length - 7)}/${latestVersion}/`)
  } else if(url.endsWith("/#/en")) {
    window.location.replace(`${url.substring(0, url.length - 7)}/${latestVersion}/`)
  } else if(url.endsWith("/#/en/latest")) {
    window.location.replace(`${url}/${latestVersion}/`)
  }
}

//绑定判断设备的css class
function bindServiceCssClass() {
  const isMobile = /ipad|iphone|ipod|android|blackberry|windows phone|opera mini|silk/i.test(navigator.userAgent)
  const bodyElement = document.querySelector("body")
  if(isMobile) {
    bodyElement.classList.add("mobile")
    window.$docsify.isMobile = true
  } else {
    bodyElement.classList.add("web")
  }
}

const codeRegex = /(`[^`\r\n]+`)/g
const pipeCharRegex = /\|/g

//需要转义内联代码中的管道符，需要将`ps -ef | grep java`转义为`ps -ef \| grep java`，docsify的bug
function escapeCode(html) {
  return html.replace(codeRegex, (s, c) => {
    return c.replace(pipeCharRegex, "\\|")
  })
}

const anchorRegex = /([^\r\n]*?){#([^\r\n}]+)}/g

//解析markdown锚点，绑定heading的id
function resolveAnchor(html) {
  return html.replace(anchorRegex, (s, prefix, id) => {
    if(prefix.startsWith("#")) return `${prefix} :id=${id}`
    else return `${prefix}<span id="${id}"></span>`
  })
}

const footNoteRegex = /\[\^(\d+)](?!: )/g

const footNoteReferenceRegex = /^\[\^(\d+)]:\s*(.*)$/gm

//解析markdown尾注，生成bootstrap4 tooltip
function resolveFootNote(html) {
  const footNotes = {}
  return html.replace(footNoteReferenceRegex, (s, p1, p2) => {
    footNotes[p1] = p2
    return ""
  }).replace(footNoteRegex, (s, p1) => {
    return `<a href="javascript:void(0);" data-toggle="tooltip" title="${footNotes[p1]}">[${p1}]</a>`
  })
}

//绑定footNote对应的tooltip
function bindFootNote() {
  $('[data-toggle="tooltip"]').tooltip()
}