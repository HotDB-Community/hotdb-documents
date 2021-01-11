# 前言

为了更好的帮助用户对管理平台以及计算节点版本进行升级操作，特此编辑《分布式事务数据库HotDB Server【手动升级】功能使用手册》。该手册旨在帮助用户通过文档操作说明，独立手动完成管理平台与计算节点的基本升级。同时提供对版本升级出现异常时的处理意见。

该手册适用于管理平台或计算节点的任意版本升级。对部分版本升级操作需特殊处理的地方，文档会给出特殊说明，升级操作时需重点关注。建议大家严格按照手册的操作说明对管理平台或计算节点进行升级。

# 管理平台手动升级

管理平台升级到V2.5.3以下版本时目前只支持通过手动的方式进行，若升级到V2.5.3以及上版本时支持半自动化升级（手动替换包与更新配置文件，程序自动升级配置库）。当升级到V2.5.6版本及以上时，还需要额外对jdk进行升级。

## 配置库升级

部分版本升级时无配置库SQL变更（例如：同版本号中不同日期的版本更新升级），上述场景无需关注配置库升级模块。

### 停止管理平台服务

登录管理平台服务器执行停止服务命令：

\#sh /usr/local/hotdb/hotdb-management/bin/hotdb_management stop

### 备份配置库数据

升级配置库前必须先备份好配置库数据，防止升级过程中出现异常情况。

**使用mysqldump备份配置库数据**

\#mysqldump -S /data/mysql/mysqldata3316/sock/mysql.sock \--set-gtid-purged=off ---single-transaction \--master-data=1 \--databases hotdb_cloud_config \--default-character-set=utf8 -uroot \> /usr/local/hotdb/hotdb_cloud_config_2.5.3_20190815.sql

### 执行配置库升级脚本

**特殊说明：**若管理平台升级的目标版本大于等于V2.5.3，此步骤无需手动执行，可在管理平台替换好新版本程序包并更新完配置文件后，由程序完成配置库的升级操作。半自动升级管理平台配置库可参考《分布式事务数据库HotDB Server-V2.5.x【管理平台】功能使用手册》升级中心-\>管理平台配置库升级功能说明。

**执行配置库升级脚本**

1.  登录管理平台配置库

\#mysql -uhotdb_cloud -p -P3306 -Dhotdb_cloud_config -h127.0.0.1

2.  将升级内容复制到配置库中执行

根据具体升级脚本内容，执行变更SQL语句

特殊说明：

-   配置库升级脚本可寻找产品供应商获取。

-   复制升级脚本内容时注意区分管理平台配置库升级SQL与计算节点配置库升级SQL。

-   若管理平台是跨版本升级（例如从2.4.9-\>2.5.3）,则配置库脚本需要按照版本顺序一个版本一个版本执行，不可将顺序打乱或遗漏某一版本的升级内容。

-   配置库升级要求所有升级SQL语句执行成功，无报错内容。

-   如果采用复制粘贴到ssh终端的方式执行，注意设置终端的字符集为utf8。

### 配置库升级异常处理

**问题与处理方法**

1.  配置库升级SQL执行出现语法报错。

-   将执行的SQL报错信息保存，同时提供升级配置库的相关信息包括但不限于：配置库当前版本与升级目标版本、执行的升级SQL语句、配置库备份文件等，全部一起发送给产品供应商分析解决。

-   使用上一步骤备份的文件将配置库恢复，恢复期间请停止管理平台服务程序。

2.  非SQL语法错误的异常情况

-   根据错误提示，分析错误原因并解决。

-   使用上一步骤备份的文件将配置库恢复，恢复期间请停止管理平台服务程序。

-   问题修复且配置库恢复成功后，重新执行升级操作。

## 替换程序包

### 备份管理平台目录

\#cd /usr/local/hotdb/

\#mv hotdb-management hotdb_management_249

### 上传并解新版本包

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -xvf hotdb-management-2.4.9-ga-20190417.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-management

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-management

## 更新配置文件

### 修改application.properties配置文件

建议打开旧管理平台目录中的application.properties配置文件，对照着将变更的参数值同步更新至新版本目录下的配置文件中。

\# cd /usr/local/hotdb/hotdb-management/conf/

\# vi application.properties

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

\# http port

server.port=3324

\# ssl socket port

server.backup.port=3322

\# hotdb management database settings

spring.datasource.url=jdbc:mysql://192.168.210.30:3307/hotdb_cloud_config_253?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&connectTimeout=3000&socketTimeout=3600000

spring.datasource.username=hotdb_cloud

spring.datasource.password=hotdb_cloud

spring.datasource.driver-class-name=com.mysql.jdbc.Driver

......（此处省略部分参数，具体以实际application.properties为准）

\# tomcat connection pool specific settingshotdb.config.sqlFirewall.interceptType=0:\\u8BEF\\u64CD\\u4F5C,1:SQL\\u6CE8\\u5165,2:\\u4E0D\\u826F\\u64CD\\u4F5C,3:\\u8BEF\\u8BBE\\u7F6Ehotdb.server.log.days=14

## 升级JDK版本

若计算节点版本由低版本升级到V2.5.5（包含）以下版本时，可跳过这一步，默认JDK版本均为1.7；

若计算节点版本由低版本升级到V2.5.6（包含）以上版本时，需要对启动脚本bin/hotdb_management文件进行调整以适配OpenJDK8，具体操作如下：

