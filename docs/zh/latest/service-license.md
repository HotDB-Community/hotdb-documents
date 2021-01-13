# 服务授权

## 管理平台授权许可证

分布式事务数据库平台（以下简称管理平台）是一种搭配分布式事务数据库（简称HotDB Server或计算节点）使用的具有配置、监控、运维、管理等强大功能的服务平台，从V2.5.6.1版本开始，引入健全的授权管理体系，即管理平台授权许可证，对可用的计算节点集群组数以及每组集群最多可用的计算节点个数进行授权许可，以确保管理平台更好的对外提供服务。

管理平台许可证分为试用许可证和正式许可证，管理平台试用许可证有且仅能管理或部署1组集群，集群组内最多包含3个计算节点，若想管理或部署多组集群时，需要申请正式许可证。

当管理平台所在服务器上同时存在试用许可证和正式许可证时，优先使用授权集群组数更多的许可证；若授权集群组数相同，优先使用授权计算节点个数更多的许可证。

### 试用许可证

若使用一键部署安装管理平台，在`hotdb-management/keys`目录下默认附带一个可用1组计算节点集群，每组集群可用3个计算节点的试用许可证，文件名为`management-license`，如下所示：

```
[root@hotdb_171_221 keys]# cd /usr/local/hotdb/hotdb-management/keys
[root@hotdb_171_221 keys]# ll

total 4
-rw-r--r--. 1 root root 1313 Dec 25 17:57 management-license**
```

#### 激活试用许可证

试用许可证无需额外激活步骤，只需将许可证文件存放在`hotdb-management/keys`目录下，启动管理平台即可自动激活。

#### 查看试用许可证

若需要查看试用许可证详细信息，可在`hotdb-management/keys`目录下执行以下命令：

```
[root@hotdb_171_221 keys]# java -jar ../utils/hotdb_management_license.jar -l management-license

License info of file management-license:
LICENSE VERIFIED.

======================== Management License ========================
file: management-license
serial number: TRAIL-1-3-0-7632041185726317126
type: TRAIL
number of available compute node cluster groups: 1
number of compute nodes available in each cluster: 3
module limit: 0
create time: 2020-11-11
customer info: hotdb.com
```

以上各列其含义分别为：

* `file` - 授权文件名
* `serial number` - 授权文件序列号
* type - 授权类型，示例：OFFICIAL（正式版）、TRAIL（试用版）
* `num of available compute node cluster groups` - 可用计算节点集群组数，为0表示不做任何限制
* `num of compute nodes available in each cluster` - 每组集群可用的计算节点个数，为0表示不做任何限制
* `module limit` - 模块限制，默认为0
* `create time` - 创建时间
* `customer info` - 用户信息备注

若提示：`ERROR: LICENSE TAMPERED!`，一般表示授权被篡改、无法使用。

或者在管理平台启动后，使用浏览器登录管理平台，在页面上将鼠标放置在版本号旁"已认证"字样上，即可查看到许可证详细信息，如下如所示：

![](assets/service-license/image3.png)

### 正式许可证

申请正式许可证需要获取管理平台所在服务器机器指纹，唯一机器指纹对应唯一正式许可证，因此正式许可证有且仅能在对应服务器上激活和使用。

#### 生成机器指纹

进入`hotdb-management/utils`目录下，执行以下命令生成机器指纹：

```
[root@hotdb_171_221 utils]# cd /usr/local/hotdb/hotdb-management/utils
[root@hotdb_171_221 utils]# ll

total 144
-rw-r--r--. 1 hotdb hotdb 145748 Nov 25 10:43 hotdb_management_license.jar

[root@hotdb_171_221 utils]# **java -jar hotdb_management_license.jar -f**
trying to generate fingerprint on Linux amd64
The fingerprint file was successfully generated in ./management-fingerprint-2020-12-28-10-17-08

[root@hotdb_171_221 utils]# ll

total 148
-rw-r--r--. 1 hotdb hotdb 145748 Nov 25 10:43 hotdb_management_license.jar
-rw-r--r--. 1 root root 1025 Dec 28 10:17 management-fingerprint-2020-12-28-10-17-08
```

