# 分布式事务数据库 HotDB Server

## 产品介绍

HotDB Server是一款实现数据容量和性能横向扩展的交易关系型分布式事务数据库产品。它兼容主流数据库协议和 SQL92/SQL99/SQL2003标准语法，支持自动水平拆分和垂直拆分，能在数据存储分布式化环境下为应用提供集中式数据库的操作体验。为大规模用户、大规模数据、高可用、高并发、高吞吐的业务场景提供强有力的支撑，同时具备强分布式透明、易扩展、易运维、无学习成本等特点。让研发工程师专注应用程序编码实现，无需关心数据的存放位置和操作位置等细节，让数据库工程师更轻松地管理海量数据和海量吞吐的数据库集群，同时提供数据安全、数据容灾、数据恢复、集群监控、智能拓扑、智能大屏、不停机扩容等整套解决方案，适用于TB或PB级的海量数据业务交易场景。

## 产品优势

**分布式**

数据分布式集群存储，业务数据支持分库分表，同时提供多种分片算法与表类型，满足用户所需的业务场景。

**强一致**

支持数据强一致性，对全局表、全局序列、分布式事务、副本数据一致、表结构一致、环境配置等都有强一致保障和一致性检测的算法机制。

**智能运维**

可视化参数配置与参数合理性校验，多线程自动化备份，数据态势感知与业务大屏展示，便捷的一键部署整套集群服务，智能数据正确性检测与异常故障及时报警。

**高可用**

计算节点、配置库、底层数据库之间实现三重高可用，不会因为主节点宕机而造成无法提供服务的问题，数据服务的可靠性达到99.999%。

**强透明**

对应用程序全透明，底层在线扩容、备份、OnlineDDL等操作对应用无感知，支持JDBC协议，支持MySQL原生通讯协议，兼容 MySQL 数据库协议及 SQL92 标准语法，覆盖99.9%以上应用开发常用SQL，支持多种数据单库/跨库操作。

**高性能**

计算节点的吞吐量能达到10万以上连接数、10万以上并发数、10万以上TPS、1000万以上QPS。

## 核心功能

**数据分片**

利用逻辑库方式分库，隔离不同业务属性的数据

可垂直或水平拆分大表，让操作集中于少量数据

内置满足不同业务场景的拆分算法和丰富的表类型

**分布式事务**

支持显示分布式事务与隐式分布式事务

支持实时强一致分布式事务

对于应用程序及客户端mysql命令操作全透明

**跨库操作**

跨库JOIN

跨库UNION

跨库聚合函数

跨库分组排序等

**弹性操作**

通过管理界面实现一键迁库，在线平滑扩容

在线增加只读节点，配置读写分离比重

支持单库向分布式集群的快速迁移

**容灾备份**

保证分布式数据库全局的时间点以及数据状态一致

备份对业务服务无阻塞

支持分布式下的库级别数据备份

支持加密备份文件、计算文件MD5值、备份至远程等功能

**读写分离**

支持数据读写分离功能，且在线设置读写权重

主从延时过大的读节点将自动从读集群中被剔除待恢复正常后再自动加入读集群中

读写分离对上层应用透明，不限制任何SQL语句下发或需要特殊语法处理

**过载保护**

支持对前端连接数总数和用户连接数的限制

控制HotDB发往数据源执行的SQL并发量，保护数据源之间负载平衡，防止某一个数据源因压力过大而宕机

**数据校验**

主备数据一致性检测，支持定时检测与异常检测结果提醒

全局表数据一致性校验

数据路由正确性结果检测

表结构与表索引检测

**在线表结构变更**

管理页面直接进行在线表结构变更操作

执行在线表结构变更期间不阻塞线上业务读写

所有正在执行与执行完成的在线表结构变更记录支持在线查看

## 适用场景

* 高可用
* 高性能
* 海量吞吐
* 海量连接
* 海量并发
* 海量数据
* 海量用户