### 上传并解压OpenJDK8安装包

**上传OpenJDK8安装包**

可使用rz命令或ftp文件传输工具上传OpenJDK8U-jdk_x64_linux_hotspot_8u252b09.tar.gz安装包，该安装包可联系热璞科技索要

**解压安装包**

\#mkdir -p /usr/local/jdk8

\#tar -xvf OpenJDK8U-jdk_x64_linux_hotspot_8u252b09.tar.gz -C /usr/local/jdk8

### 修改启动脚本

对启动脚本bin/hotdb_management脚本只需调整一下2处：

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--原配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

JAVA_BIN=\$shellPath/../../jdk/bin/java

JAVA_VERSION=\"1.7.0_80\"

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--新配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

JAVA_BIN=/usr/local/jdk8/jdk8u252-b09/bin/java

JAVA_VERSION=\"1.8.0_252\"

## 启动管理平台与启动异常处理

### 启动管理平台服务

\#cd /usr/local/hotdb/hotdb-management/bin/

\#sh hotdb_management start

### 启动异常处理

**问题与处理方案**

1.  管理平台日志报错，服务无法启动

-   将新版本目录重命名，并将旧目录重新命名为hotdb-management。

-   使用上一步骤备份的配置库文件将配置库恢复。

-   重新启动旧版本管理平台服务。

-   分析启动失败的问题，解决后再按照上述步骤重新升级。

# 计算节点手动升级

计算节点版本升级支持手动和自动两种方式，自动升级请参考《分布式事务数据库HotDB Server-V2.5.x【管理平台】功能使用手册》升级中心菜单说明。以下将通过手动的方式对单节点、主备节点、多节点模式的集群进行计算节点版本升级说明。

## 注意事项

### 升级JDK版本

若计算节点版本由低版本升级到V2.5.5（包含）以下版本时，可跳过这一步，默认JDK版本均为1.7；