#### 申请正式许可证

将生成的机器指纹`management-fingerprint-2020-12-28-10-17-08`文件作为附件，发送授权申请邮件给热璞科技（[service@hotdb.com](service@hotdb.com)），由热璞科技相关对接人协助授权并回复正式许可证文件。

#### 更新正式许可证

收到管理平台正式许可证文件后，将正式许可证文件上传至`hotdb-management/keys`目录下即可。

以正式许可证文件`management-license-official-2020-12-23-15-40-55`为例。

#### 激活正式许可证

启动管理平台即可自动激活。若管理平台已处于运行状态，默认1分钟检测一次许可证状态，当检测到新许可证时会自动激活。

#### 查看正式许可证

若需要查看正式许可证详细信息，可在`hotdb-management/keys`目录下执行以下命令：

```
[root@hotdb_171_221 keys]# java -jar ../utils/hotdb_management_license.jar -l management-license-official-2020-12-23-15-40-55

License info of file management-license-official-2020-12-23-15-40-55:
LICENSE VERIFIED.

======================== Management License ========================
file: management-license-official-2020-12-23-15-40-55
serial number: OFFICIAL-4-9-0-3441650625331880732
type: OFFICIAL
number of available compute node cluster groups: 4
number of compute nodes available in each cluster: 9
module limit: 0
create time: 2020-12-23
customer info: poc_license_for_bank_of_256test
```

或者在管理平台启动后，使用浏览器登录管理平台，在页面上将鼠标放置在版本号旁"已认证"字样上，即可查看到许可证详细信息，如下如所示：

![](assets/service-license/image4.png)

### 查看正在使用的许可证

若`hotdb-management/keys`目录下存在多个许可证时，可执行以下命令查看当前正在使用的许可证：

```
[root@hotdb_171_221 keys]# java -jar ../utils/hotdb_management_license.jar -i

The License currently in use is:

======================== Management License ========================
file: management-license-official-2020-12-23-15-40-55
serial number: OFFICIAL-4-9-0-3441650625331880732
type: OFFICIAL
number of available compute node cluster groups: 4
number of compute nodes available in each cluster: 9
module limit: 0
create time: 2020-12-23
customer info: poc_license_for_bank_of_256test
```

* 如果是试用许可证，查看许可证信息时可见`type: TRAIL`
* 如果是正式许可证，查看许可证信息时可见`type: OFFICIAL`

### 注意事项

首次使用管理平台许可证时，需要注意以下事项。

#### 机器指纹

机器指纹由服务器多种硬件设备的信息组成，例如CPUID、硬盘序列号、内存序列号、网卡MAC地址、根分区的UUID和系统\\虚拟机UUID等，在更新并使用正式许可证后，应避免改动服务器硬件信息，此操作会导致许可证失效。

#### 正式许可证文件

拿到正式许可证文件时，应妥善保管，避免人为改动文件内容，此操作会导致许可证失效。

#### 超出许可证限制

-   若管理平台当前管理的计算节点集群组数或者计算节点个数**达到**许可证授权集群组数或计算节点个数上限，**不允许添加新集群**，提示"超过平台授权的可用计算节点个数，禁止添加"；
-   若管理平台当前管理的计算节点集群组数或者计算节点个数**达到**许可证授权集群组数或计算节点个数上限，**不允许部署新集群**，提示"超过平台授权的可用计算节点集群组数，禁止通过参数配置功能添加集群"；
-   若管理平台当前管理的计算节点集群组数或者计算节点个数**超过**许可证授权集群组数或计算节点个数上限，等待1分钟刷新页面或者重启管理平台后，**不允许进入正常操作页面**，需要更新许可证以满足当前已经管理的计算节点集群组数或者计算节点个数；

