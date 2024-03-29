//为什么extensions不生效？
//window.$docsify.markdown = function(){...} 会使":id=foo"这样的语法不生效

const renderer = {
  //渲染rowspan和colspan
  tablecell(content, flags) {
    if(content === "^") {
      return `<td class="rowspan"></td>`
    } else if(content === "&lt;") { //不是"<"
      return `<td class="colspan"></td>`
    } else {
      return `<td>${content}</td>`
    }
  }
}

//const anchor = {
//  name: "anchor",
//  level: "inline",
//  start(src) {
//    console.log(src)
//    return src.matches(/{#/)?.index
//  },
//  tokenizer(src, tokens) {
//    let group = /({#.+?})/.exec(src)
//    if(group) {
//      return {
//        type: "anchor",
//        raw: group[0]
//      }
//    }
//  },
//  renderer(token) {
//    return `<span id="${id}"></span>`
//  }
//}
//
//const descriptionlist = {
//  name: 'descriptionList',
//  level: 'block',                                     // Is this a block-level or inline-level tokenizer?
//  start(src) {
//    return src.match(/:[^:\n]/)?.index
//  }, // Hint to Marked.js to stop and check for a match
//  tokenizer(src, tokens) {
//    const rule = /^(?::[^:\n]+:[^:\n]*(?:\n|$))+/    // Regex for the complete token
//    const match = rule.exec(src)
//    if(match) {
//      const token = {                                 // Token to generate
//        type: 'descriptionList',                      // Should match "name" above
//        raw: match[0],                                // Text to consume from the source
//        text: match[0].trim(),                        // Additional custom properties
//        tokens: []                                    // Array where child inline tokens will be generated
//      }
//      this.lexer.inline(token.text, token.tokens)    // Queue this data to be processed for inline tokens
//      return token
//    }
//  },
//  renderer(token) {
//    return `<dl>${this.parser.parseInline(token.tokens)}\n</dl>` // parseInline to turn child tokens into HTML
//  }
//}
//
//const description = {
//  name: 'description',
//  level: 'inline',                                 // Is this a block-level or inline-level tokenizer?
//  start(src) {
//    return src.match(/:/)?.index
//  },    // Hint to Marked.js to stop and check for a match
//  tokenizer(src, tokens) {
//    const rule = /^:([^:\n]+):([^:\n]*)(?:\n|$)/  // Regex for the complete token
//    const match = rule.exec(src)
//    if(match) {
//      return {                                         // Token to generate
//        type: 'description',                           // Should match "name" above
//        raw: match[0],                                 // Text to consume from the source
//        dt: this.lexer.inlineTokens(match[1].trim()),  // Additional custom properties, including
//        dd: this.lexer.inlineTokens(match[2].trim())   //   any further-nested inline tokens
//      }
//    }
//  },
//  renderer(token) {
//    return `\n<dt>${this.parser.parseInline(token.dt)}</dt><dd>${this.parser.parseInline(token.dd)}</dd>`
//  },
//  childTokens: ['dt', 'dd'],                 // Any child tokens to be visited by walkTokens
//  walkTokens(token) {                        // Post-processing on the completed token tree
//    if(token.type === 'strong') {
//      token.text += ' walked'
//    }
//  }
//}

window.$docsify.markdown = {
  renderer: renderer
}

//window.$docsify.markdown = function(marked, render) {
//  marked.use({renderer: renderer})
//  marked.use({extensions: [anchor]})
//  marked.use({extensions: [descriptionlist,description]})
//  console.log(marked.Lexer.rules)
//  console.log(marked('A Description List:\n'
//    + ':   Topic 1   :  Description 1\n'
//    + ': **Topic 2** : *Description 2*'));
//  return marked
//}

window.$docsify.plugins = [
  function(hook, vm) {
    hook.init(function() {
      redirectLocation()
    })
    hook.beforeEach(function(html) {
      redirectLocation()
      html = prehandleMarkdown(html)
      bindProperties(vm)
      return html
    })
    hook.afterEach(function(html, next) {
      next(html)
    })
    hook.doneEach(function() {
      $(document).ready(function() {
        bindFootNote()
      })
    })
  }
]

//地址重定向
function redirectLocation() {
  let defaultVersion = window.$docsify.properties.defaultVersion
  let defaultVersionSuffix = defaultVersion ? defaultVersion + "/" : ""
  let locale = inferLocale()
  let url = window.location.href
  if(url.charAt(url.length - 1) === "/") url = url.substring(0, url.length - 1)
  if(url.indexOf("/#") === -1) {
    window.location.replace(`${url}/#/${locale}/${defaultVersionSuffix}`)
  } else if(url.endsWith("/#")) {
    window.location.replace(`${url}/${locale}/${defaultVersionSuffix}`)
  } else{
    const locales = window.$docsify.locales
    locales.forEach(it=>{
      if(url.endsWith(`/#/${it}`)){
        window.location.replace(`${url}/${defaultVersionSuffix}`)
      }
    })
  }
}

//推断语言区域
function inferLocale() {
  const locales = window.$docsify.locales
  const locale = navigator.language
  locales.forEach(it =>{
    if(locale.startsWith(it)) return it
  })
  return "zh"
}

//预处理markdown
function prehandleMarkdown(html){
  let isCodeFence = false
  return html.split("```").map(snippet =>{
    if(isCodeFence){
      isCodeFence = false
      return snippet
    }else{
      isCodeFence = true
      snippet = escapeInCode(snippet)
      snippet = resolveAnchor(snippet)
      snippet = resolveFootNote(snippet)
      return snippet
    }
  }).join("```")
}

//需要转义表格单元格中的内联代码中的管道符
function escapeInCode(html) {
  return html.replace(/^[ \t]*\|(.*)\|[ \t]*$/gm, (s, content) => {
    return "|" + content.replace(/`([^`]+)`/g, (ss, c) => {
      return "`" + c.replace("|", "\\|") + "`"
    }) + "|"
  })
}

//解析markdown锚点，绑定heading的id
function resolveAnchor(html) {
  return html.replace(/(.*?){#(.+?)}/g, (s, prefix, id) => {
    if(prefix.startsWith("#")) return `${prefix} :id=${id}`
    else return `${prefix}<span id="${id}"></span>`
  })
}

//解析markdown尾注，生成bootstrap4的tooltip
function resolveFootNote(html) {
  const footNotes = {}
  return html.replace(/^\[\^(\d+)]:\s*(.*)$/gm, (s, id, text) => {
    footNotes[id] = text
    return ""
  }).replace(/\[\^(\d+)](?!: )/g, (s, id) => {
    return `<a href="javascript:void(0);" data-toggle="tooltip" title="${footNotes[id]}">[${id}]</a>`
  })
}

//绑定footNote对应的tooltip
function bindFootNote() {
  $('[data-toggle="tooltip"]').tooltip()
}

//解析以{{expression}}格式表示的表达式（基于Vue实例）
function resolveExpression(html){
  return html.replace(/{{(\w+)}}/, (s, expression) => { return vm[expression]})
}

function bindProperties(vm) {
  //绑定自定义属性
  const properties = window.$docsify.properties
  properties.filePath = vm.route.file //格式：xxx/xxx.md
  properties.fileUrl = vm.route.path //格式：/xxx/xxx
  const strs = properties.filePath.split("/");
  properties.fileName = strs.length > 1 ? strs[strs.length -1] : null;
  properties.language = strs.length > 1 ? strs[0] : null;
  properties.version = strs.length > 2 ? strs[1] : null;
}