若计算节点版本由低版本升级到V2.5.6（包含）以上版本时，需要对JDK版本进行升级至1.8，具体操作步骤请参考管理平台[升级JDK版本](#升级jdk版本)，同时也需要对启动脚本bin/hotdb_server文件中"JAVA_BIN"、"JAVA_VERSION"进行修改。

### 升级新版许可证

若计算节点版本由低版本升级到V2.5.5（包含）以下版本时，可跳过这一步，默认许可证无变化；

若计算节点版本由低版本升级到V2.5.6（包含）以上版本时，计算节点许可证升级为官方自研许可证，原程序包内keys/目录下许可证将无法使用，直接废弃；新版本包内keys/目录下默认存放90天、16节点试用版许可证，启动计算节点时自动激活。**若发现当前已有的节点数大于默认试用版的节点数**，则需要在升级之前参考《分布式事务数据库HotDB Server【服务授权】功能使用手册》V2.5.6 版本以上的文档进行机器码获取及许可证更新操作，否则可能导致计算节点服务无法正常启用。

## 单节点集群模式升级

### 停止计算节点服务

**登录计算节点服务器执行停止服务命令：**

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server stop

### 配置库升级

**特殊说明：**升级配置库前必须先备份好配置库数据，防止升级过程中出现异常情况。双主或MGR类型的配置库只备份当前主配置库的数据即可。部分版本升级时无配置库SQL变更（例如：同版本号中不同日期的版本更新升级），上述场景无需关注配置库升级模块。

**升级操作：**

计算节点配置库升级与管理平台配置库升级步骤一致，备份配置库、执行升级SQL、升级异常情况处理等操作说明请参考管理平台[配置库升级说明](#配置库升级)。

### 替换程序包

#### 备份计算节点目录 {#备份计算节点目录 .list-paragraph}

\#cd /usr/local/hotdb/

\#mv hotdb-server hotdb_server_253

#### 上传并解压新版本包 {#上传并解压新版本包 .list-paragraph}

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -xvf hotdb-server-2.5.4-ga-20190812.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-server

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-server

#### NDB SQL服务 {#ndb-sql服务 .list-paragraph}

若升级前的计算节点版本大于等于V2.5.3需要注意，之前备份的计算节点目录中是否包含NDB SQL服务（以ndbsql开头的目录）。若存在则需要将之前备份的计算节点目录中的NDB SQL目录重新拷贝到升级后的计算节点目录中。

**进入之前备份的计算节点目录下**

\#cd /usr/local/hotdb/hotdb_server_253

**查看计算节点目录下内容**

\#ll

drwxr-xr-x. 2 hotdb hotdb 4096 Aug 26 17:49 bin

drwxr-xr-x. 2 hotdb hotdb 4096 Aug 19 10:35 conf

drwxr-xr-x. 2 hotdb hotdb 4096 Aug 19 10:35 keys

drwxr-xr-x. 2 hotdb hotdb 4096 Aug 19 10:35 lib

drwxr-xr-x. 2 hotdb hotdb 4096 Jul 8 11:33 logs

lrwxrwxrwx. 1 root root 58 Aug 26 17:50 ndbsql_bin -\> /usr/local/mysql-cluster-gpl-7.5.12-linux-glibc2.12-x86_64

drwxr-xr-x. 3 hotdb hotdb 4096 Aug 26 17:53 ndbsql_data

drwxr-xr-x. 2 hotdb hotdb 4096 Aug 19 10:35 utils

**从备份目录中将NDB SQL内容拷贝到升级后的计算节点目录下**

\#cp -rp /usr/local/hotdb/hotdb_server_253/ndbsql\* /usr/local/hotdb/hotdb-server/

### 更新配置文件

#### server.xml配置文件 {#server.xml配置文件 .list-paragraph}

建议打开备份的计算节点目录中的server.xml配置文件，对照着将变更的参数值同步更新至新版本目录下的配置文件中。

\# cd /usr/local/hotdb/hotdb-server/conf/

\# vi server.xml

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

\<?xml version=\"1.0\" encoding=\"UTF-8\"?\>

\<!DOCTYPE hotdb:server SYSTEM \"server.dtd\"\>

\<hotdb:server xmlns:hotdb=\"http://cn.hotpu/\"\>

\<system\>

\<property name=\"url\"\>jdbc:mysql://192.168.210.30:3307/hotdb_config_test253\</property\>\<!\-- 主配置库地址，需指定配置库服务所在的真实IP地址 \--\>

\<property name=\"username\"\>hotdb_config\</property\>\<!\-- 主配置库用户名 \--\>

\<property name=\"password\"\>hotdb_config\</property\>\<!\-- 主配置库密码 \--\>

\<property name=\"bakUrl\"\>jdbc:mysql://192.168.210.30:3307/hotdb_config_test253\</property\>\<!\-- 从配置库地址，需指定配置库服务所在的真实IP地址 \--\>

\<property name=\"bakUsername\"\>hotdb_config\</property\>\<!\-- 从配置库用户名 \--\>

\<property name=\"bakPassword\"\>hotdb_config\</property\>\<!\-- 从配置库密码 \--\>

\<property name=\"configMGR\"\>false\</property\>\<!\-- 配置库是否使用MGR \--\>

\<property name=\"bak1Url\"\>\</property\>\<!\-- MGR配置库地址(如配置库使用MGR,必须配置此项)，需指定配置库服务所在的真实IP地址 \--\>

\<property name=\"bak1Username\"\>\</property\>\<!\-- MGR配置库用户名(如配置库使用MGR,必须配置此项) \--\>

\<property name=\"bak1Password\"\>\</property\>\<!\-- MGR配置库密码(如配置库使用MGR,必须配置此项) \--\>

\<property name=\"haMode\"\>1\</property\>\<!\-- 高可用模式：0：主备；1：集群 \--\>

\<property name=\"serverId\"\>1\</property\>\<!\-- 集群节点编号1-N（节点数)，集群内唯一且N\<=集群中节点总数 \--\>

\<property name=\"clusterName\"\>HotDB-Cluster30\</property\>\<!\-- 集群组名称 \--\>

\<property name=\"clusterSize\"\>3\</property\>\<!\-- 集群中节点总数 \--\>

\<property name=\"clusterNetwork\"\>192.168.210.0/24\</property\>\<!\-- 集群所在网段 \--\>

\<property name=\"clusterHost\"\>192.168.210.30\</property\>\<!\-- 本节点所在IP \--\>

\<property name=\"clusterPort\"\>3326\</property\>\<!\-- 集群通信端口 \--\>

\<property name=\"haState\"\>master\</property\>\<!\-- HA角色，主节点：master，备节点：backup (集群模式下，此项无效） \--\>

\<property name=\"haNodeHost\"\>\</property\>\<!\-- HA角色，其他节点IP:PORT （主备模式下使用，PORT表示管理端口，例：192.168.200.2:3325） \--\>

\<property name=\"serverPort\"\>3323\</property\>\<!\-- 服务端口 \--\>

\<property name=\"managerPort\"\>3325\</property\>\<!\-- 管理端口 \--\>

\<property name=\"processors\"\>16\</property\>\<!\-- 处理器数 \--\>

......（此处省略部分参数，具体以实际server.xml为准）

\</hotdb:server\>

#### 计算节点启动脚本 {#计算节点启动脚本 .list-paragraph}

建议打开备份的计算节点bin/目录下的hotdb_server脚本，对照着将变更的参数值同步更新至新版本目录下的脚本文件中。

\#!/bin/sh

PID_DIR=\"\$HOTDB_HOME\"/run

PID_FILE=\"\$PID_DIR\"/hotdb-server.pid

HA_STARTUP=\"\$HOTDB_HOME\"/bin/keepalived

DRIVER_DIR=\"\$HOTDB_HOME\"/utils

HOTDB_LOGS=\"\$HOTDB_HOME\"/logs/hotdb.log

HOTDB_CONSOLE_LOG=\"\$HOTDB_HOME\"/logs/console.log

DRIVER_PACKAGE=\"\$DRIVER_DIR\"/aksusbd-7\*.tar.gz

TMPFILE_DIR=\"\$HOTDB_HOME\"/HotDB-TEMP

JAVA_BIN=\"\$HOTDB_HOME\"/../jdk/bin/java

JAVA_VERSION=\"1.7.0_80\"

\#with CMS Garbage Collection

JAVA_OPTS=\"-server -Xms4G -Xmx4G -XX:MaxDirectMemorySize=24G\"

\#with G1 Garbage Collection

\#JAVA_OPTS=\"-server -Xms16G -Xmx16G -XX:MaxDirectMemorySize=24G\"

......（此处省略部分参数，具体以实际hotdb_server脚本内容为准）

HOTDB_CLASSPATH=\"\$HOTDB_HOME/conf:\$HOTDB_HOME/lib/classes\"

#### 其他配置文件 {#其他配置文件 .list-paragraph}

除server.xml与计算节点启动脚本需要与旧版本中计算节点配置文件设置的值保持同步外，还需关注conf/目录下log4j2.xml的相关参数设置是否有变更。

### 启动计算节点与启动异常处理

#### 启动计算节点服务 {#启动计算节点服务 .list-paragraph}

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server start

#### 启动异常处理 {#启动异常处理-1 .list-paragraph}

**问题与处理方案**

计算节点日志报错，服务无法启动

-   将新版本目录重命名，并将旧目录重新命名为hotdb-server。

-   使用之前备份的配置库文件将配置库恢复。

-   重新启动旧版本目录中的计算节点服务。

-   分析启动失败的问题，解决后再按照上述步骤重新升级。

## 主备节点集群模式停机升级

以下将介绍在停机条件下对主备模式集群中的主备计算节点分别进行版本升级。停机升级过程中计算节点将无法提供任何服务，请注意在无业务进行时执行升级操作。

### 配置库升级

计算节点配置库升级与管理平台配置库升级步骤一致，区别在于执行的升级SQL内容与连接的配置库地址不同。具体操作步骤请参考管理平台[配置库升级说明](#配置库升级)。

### 停止计算节点与keepalived服务

**停止备keepalived服务**

\#service keepalived stop

**停止备计算节点服务**

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

**停止主keepalived服务**

\#service keepalived stop

**停止主计算节点服务**

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

### 升级主备计算节点版本

替换升级包、更新配置文件等操作请参照单节点集群模式中[计算节点版本升级](#单节点集群模式升级)操作说明。

### NDB SQL服务

若升级前的计算节点版本大于等于V2.5.3需要注意，之前备份的计算节点目录中是否包含NDB SQL服务（以ndbsql开头的目录）。若存在则需要将之前备份的计算节点目录中的NDB SQL目录重新拷贝到升级后的计算节点目录中。

具体操作方式请参照[单节点集群计算节点升级NDB SQL服务](#ndb-sql服务-2)处理说明。

### 启动keepalived与计算节点服务

先启动主计算节点服务，**待计算节点服务完全启动成功**（服务端口与管理端口都可正常连接）后再启动主计算节点keepalived服务。

**启动主计算节点服务**

**\#cd** /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server start

**启动主keepalived服务**

\#service keepalived start

**待ping通主keepalived的VIP地址**后，再启动备计算节点服务。 备计算节点服务**完全启动成功**（服务端口关闭，管理端口能正常连接）后再启动备keepalived服务。

**启动备计算节点服务**

**\#cd** /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server start

**启动备keepalived服务**

\#service keepalived start

## 主备节点集群模式不停机升级

以下将介绍在主备节点集群模式中，保证服务不中断的情况下对主备计算节点进行版本升级。升级过程会发生一次计算节点高可用切换，在切换过程中会导致客户端应用短暂失去连接的情况，生产环境建议在业务低峰期进行升级操作。

### 不停机升级前提

主备节点集群模式选择不停机升级要求必须满足以下前提才能进行，否则只能在[停机条件下对计算节点版本进行升级](#主备节点集群模式停机升级)操作。

-   计算节点配置库的升级SQL中不包含任何"alter table"修改已有字段（修改增加字段长度或范围的除外）的语句。

-   计算节点配置库的升级SQL中不包含任何"drop table"的语句。

-   计算节点配置库的升级SQL中不包含任何"update\\delete"已有数据的语句。

### 高可用切换检查

计算节点升级时需要进行一次高可用切换操作，为保证切换顺利完成需人工检查当前高可用环境是否符合切换条件。以下检查项要求所有都满足要求，否则可能导致高可用切换失败。

#### 高可用切换检查项 {#高可用切换检查项 .list-paragraph}

1.  **主备计算节点服务正常**

> **要求：**当前主计算节点服务端口（默认3323）正常开放，管理端口（默认3325）正常开放，当前备
>
> 计算节点服务端口状态关闭，管理端口正常开放。

2.  **主备计算节点配置文件server.xml配置正确**

-   **当前主计算节点server.xml配置**

> \<property name=\"haState\"\>master\</property\>\< HA 角色，主节点：master，备节点：backup\>\<property name=\"haNodeHost\"\>\</property\>\<HA 角色，其他节点 IP:PORT\>

-   **当前备计算节点server.xml配置**

> \<property name=\"haState\"\>backup\</property\>\< HA 角色，主节点：master，备节点：backup\>
>
> \<property name=\"haNodeHost\"\>192.168.200.190:3325\</property\>\<HA 角色，其他节点 IP:PORT\>
>
> **注意：**上述IP地址需填写当前主计算节点所在服务器IP地址，端口号为当前主计算节点管理端口

3.  **主备keepalived配置文件keepalived.conf配置正确**

-   **当前主计算节点keepalived.conf配置**

> ! Configuration File for keepalived
>
> global_defs {
>
> router_id HotDB Server-ha
>
> }
>
> vrrp_script check_HotDB Server_process {
>
> script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p
>
> rocess.sh process\"
>
> interval 5
>
> fall 2
>
> rise 1
>
> weight -10
>
> }
>
> vrrp_script check_HotDB Server_connect_state {
>
> state
>
> code
>
> script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p
>
> rocess.sh connect_master\"
>
> interval 5
>
> fall 3
>
> rise 1
>
> timeout 5
>
> weight -10
>
> }
>
> vrrp_instance VI_1 {
>
> state BACKUP
>
> interface eth1
>
> virtual_router_id 89
>
> nopreempt
>
> priority 100
>
> advert_int 1
>
> authentication {
>
> auth_type PASS
>
> auth_pass 1111
>
> }
>
> track_script {
>
> check_HotDB Server_process
>
> check_HotDB Server_connect_state
>
> }
>
> \#be careful in red hat
>
> track_interface {
>
> eth1
>
> }
>
> virtual_ipaddress {
>
> 192.168.200.140/24 dev eth1 label eth1:1
>
> }
>
> notify_master \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec
>
> k_hotdb_process.sh master_notify_master\"
>
> notify_backup \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec
>
> k_hotdb_process.sh master_notify_backup\"
>
> notify_fault \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_process.sh master_notify_backup\"
>
> }

-   **当前备计算节点keepalived.conf配置**

> ! Configuration File for keepalived
>
> global_defs {
>
> router_id HotDB Server-ha
>
> }
>
> vrrp_script check_HotDB Server_process {
>
> script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p
>
> rocess.sh process\"
>
> interval 5
>
> fall 2
>
> rise 1
>
> weight -10
>
> }
>
> vrrp_script check_HotDB Server_connect_state {
>
> state
>
> code
>
> script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p
>
> rocess.sh connect_backup\"
>
> interval 5
>
> fall 3
>
> rise 1
>
> timeout 5
>
> weight -10
>
> }
>
> vrrp_instance VI_1 {
>
> state BACKUP
>
> interface eth0
>
> virtual_router_id 89
>
> priority 95
>
> advert_int 1
>
> authentication {
>
> auth_type PASS
>
> auth_pass 1111
>
> }
>
> track_script {
>
> check_HotDB Server_process
>
> check_HotDB Server_connect_state
>
> }
>
> \#be careful in red hat
>
> track_interface {
>
> eth0
>
> }
>
> virtual_ipaddress {
>
> 192.168.200.140/24 dev eth0 label eth0:1
>
> }
>
> notify_master \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec
>
> k_hotdb_process.sh backup_notify_master\"
>
> notify_backup \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec
>
> k_hotdb_process.sh backup_notify_backup\"
>
> notify_fault \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_process.sh backup_notify_backup\"
>
> }

4.  **配置校验正常**

-   配置校验正常通过，可在管理平台中"配置-\>配置校验"菜单中检测配置库配置是否正确

-   计算节点内存信息与配置库保持一致，可通过管理平台"动态加载"功能或登录管理端口（默认3325）执行reload @\@config命令确保两者信息一致

5.  **Keepalived程序运行正常**

-   主备keepalived程序运行正常，可在主备计算节点服务器中通过"service keepalived statu

> s"命令查询

6.  **Keepalived的VIP在当前主计算节点上**

-   在当前主计算节点服务器上执行"ip addr"显示内容包含keepalived配置的虚拟IP地址

### 配置库升级

计算节点配置库升级与管理平台配置库升级步骤一致，区别在于执行的升级SQL内容与连接的配置库地址不同。具体操作步骤请参考管理平台[配置库升级说明](#配置库升级)。

### 备计算节点升级

#### 停止备计算节点服务 {#停止备计算节点服务 .list-paragraph}

登录备计算节点服务器执行停止服务命令：

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

#### 备份备计算节点目录 {#备份备计算节点目录 .list-paragraph}

\#cd /usr/local/hotdb/

\#mv hotdb-server hotdb_server_249

#### 上传并解压新版本包 {#上传并解压新版本包-1 .list-paragraph}

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -xvf hotdb-server-2.4.9-ga-20190812.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-server

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-server

#### NDB SQL服务 {#ndb-sql服务-2 .list-paragraph}

若升级前的计算节点版本大于等于V2.5.3需要注意，之前备份的计算节点目录中是否包含NDB SQL服务（以ndbsql开头的目录）。若存在则需要将之前备份的计算节点目录中的NDB SQL目录重新拷贝到升级后的计算节点目录中。

具体操作方式请参照[单节点集群计算节点升级NDB SQL服务](#ndb-sql服务-2)处理说明。

#### server.xml配置文件 {#server.xml配置文件-1 .list-paragraph}

建议打开旧计算节点目录中的server.xml配置文件，对照着将变更的参数值同步更新至新版本目录下的配置文件中。

\# cd /usr/local/hotdb/hotdb-server/conf/

\# vi server.xml

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

**注：**参考修改内容此处不再赘述，可查看单节点集群模式升级中[更新server.xml配置文件](#server.xml配置文件)说明。

#### 计算节点启动脚本 {#计算节点启动脚本-1 .list-paragraph}

建议打开备份的计算节点bin/目录下的hotdb_server脚本，对照着将变更的参数值同步更新至新版本目录下的脚本文件中。

\# cd /usr/local/hotdb/hotdb-server/bin/

\# vi hotdb_server

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

**注：**参考修改内容此处不再赘述，可查看单节点集群模式升级中更新[计算节点启动脚本](#计算节点启动脚本)说明。

#### 其他配置文件 {#其他配置文件-1 .list-paragraph}

除server.xml与计算节点启动脚本需要与旧版本中计算节点配置文件设置的值保持同步外，还需关注conf/目录下log4j2.xml的相关参数设置是否有变更。

#### 启动备计算节点服务 {#启动备计算节点服务 .list-paragraph}

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server start

**注意：**备计算节点服务启动后服务端口（默认3323）是关闭的，管理端口（默认3325）是开启的才算正常。

### 主计算节点升级

#### 停止主计算节点服务 {#停止主计算节点服务 .list-paragraph}

**登录主计算节点服务器执行停止服务命令：**

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server stop

#### 检查高可用是否切换成功 {#检查高可用是否切换成功 .list-paragraph}

主计算节点服务关闭后，程序会发生高可用切换，切换后备计算节点的**服务端口（默认3323）会被启动**，

且在备计算节点服务器上执行"ip addr"命令可查看**keepalived配置的虚拟IP地址已漂移**过来。

**注：**若以上任一要求未满足则代表高可用切换失败，则需要由非停机升级转为[停机升级](#多节点集群模式停机升级)。

#### 备份主计算节点目录 {#备份主计算节点目录 .list-paragraph}

\#cd /usr/local/hotdb/

\#mv hotdb-server hotdb-server_249

#### 上传并解压新版本包 {#上传并解压新版本包-2 .list-paragraph}

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -xvf hotdb-server-2.4.9-ga-20190812.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-server

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-server

#### NDB SQL服务 {#ndb-sql服务-3 .list-paragraph}

若升级前的计算节点版本大于等于V2.5.3需要注意，之前备份的计算节点目录中是否包含NDB SQL服务（以ndbsql开头的目录）。若存在则需要将之前备份的计算节点目录中的NDB SQL目录重新拷贝到升级后的计算节点目录中。

具体操作方式请参照[单节点集群计算节点升级NDB SQL服务](#ndb-sql服务-2)处理说明。

#### server.xml配置文件 {#server.xml配置文件-2 .list-paragraph}

建议打开旧计算节点目录中的server.xml配置文件，对照着将变更的参数值同步更新至新版本目录下的配置文件中。

\# cd /usr/local/hotdb/hotdb-server/conf/

\# vi server.xml

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

**注：**参考修改内容此处不再赘述，可查看单节点集群模式升级中[更新server.xml配置文件](#server.xml配置文件)说明。

#### 计算节点启动脚本 {#计算节点启动脚本-2 .list-paragraph}

建议打开备份的计算节点bin/目录下的hotdb_server脚本，对照着将变更的参数值同步更新至新版本目录下的脚本文件中。

\# cd /usr/local/hotdb/hotdb-server/bin/

\# vi hotdb_server

\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--配置内容\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\-\--

**注：**参考修改内容此处不再赘述，可查看单节点集群模式升级中更新[计算节点启动脚本](#计算节点启动脚本)说明。

#### 其他配置文件 {#其他配置文件-2 .list-paragraph}

除server.xml与计算节点启动脚本需要与旧版本中计算节点配置文件设置的值保持同步外，还需关注conf/目录下log4j2.xml的相关参数设置是否有变更。

#### 手动执行高可用环境重建 {#手动执行高可用环境重建 .list-paragraph}

为保证当前备计算节点启动正常，需进行手动执行高可用环境重建操作。若使用的管理平台为V2.4.8及以上版本时可使用"高可用重建"功能替代以下操作。

**说明：**以下操作说明使用的"当前备"为计算节点服务器没有VIP（keepalived虚拟IP）的计算节点，

"当前主"为VIP所在的计算节点。可在主备计算节点服务器上执行"ip addr"命令查看当前VIP漂移位置，以确定当前计算节点的主备状态。

1.  **停止当前备（无VIP）keepalived服务**

\#service keepalived stop

2.  **当前主（有VIP）计算节点server.xml与keepalived.conf修改**

**当前主（有VIP）计算节点server.xml配置修改**

\<property name=\"haState\"\>master\</property\>\< HA 角色，主节点：master，备节点：backup\>\<property name=\"haNodeHost\"\>\</property\>\<HA 角色，其他节点 IP:PORT\>

**当前备（无VIP）计算节点keepalived.conf配置修改**

! Configuration File for keepalived

global_defs {

router_id HotDB Server-ha

}

vrrp_script check_HotDB Server_process {

script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p

rocess.sh process\"

interval 5

fall 2

rise 1

weight -10

}

vrrp_script check_HotDB Server_connect_state {

state

code

script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p

rocess.sh connect_master\"

interval 5

fall 3

rise 1

timeout 5

weight -10

}

vrrp_instance VI_1 {

state BACKUP

interface eth1

virtual_router_id 89

nopreempt

priority 100

advert_int 1

authentication {

auth_type PASS

auth_pass 1111

}

track_script {

check_HotDB Server_process

check_HotDB Server_connect_state

}

\#be careful in red hat

track_interface {

eth1

}

virtual_ipaddress {

192.168.200.140/24 dev eth1 label eth1:1

}

notify_master \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec

k_hotdb_process.sh master_notify_master\"

notify_backup \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec

k_hotdb_process.sh master_notify_backup\"

notify_fault \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_process.sh master_notify_backup\"

}

3.  **当前备（无VIP）计算节点server.xml与keepalived.conf修改**

**当前备计算节点server.xml配置**

\<property name=\"haState\"\>backup\</property\>\< HA 角色，主节点：master，备节点：backup\>

\<property name=\"haNodeHost\"\>192.168.200.190:3325\</property\>\<HA 角色，其他节点 IP:PORT\>

**注意：**上述IP地址需填写当前主计算节点所在服务器IP地址，端口号为当前主计算节点管理端口

**当前备（无VIP）计算节点keepalived.conf配置**

! Configuration File for keepalived

global_defs {

router_id HotDB Server-ha

}

vrrp_script check_HotDB Server_process {

script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p

rocess.sh process\"

interval 5

fall 2

rise 1

weight -10

}

vrrp_script check_HotDB Server_connect_state {

state

code

script \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_p

rocess.sh connect_backup\"

interval 5

fall 3

rise 1

timeout 5

weight -10

}

vrrp_instance VI_1 {

state BACKUP

interface eth0

virtual_router_id 89

priority 95

advert_int 1

authentication {

auth_type PASS

auth_pass 1111

}

track_script {

check_HotDB Server_process

check_HotDB Server_connect_state

}

\#be careful in red hat

track_interface {

eth0

}

virtual_ipaddress {

192.168.200.140/24 dev eth0 label eth0:1

}

notify_master \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec

k_hotdb_process.sh backup_notify_master\"

notify_backup \"/bin/bash /usr/local/hotdb/hotdb-server/bin/chec

k_hotdb_process.sh backup_notify_backup\"

notify_fault \"/bin/bash /usr/local/hotdb/hotdb-server/bin/check_hotdb_process.sh backup_notify_backup\"

}

4.  **当前主（有VIP）keepalived服务执行配置重新加载**

\#service keepalived reload

#### 启动当前备（无VIP）计算节点服务与keepalived {#启动当前备无vip计算节点服务与keepalived .list-paragraph}

**启动计算节点服务**

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server start

**计算节点服务启动成功后再启动keepalived服务**

\#service keepalived start

#### 特殊说明 {#特殊说明 .list-paragraph}

-   按不停机升级流程完成后，keepalived的虚拟IP会发生漂移。集群中的主备计算节点角色会进行互换。

-   若需要将主备计算节点角色还原成升级前的状态，可通过管理平台提供的"高可用切换"功能，或使用手动切换的方式（直接停止当前主服务模拟故障切换）对计算节点角色进行再次互换。

-   注意切换完成后必须进行一次高可用重建，可使用管理平台提供的"高可用重建"功能或参照"[手动执行高可用环境重建](#server.xml配置文件-2)"进行操作。

## 多节点集群模式停机升级

以下将介绍多节点集群模式中三个计算节点的版本升级操作，若为三个以上计算节点的多节点集群升级也可以参照以下操作说明进行。

#### 停止计算节点服务 {#停止计算节点服务-1 .list-paragraph}

**停止secondary1计算节点服务**

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

**停止secondary2计算节点服务**

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

**停止primary计算节点服务**

\#sh /usr/local/hotdb/hotdb-server/bin/hotdb_server stop

#### 升级配置库 {#升级配置库 .list-paragraph}

计算节点配置库升级与管理平台配置库升级步骤一致，区别在于执行的升级SQL内容与连接的配置库地址不同。具体操作步骤请参考管理平台[配置库升级说明](#配置库升级)。

#### 替换升级包与更新配置文件 {#替换升级包与更新配置文件 .list-paragraph}

参照单节点集群模式中[计算节点版本升级](#单节点集群模式升级)操作说明为集群中的计算节点依次替换版本包以及更新对应的配置文件内容。

#### 启动计算节点服务 {#启动计算节点服务-1 .list-paragraph}

计算节点服务程序启动不分先后顺序，可依次启动完成后登录任一计算节点管理端口（默认3325）执行"show @\@cluster"命令查询当前集群中计算节点运行状态与角色信息。

## 多节点集群模式不停机升级

以下将介绍多节点集群模式中三个计算节点的版本升级操作，若为三个以上计算节点的多节点集群升级也可以参照以下操作说明进行。

### 不停机升级前提

多节点集群模式选择不停机升级要求必须满足以下前提才能进行，否则只能在[停机条件下对计算节点版本进行升级](#灾备模式的集群升级)操作。

-   计算节点配置库的升级SQL中不包含任何"alter table"修改已有字段（修改增加字段长度或范围的除外）的语句。

-   计算节点配置库的升级SQL中不包含任何"drop table"的语句。

-   计算节点配置库的升级SQL中不包含任何"update\\delete"已有数据的语句。

-   升级前的计算节点版本不低于V2.5.1。

-   升级前的计算节点版本若为V2.5.1或V2.5.3则要求版本中的日期必须大于等于20190821。

**升级须知：**

升级集群中的secondary计算节点会导致连接在该secondary计算节点上的客户端连接中断，升级primary计算节点除对客户端连接会造成中断外还包括部分进行中的事务中断的问题，生产环境建议在业务低峰期进行升级操作。

**特殊说明：**primary与secondary是计算节点在管理端口（默认3325）中执行"show @\@cluster"命令显示的当前计算节点的角色。以下secondary1与secondary2代指三个计算节点集群中secondary属性的计算节点，末尾的数字标识不分先后顺序，可代指任一secondary计算节点。

### 配置库升级

计算节点配置库升级与管理平台配置库升级步骤一致，区别在于执行的升级SQL内容与连接的配置库地址不同。具体操作步骤请参考管理平台[配置库升级说明](#配置库升级)。

### Secondary1计算节点升级

替换升级包、更新配置文件、启动计算节点服务等操作请参照单节点集群模式中[计算节点版本升级](#单节点集群模式升级)操作说明。

### Secondary2计算节点升级

替换升级包、更新配置文件、启动计算节点服务等操作请参照单节点集群模式中[计算节点版本升级](#单节点集群模式升级)操作说明。

### Primary计算节点升级

#### 停止primary计算节点服务 {#停止primary计算节点服务 .list-paragraph}

\#cd /usr/local/hotdb/hotdb-server/bin

\#sh hotdb_server stop

**注：**停止primary计算节点服务会导致集群发生一次换主动作，请保证停止primary计算节点前其余secondary计算节点服务运行正常。

替换升级包、更新配置文件、启动计算节点服务等操作请参照单节点集群模式中[计算节点版本升级](#单节点集群模式升级)操作说明。

## 灾备模式的集群升级

对一个开启灾备模式的计算节点集群做手动升级，需要以机房为单位进行升级。

若机房发生切换，建议优先修复机房故障后再进行升级操作，即灾备模式的集群建议在两个机房内无组件异常且机房间复制状态正常时进行手动升级。

灾备模式的集群升级必须先升级中心机房，确保升级无误后，再升级灾备机房。

### 中心机房升级

灾备模式的中心机房升级与单机房模式的集群升级大致相同。

若中心机房的计算节点为单节点模式，请参考[单节点集群模式停机升级](#单节点集群模式升级)。

若中心机房的计算节点为主备节点模式，请参考[主备节点集群模式停机升级](#主备节点集群模式停机升级)或[不停机升级](#主备节点集群模式不停机升级)。

若中心机房的计算节点为多节点模式，请参考[多节点集群模式停机升级](#多节点集群模式停机升级)或[不停机升级](#多节点集群模式不停机升级)。

### 灾备机房升级

因灾备机房不提供真实的数据库服务，故在日常运维过程中，灾备机房的计算节点仅开启管理端口或处于停机状态。

若灾备机房的计算节点为单节点模式，请参考[单节点集群模式停机升级](#单节点集群模式升级)。

若灾备机房的计算节点为主备节点模式，请参考[主备节点集群模式停机升级](#主备节点集群模式停机升级)或[不停机升级](#主备节点集群模式不停机升级)。

若灾备机房的计算节点为多节点模式，请参考[多节点集群模式停机升级](#多节点集群模式停机升级)或[不停机升级](#多节点集群模式不停机升级)。

注意：灾备机房升级过程种，无需对配置库进行升级。在中心机房配置库升级时会通过中心机房配置库与灾备机房配置库之间的MySQL复制关系自动同步。

# 备份程序手动升级

备份程序（HotDB Backup）一般不需要频繁升级更新版本，只在备份程序有较大功能优化或bug修复时更新即可。更新备份程序要求对所有存储节点服务器上部署的备份程序执行相同的升级操作。

## 停止备份程序服务

停止备份程序服务前，请确认当前管理平台中没有未完成的备份任务，否则可能导致备份任务的异常中断。

\#cd /usr/local/hotdb/hotdb-backup/bin

\#sh hotdb_backup stop

## 备份旧备份程序目录

\#cd /usr/local/hotdb/

\#mv hotdb-backup/ hotdb-backup_1.0

## 上传并解压新版本包

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -xvf hotdb-backup-2.0-20190109.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-backup

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-backup

## 启动服务

\#cd /usr/local/hotdb/hotdb-backup/bin

\#sh hotdb_backup start -h 192.168.220.104 -p 3322

**注：**IP地址为备份程序所关联的管理平台服务器地址，端口号为管理平台配置文件application.properties中server.backup.port参数值

**特殊说明：**其余存储节点服务器上的备份程序升级只需按照上述流程操作即可。

# 监听程序手动升级

监听程序（HotDB Listener）一般不需要频繁升级更新版本，只在监听程序有较大功能优化或bug修复时更新即可。更新监听程序要求对所有存储节点服务器上部署的监听程序执行相同的升级操作。

## 停止监听程序服务

停止监听程序服务前，请确认当前计算节点是否有正在进行SQL操作，否则可能存在事务损失。

\#cd /usr/local/hotdb/hotdb-listener/bin

\#sh hotdb_listener stop

## 备份旧备份程序目录

\#cd /usr/local/hotdb/

\#mv hotdb-listener/ hotdb-listener_1.0

## 上传并解压新版本包

**上传新版本包**

可使用rz命令或ftp文件传输工具上传新版本包

**解压新版本包**

\#tar -zvxf hotdb-listener-0.0.1-linux.tar.gz -C /usr/local/hotdb

**为hotdb用户赋予文件夹权限**

\#chown -R hotdb:hotdb /usr/local/hotdb/hotdb-listener

**恢复目录默认上下文**

\#restorecon -R /usr/local/hotdb/hotdb-listener

## 修改配置文件

**修改管理端口配置文件同服务停止前一致，默认3330：**

\#cd /usr/local/hotdb/hotdb-listener/conf

\#vi config.properties

host=0.0.0.0

port=3330

## 启动服务

\#cd /usr/local/hotdb/hotdb-listener/bin

\#sh hotdb_listener start

**特殊说明：**其余存储节点服务器上的监听程序升级只需按照上述流程操作即可。