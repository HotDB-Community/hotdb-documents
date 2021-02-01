const repositoryUrl = "https://github.com/HotDB-Community/hotdb-documents"

window.$docsify = {
  name: "HotDB Documents",
  nameLink: {
    "/zh/": "#/zh/latest/",
    "/en/": "#/en/latest/"
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
  mergeNavbar: true,
  maxLevel: 3,
  subMaxLevel: 3,
  notFoundPage: true,
  search: {
    noData: {
      "/zh/": "没有结果！",
      "/en/": "No results!"
    },
    path: "auto",
    placeholder: {
      "/zh/": "搜索",
      "/en/": "Search"
    }
  },
  pagination: {
    previousText: {
      "/zh/": "上一章节",
      "/en/": "Previous Chapter",
    },
    nextText: {
      "/zh/": "下一章节",
      "/en/": "Next Chapter",
    },
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

  topbar: {
    downloadUrl: "javascript:void(0)", //TODO
    downloadText: {
      "/zh/": "<i class='fa fa-download'></i> 下载文档",
      "/en/": "<i class='fa fa-download'></i> Download Document"
    },
    downloadTitle: {
      "/zh/": "下载整合的PDF文档",
      "/en/": "Download integrated pdf document"
    },
    editUrl: `${repositoryUrl}/blob/master/docs`,
    editText: {
      "/zh/": "<i class='fa fa-edit'></i> 编辑文档",
      "/en/": "<i class='fa fa-edit'></i> Edit Document"
    },
    editTitle: {
      "/zh/": "在Github上编辑文档",
      "/en/": "Edit document on Github"
    },
    issuesUrl: `${repositoryUrl}/issues`,
    issuesText: {
      "/zh/": "<i class='fa fa-comment'></i> 反馈问题",
      "/en/": "<i class='fa fa-comment'></i> Report Issues"
    },
    issuesTitle: {
      "/zh/": "在Github上反馈问题",
      "/en/": "Report Issues on Github"
    }
  },

  plugins: [
    function(hook, vm) {
      //console.log(vm)

      hook.init(function() {
        redirectLocation()
      })

      hook.beforeEach(function(html) {
        //console.log(html)

        //绑定window.$docsify.fileName，以斜线开始
        window.$docsify.fileName = `/${vm.route.file}`
        //绑定windows.$docsify.fileUrl，以#开始，没有文件后缀名
        window.$docsify.fileUrl = `#/${vm.route.path}`

        //预处理markdown
        return resolveFootNote(resolveAnchor(escapeCode(html)))
      })
      hook.afterEach(function(html) {
        //console.log(html)
        
        //添加topbar
        return createTopBar() + resolveRowSpanAndColSpan(html)
      })
      hook.doneEach(function() {
        $(document).ready(function() {
          bindFootNote()
        })
      })
    }
  ],

  fileName: "",
  fileUrl: ""
}

//地址重定向
function redirectLocation() {
  let url = window.location.href
  if(url.charAt(url.length - 1) === "/") url = url.substring(0, url.length - 1)
  if(url.indexOf("/#") === -1) {
    window.location.replace(`${url}/#/zh/latest/`)
  } else if(url.endsWith("/#")) {
    window.location.replace(`${url}/zh/latest/`)
  } else if(url.endsWith("/#/zh")) {
    window.location.replace(`${url}/latest/`)
  } else if(url.endsWith("/#/en")) {
    window.location.replace(`${url}/latest/`)
  }
}

const codeWithPipeCharRegex = /(`[^`\r\n]+`)/g

//需要转义内联代码中的管道符，需要将`ps -ef | grep java`转义为`ps -ef \| grep java`，docsify的bug
function escapeCode(html){
  return html.replace(codeWithPipeCharRegex,(s,c)=>{
    return c.replaceAll("|","\\|")
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

const anchorRegex = /{#([\w-]+)}/g

//解析markdown锚点，绑定heading的id
function resolveAnchor(html) {
  return html.replace(anchorRegex, " :id=$1")
}

function createTopBar() {
  return `<div class="topbar">
        <p style="float: right">
          <a class="topbar-link" href="${window.$docsify.topbar.downloadUrl}"  target="_blank"
           title="${getText(window.$docsify.topbar.downloadTitle)}">
            ${getText(window.$docsify.topbar.downloadText)}
          </a class="topbar-link">
          <a class="topbar-link" href="${window.$docsify.topbar.editUrl}${window.$docsify.fileName}" target="_blank"
           title="${getText(window.$docsify.topbar.editTitle)}">
            ${getText(window.$docsify.topbar.editText)}
          </a class="topbar-link">
          <a class="topbar-link" href="${window.$docsify.topbar.issuesUrl}" target="_blank"
           title="${getText(window.$docsify.topbar.issuesTitle)}">
            ${getText(window.$docsify.topbar.issuesText)}
          </a>
        </p>
      </div>`
}

const rowSpanOrColSpanRegex = /<td>([\^<])<\/td>/g

//处理合并单元格的特殊语法
function resolveRowSpanAndColSpan(html){
  return html.replace(rowSpanOrColSpanRegex,(s,c)=>{
     return c==="^" ? `<td class="rowspan"></td>`:`<td class="colspan"></td>>`
  })  
}

//绑定footNote对应的tooltip
function bindFootNote() {
  $('[data-toggle="tooltip"]').tooltip()
}


//得到基于语言的文本
function getText(text) {
  if(typeof text === "string") {
    return text
  } else if(typeof text === "object") {
    const url = window.location.href
    for(const key in text) {
      if(url.indexOf(`/#${key}`) !== -1) {
        return text[key]
      }
    }
  }
}