## 计算节点授权许可证

HotDB计算节点能正常提供服务，需要使用热璞科技正规的授权许可证进行激活。从HotDB Server V2.5.6版本起，计算节点启用新自研的加密许可证授权管理功能，本文档基于新自研的加密许可证授权功能进行说明。若您使用的计算节点版本低于（不包含）V2.5.6时，仍旧参考历史与其配套的服务授权使用说明文档进行操作。

新自研加密许可证只有软锁，没有硬锁。软锁又分为"正式软锁许可证（简称为正式许可证）"与[试用许可证](#试用许可证)，正式许可证可以控制有效期、节点数、逻辑库数，且唯一机器指纹对应唯一正式软锁；试用许可证无需机器指纹即可在任意机器上激活，可控制节点数、逻辑库数，且有效期永久。目前仅允许使用一次试用许可证，若要更新需申请正式许可证。

当服务器上同时存在试用许可证和正式许可证时，优先使用授权节点数更多的许可证；若授权节点数相同，优先使用授权逻辑库数更多的许可证；若授权逻辑库数相同，优先使用授权有效期更长的许可证。

### 试用许可证

若使用一键部署安装计算节点，在hotdb-server/keys目录下默认附带一个具有16个节点，2个逻辑库的试用许可证，文件名为license-trail，如下所示：

```
[root@localhost keys]# cd /usr/local/hotdb/hotdb-server/keys
[root@localhost keys]# ll

total 4
-rw-r--r-- 1 root root 1369 Aug 6 16:21 **license-trail**
```

#### 激活试用许可证

新自研加密许可证无需额外激活步骤，只需将许可证文件存放在`hotdb-server/keys`目录下，启动计算节点即可自动激活。

#### 查看试用许可证

若需要查看试用许可证详细信息，可在`hotdb-server/keys`目录下执行以下命令：

```
[root@localhost keys]# **java -jar ../utils/hotdb_license.jar -l license-trail**

License info of file license-trail:
LICENSE VERIFIED.

======================== HotDB License ========================
file: license-trail
serial number: TRAIL-64-0-1609344000000-8283391465276724534
type: TRAIL
datanode limit: 16
module limit: 0
logicdb limit: 2
create time: 2020-04-23
effective time: 2020-09-15
expire time: 2020-12-14
time left: 89 days 23 hours 58 minutes 51 seconds.
customer info: hotdb.com
```

以上各列其含义分别为：

* file - 授权文件名
* serial number - 授权文件序列号
* type - 授权类型，分为 EXPIRATION（有效）、PERPETUAL（永久）、TRAIL（试用）
* datanode limit - 数据节点数限制
* module limit - 模块限制（当前暂未做功能，可暂时忽略）
* logicdb limit - 可用逻辑库数限制，为0表示不限制
* create time - 创建时间
* effective time - 可用开始时间
* expire time - 可用截止时间，注意，此处可能是非日期形式，EXPIRED 代表授权过期、NOT ACTIVATED YET尚未激活、NEVER代表永久
* time left - 剩余可用时间
* customer info - 用户信息备注

若提示：ERROR: LICENSE TAMPERED! 一般表示授权被篡改、无法使用。

或者在计算节点启动后，`hotdb.log`日志文件中会输出许可证信息，如下所示：

```
2020-12-25 16:38:03.860 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.W(310) - Thanks for choosing HotDB. Datanode limit to: 16, logic database limit to: 2
```

或者登录3325端口执行`show @@usbkey\G`也可查看许可证信息，如下所示：

```
root@127.0.0.1:(none) 8.0.15 01:46:08> show @@usbkey\G

*************************** 1. row ***************************
left_time: 0
usbkey_status: 1
usbkey_type: 1
node_limit: 16
logicdb_limit: 2
last_check_time: 2020-12-28 13:46:14.222
usbkey_check_stuck: 0
last_exception_time: NULL
last_exception_info: NULL
exception_count: 0
comment: NULL
1 row in set (1.26 sec)
```

### 正式许可证

当想授权节点数大于16，授权逻辑库数大于2，需要申请正式许可证。正式许可证按照授权有效期不同分为有期限正式许可证和永久正式许可证，更新和激活步骤相同。

更新正式许可证需要获取服务器机器指纹，唯一机器指纹对应唯一正式软锁，因此正式许可证有且仅能在对应服务器上激活和使用。

#### 生成机器指纹

进入`hotdb-server/utils`目录下，执行以下命令生成机器指纹：

```
[root@localhost utils]# java -jar hotdb_license.jar -f

trying to generate fingerprint on Linux amd64
The fingerprint file was successfully generated in ./fingerprint-2020-09-15-14-41-53

[root@localhost utils]# ll

total 136
-rw-r--r-- 1 root root 2049 Sep 15 14:41 fingerprint-2020-09-15-14-41-53
-rw-r--r-- 1 root root 98165 Aug 25 14:43 hotdb_license.jar
-rw-r--r-- 1 root root 34976 Aug 25 14:43 SM4-1.0.jar
```

#### 申请正式许可证

将生成的机器指纹`fingerprint-2020-09-15-14-41-53`文件作为附件，发送授权申请邮件给热璞，由热璞相关对接人协助授权并获取正式许可证文件。

#### 更新正式许可证

下文以正式许可证文件`license-expiration-2020-09-15-14-53-39`为例。上传正式许可证文件至`hotdb-server/keys`目录下即可。

#### 激活正式许可证

启动计算节点即可自动激活。若计算节点已处于running状态，默认5分钟检测一次许可证状态，当检测到新许可证时会自动激活，或者也可以在3325端口执行`show @@usbkey\G`命令手动触发一次许可证检测以激活新许可证。

#### 查看正式许可证

若需要查看正式许可证详细信息，可在`hotdb-server/keys`目录下执行以下命令：

```
[root@localhost keys]# java -jar ../utils/hotdb_license.jar -l license-expiration-2020-09-15-14-53-39

License info of file license-expiration-2020-09-15-14-53-39:
LICENSE VERIFIED.

======================== HotDB License ========================
file: license-expiration-2020-09-15-14-53-39
serial number: EXPIRATION-64-0-1600704000000-4148973046739213858
type: EXPIRATION
datanode limit: 64
module limit: 0
logicdb limit: 20
create time: 2020-09-15
effective time: 2020-09-15
expire time: 2020-09-22
time left: 6 days 9 hours 5 minutes 8 seconds.
customer info: license_for_test
```

或者在计算节点启动后，hotdb.log日志文件中会输出许可证信息，如下所示：

```
2020-09-15 15:00:03.706 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(262) - Thanks for chooising HotDB.
2020-09-15 15:00:03.710 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(269) - HotDB license expire time: 2020-09-22, datanode limit to: 64, logic database limit to: 20
2020-09-15 15:00:03.710 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(274) - HotDB trial expires in 6 days 8 hours 59 minutes 56 seconds.
```

或者登录3325端口执行`show @@usbkey\G`也可查看许可证信息，如下所示：

```
root@127.0.0.1:(none) 5.7.22 03:00:56> show @@usbkey\G

*************************** 1. row ***************************
left_time: 550796
usbkey_status: 1
usbkey_type: 2
node_limit: 64
logicdb_limit: 20
last_check_time: 2020-09-15 15:00:03.710
usbkey_check_stuck: 0
last_exception_time: NULL
last_exception_info: NULL
exception_count: 0
comment: NULL
1 row in set (0.02 sec)
```
### 查看正在使用的许可证

若`hotdb-server/keys`目录下存在多个许可证时，可执行以下命令查看当前正在使用的许可证：

```
[root@localhost keys]# java -jar ../utils/hotdb_license.jar -i

The License currently in use is:

======================== HotDB License ========================
file: license-trail
serial number: TRAIL-64-0-1609344000000-8283391465276724534
type: TRAIL
datanode limit: 64
module limit: 0
logicdb limit: 2
create time: 2020-04-23
effective time: 2020-09-15
expire time: 2020-12-14
time left: 89 days 22 hours 55 minutes 48 seconds.
```
-   如果是试用许可证，查看许可证信息时可见`type: TRAIL`
-   如果是有期限的正式许可证，查看许可证信息时可见`type: EXPIRATION`
-   如果是永久的正式许可证，查看许可证信息时可见`type: PERPETUAL`

### 注意事项

首次使用HotDB Server V2.5.6版本计算节点时，由于新自研加密许可证与老版本存在巨大差异，使用时需要注意以下事项。

#### 机器指纹

机器指纹由服务器多种硬件设备的信息组成，例如CPUID、硬盘序列号、内存序列号、网卡MAC地址、根分区的UUID和系统/虚拟机UUID等，在更新并使用正式许可证后，应避免改动服务器硬件信息，此操作会导致许可证失效。

#### 正式许可证文件

拿到正式许可证文件时，应妥善保管，避免人为改动文件内容，此操作会导致许可证失效。

#### 许可证过期

计算节点启动后，默认5分钟检测一次许可证状态。当许可证过期时，计算节点服务不会立刻停止，但会限制节点数为0，3325端口不允许执行reload @@config，3323端口不允许执行任意SQL，同时日志文件hotdb.log中也会间隔5分钟输出一次许可证过期警告信息，如下所示：

```
2020-12-15 00:02:52.113 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(163) - no matched license detected. Datanode limit to 0.
2020-12-15 00:02:52.114 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.a(5540) - Number of Datanodes Exceeded Maximum Size Limit. expect:4 limit:0
```

此时在3325端口执行`reload @@config`会报失败：

```
root@127.0.0.1:(none) 5.7.22 12:06:55> reload @@config;

ERROR 10190 (HY000): Reload config failure, Number of Datanodes Exceeded Maximum Size Limit. For more details, please check the log
```

当许可证过期后，人为手动重启计算节点服务，此时服务将无法启动，报无可用许可证，如下所示：

```
2020-12-15 00:10:47.388 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(158) - no available matched license detected. we will shutdown now.
2020-12-15 00:10:47.389 [INFO] [EXIT[ FLOW ]] [ShutdownHook] cn.hotpu.hotdb.c(770) - begin to exit...
```

因此，当许可证有效期不足时，应尽快申请并更新新许可证。

#### 许可证授权功能超过限制

当计算节点重启时，若许可证授权节点数、逻辑库数小于当前配置的数据节点数、逻辑库数。则计算节点会启动成功，但无法正常使用，会提示Wrong HotDB Config，直到更新了可用授权或修改配置且被计算节点识别为止（间隔5分钟定时任务识别或修改当前配置的节点数/逻辑库数动态加载）。

授权逻辑库数超过已有配置限制提醒示例：

```
root> show databases;

ERROR 10161 (HY000): Wrong HotDB Config, due to: Number of Logic Database Exceeded Maximum Size Limit. expect:3 limit:2
```

日志提醒示例：

```
2020-12-22 14:13:06.231 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.a(5844) - Number of Logic Database Exceeded Maximum Size Limit. expect:3 limit:2
```

当计算节点使用过程中，许可证授权节点数、逻辑库数小于当前配置的数据节点数、逻辑库数，则不允许动态加载，提示示例如下：

```
root> reload @@config;

ERROR 10190 (HY000): Reload config failure, Number of Logic Database Exceeded Maximum Size Limit. For more details, please check the log
```

#### 服务器时间

新自研加密许可证高度依赖服务器当前时间，因此需保持服务器时间与网络时间同步。若遇到许可证突然提示不可用，首先应检查服务器时间是否被篡改。