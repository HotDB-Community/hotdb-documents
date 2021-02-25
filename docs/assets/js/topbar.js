window.$docsify.topbar = {
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
}

window.$docsify.plugins.push(
  function(hook) {
    hook.afterEach(function(html) {
      return createTopBar() + html
    })
  })

//添加topbar
function createTopBar() {
  return `<div class="topbar">
        <ul>
		<li>
          <a href="${window.$docsify.topbar.downloadUrl}"  target="_blank"
           title="${getText(window.$docsify.topbar.downloadTitle)}">
            ${getText(window.$docsify.topbar.downloadText)}
          </a class="topbar-link">
		</li>
		<li>
          <a href="${window.$docsify.topbar.editUrl}${window.$docsify.fileName}" target="_blank"
           title="${getText(window.$docsify.topbar.editTitle)}">
            ${getText(window.$docsify.topbar.editText)}
          </a class="topbar-link">
		</li>
		<li>
          <a href="${window.$docsify.topbar.issuesUrl}" target="_blank"
           title="${getText(window.$docsify.topbar.issuesTitle)}">
            ${getText(window.$docsify.topbar.issuesText)}
          </a>
		</li>
      </div>`
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