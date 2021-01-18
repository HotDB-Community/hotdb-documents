# CHANGELOG

* [X] 初始化仓库，整理目录，压缩图片，导入word文档到markdown文档，保证唯一的一级标题
* [X] 补充和压缩图片，基于脚本修正markdown文件格式
* [X] 将库放到本地（避免莫名其妙访问不了某些库）
* [X] 编写代码将奇怪的表格转化为正确的markdown表格
* [ ] 修改产品介绍文档，去掉目标
* [ ] README文档下方放上管理平台首页大图/多栏文档目录
* [ ] 检查markdown文档的格式
* [ ] 检查markdown文档中的文档和标题链接
* [ ] 为markdown文档中的代码块统一注明语言
* [ ] 寻找或者自定义插件实现markdown尾注语法（`text[^1]`）
* [X] 自定义实现markdown锚点语法（`# Title {#id}`）

# TODO

* [X] 确定目录结构
* [X] 修改edit-on-github插件，允许添加其他的github页面链接（如issue、pull request）
* [X] 自定义页面，edit-on-github按钮左侧添加编辑图标
* [ ] 自定义页面，侧边栏导航右侧添加菜单图标
* [ ] 修改docsify-glossary插件，允许不同语言的术语表（自行实现）
* [ ] 编写脚本合并所有文档并生成pdf
* [ ] 编写脚本从markdown生成toc
* [ ] 编写脚本为markdown的每个标题添加正确的序号
* [ ] 编写脚本使用正确的长度格式化markdown表格（中文字符的长度为2）

# 检查Markdown文档

> * D - 原文档已过时
> * T - 待编写
> * L - 优化列表，将
> * C - 标注代码块语言
> * M - 必要的单词/命令标注为代码（`code`）

中文文档（截止时间：2021-01-14 15:00:21）

* `introduce`
  * [X] `docs/zh/latest/introduce.md`
  * [X] `docs/zh/latest/white-paper.md` 
  * [ ] `docs/zh/latest/whats-new.md` T
* `quick-start`
  * [X] `docs/zh/latest/quick-start-guide.md`
  * [ ] `docs/zh/latest/basic-operations.md` T
* `install-deploy-update`
  * [X] `docs/zh/latest/install-and-deploy.md`
  * [X] `docs/zh/latest/service-license.md`
  * [X] `docs/zh/latest/manual-update.md`
  * [X] `docs/zh/latest/cluster-environment-requirement.md`
  * [X] `docs/zh/latest/hardware-config-recommendation.md`
* `hotdb-server`
  * [X] `docs/zh/latest/standard.md` D
  * [X] `docs/zh/latest/management-port-command.md` L C M
* `hotdb-management`
  * [X] `docs/zh/latest/hotdm-management.md`
  * [X] `docs/zh/latest/intelligent-inspection.md` L C
* `idc`
  * [X] `docs/zh/latest/cross-idc-disaster-recovery.md` C
  * [X] `docs/zh/latest/visual-idc.md` L C
* `appendix`
  * [X] `docs/zh/latest/glossary.md`
  * [X] `docs/zh/latest/parameters.md`
  * [X] `docs/zh/latest/error-codes.md`

英文文档（截止时间：2021-01-14 15:00:21）

* `introduce`
  * [X] `docs/zh/latest/introduce.md`
  * [X] `docs/zh/latest/white-paper.md` 
  * [ ] `docs/zh/latest/whats-new.md`
* `quick-start`
  * [X] `docs/zh/latest/quick-start-guide.md`
  * [ ] `docs/zh/latest/basic-operations.md`
* `install-deploy-update`
  * [X] `docs/zh/latest/install-and-deploy.md`
  * [X] `docs/zh/latest/service-license.md`
  * [X] `docs/zh/latest/manual-update.md`
  * [X] `docs/zh/latest/cluster-environment-requirement.md`
  * [X] `docs/zh/latest/hardware-config-recommendation.md`
* `hotdb-server`
  * [X] `docs/zh/latest/standard.md` D
  * [X] `docs/zh/latest/management-port-command.md` D
* `hotdb-management`
  * [X] `docs/zh/latest/hotdm-management.md`
  * [X] `docs/zh/latest/intelligent-inspection.md`
* `idc`
  * [X] `docs/zh/latest/cross-idc-disaster-recovery.md` D
  * [X] `docs/zh/latest/visual-idc.md`
* `appendix`
  * [X] `docs/zh/latest/glossary.md`
  * [X] `docs/zh/latest/parameters.md`
  * [X] `docs/zh/latest/error-codes.md`