# TODO

* [X] 初始化仓库，整理目录，压缩图片，导入word文档到markdown文档，保证唯一的一级标题
* [X] 补充和压缩图片，基于脚本修正markdown文件格式
* [X] 将库放到本地（避免莫名其妙访问不了某些库）
* [X] 编写代码将奇怪的表格转化为正确的markdown表格
* [X] 修改产品介绍文档，去掉目标
* [X] README文档下方放上多栏文档目录
* [X] 修改edit-on-github插件，允许添加其他的github页面链接（如issue、pull request）
* [X] 寻找或者自定义插件实现markdown尾注语法（`text[^1]`）
* [X] 自定义实现markdown锚点语法（`# Title {#id}`）
* [X] 格式化所有markdown文档
* [X] 为所有中文文档适用markdown警告框语法（`> !!!NOTE`)
* [X] 更新图片，修复英文单词之间没有空格的问题
* [X] 更新图片：中文管理平台、跨机房灾备、白皮书
* [ ] 检查markdown文档的格式
* [ ] 检查markdown文档中的文档和标题链接
* [ ] 为markdown文档中的代码块
* [ ] 润色markdown文档中的内联代码
* [ ] 修改docsify-glossary插件，允许不同语言的术语表（自行实现）
* [ ] 编写脚本合并所有文档并生成pdf
* [ ] 编写脚本从markdown生成toc
* [ ] 编写脚本为markdown的每个标题添加正确的序号
* [ ] 编写脚本使用正确的长度格式化markdown表格（中文字符的长度为2）

# 检查Markdown文档

> * D - 原文档已过时
> * T - 待编写
> * L - 优化列表
> * F - 适用代码块codeFence
> * M - 必要的单词/命令标注为代码
> * P - 润色，适用警告框（NOTE/TIP/WARNING等）、尾注等特殊语法
> * C - 中英文对比校对
> * 任务状态 - 是否已经具有良好的浏览效果

中文文档（截止时间：2021-01-14 15:00:21）

* `introduce`
  * [X] `docs/zh/latest/introduce.md` F P
  * [X] `docs/zh/latest/white-paper.md` F P
  * [ ] `docs/zh/latest/whats-new.md` T
* `quick-start`
  * [X] `docs/zh/latest/quick-start-guide.md` F
  * [ ] `docs/zh/latest/basic-operations.md` T
* `install-deploy-update`
  * [X] `docs/zh/latest/install-and-deploy.md` F C
  * [X] `docs/zh/latest/service-license.md` F C
  * [X] `docs/zh/latest/manual-update.md` F C
  * [X] `docs/zh/latest/cluster-environment-requirement.md` F
  * [X] `docs/zh/latest/hardware-config-recommendation.md` F
* `hotdb-server`
  * [X] `docs/zh/latest/standard.md` F
  * [X] `docs/zh/latest/management-port-command.md` L M F
* `hotdb-management`
  * [X] `docs/zh/latest/hotdm-management.md` F
  * [X] `docs/zh/latest/intelligent-inspection.md` L F C
* `idc`
  * [X] `docs/zh/latest/cross-idc-disaster-recovery.md` F C
  * [X] `docs/zh/latest/visual-idc.md` L F C
* `appendix`
  * [X] `docs/zh/latest/glossary.md` F C
  * [X] `docs/zh/latest/parameters.md` F C
  * [X] `docs/zh/latest/error-codes.md` F C

英文文档（截止时间：2021-01-14 15:00:21）

* `introduce`
  * [X] `docs/en/latest/introduce.md` P
  * [X] `docs/en/latest/white-paper.md` P 
  * [ ] `docs/en/latest/whats-new.md` T
* `quick-start`
  * [X] `docs/en/latest/quick-start-guide.md`
  * [ ] `docs/en/latest/basic-operations.md` T
* `install-deploy-update`
  * [X] `docs/en/latest/install-and-deploy.md` C
  * [X] `docs/en/latest/service-license.md` C
  * [X] `docs/en/latest/manual-update.md` C
  * [X] `docs/en/latest/cluster-environment-requirement.md`
  * [X] `docs/en/latest/hardware-config-recommendation.md`
* `hotdb-server`
  * [ ] `docs/en/latest/standard.md` D
  * [ ] `docs/en/latest/management-port-command.md` D
* `hotdb-management`
  * [ ] `docs/en/latest/hotdm-management.md`
  * [X] `docs/en/latest/intelligent-inspection.md` C
* `idc`
  * [X] `docs/en/latest/cross-idc-disaster-recovery.md` C
  * [X] `docs/en/latest/visual-idc.md` C
* `appendix`
  * [X] `docs/en/latest/glossary.md` C
  * [X] `docs/en/latest/parameters.md` C
  * [X] `docs/en/latest/error-codes.md` C
  
# 问题

* 参数说明，allowRCWithoutReadConsistentInXA，中英文不一致
* 计算节点错误码 英文，未翻译
