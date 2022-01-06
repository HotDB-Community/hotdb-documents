window.$docsify.topbar = {
  //TODO 考虑使用度盘
  downloadUrl: "https://hotpu-1257128263.cos.ap-shanghai.myqcloud.com/%E5%AF%B9%E5%A4%96%E5%BC%80%E6%94%BE%E4%B8%8B%E8%BD%BD%E6%96%87%E6%A1%A3/%E7%83%AD%E7%92%9E%E6%95%B0%E6%8D%AE%E5%BA%93HotDB%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C-2.5.6--V2.0.rar",
  downloadText: {
    "/zh/": "<i class='fa fa-download'></i> 下载文档",
    "/en/": "<i class='fa fa-download'></i> Download Document"
  },
  downloadTitle: {
    "/zh/": "下载整合的PDF文档",
    "/en/": "Download integrated pdf document"
  },
  editUrl: `https://github.com/HotDB-Community/hotdb-documents/blob/master/docs`,
  editText: {
    "/zh/": "<i class='fa fa-edit'></i> 编辑文档",
    "/en/": "<i class='fa fa-edit'></i> Edit Document"
  },
  editTitle: {
    "/zh/": "在Github上编辑文档",
    "/en/": "Edit document on Github"
  },
  issuesUrl: `https://github.com/HotDB-Community/hotdb-documents/issues`,
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
      const locales = window.$docsify.locales
      const fileUrl = window.$docsify.properties.fileUrl
      if(locales.some(it=> fileUrl.indexOf(it) !== -1)) {
        return createTopBar() + html
      }else{
        return html
      }
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
          <a href="${window.$docsify.topbar.editUrl}/${window.$docsify.properties.filePath}" target="_blank"
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