# 集群环境要求

## 分布式事务数据库集群配置参考

> !!!INFO
>
> 集群环境要求包含服务器硬件配置、操作系统、软件部署、软件配置四个方面。在部署安装前或安装完成后请检查以下各项是否符合分布式事务数据库集群使用要求，若不满足以下要求可能会给集群的运行带来不可预知的异常以及无法发挥集群的最佳性能。

| 一级标题 | 二级标题 | 三级标题 | 使用要求 |
|---------|-------------|----------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 服务器硬件配置 | CPU | CPU | 1个计算节点≥40个逻辑核<br>1个管理平台≥4个逻辑核<br>1个MySQL实例≥8个逻辑核（包括存储节点实例与配置库实例）<br>1个LVS实例≥16个逻辑核（仅限多节点负载均衡模式）<br>服务器要求：可用逻辑核总数大于服务器已安装程序所需逻辑核数 |
|   | 内存 | 内存 | 1个计算节点≥30G<br>1个管理平台≥2G<br>1个配置库MySQL实例≥3G<br>一个存储节点MySQL实例≥60G<br>1个LVS实例≥6G（仅限多节点负载均衡模式）<br>操作系统≥1G<br>所有的服务器要求：内存数大于已安装程序所需内存数 |
|   | 磁盘空间 | 磁盘空间 | 1个计算节点≥100G<br>1个管理平台≥10G<br>1个配置库MySQL实例≥100G<br>一个存储节点MySQL实例≥1000G<br>操作系统≥60G<br>服务器（除多节点负载均衡模式的LVS服务器）磁盘空间要求：总磁盘空间大于已安装程序所需磁盘空间 |
|   | 磁盘IO响应 | 服务器磁盘IO响应时间 | 服务器：执行磁盘IO响应检测指令`sar -d |egrep -i '^Average: '|awk 'BEGIN{p=0}{if ($(NF-2)>50) p=1;if ($(NF-1)>5) p=1;if ($NF>50) p=1}END{if (p==1) print "disk latency performance is low, or disk utilization is high"}'`，<br>未报：`disk latency performance is low, or disk utilization is high`或无法执行指令 |
|   | 网络质量 | 服务器网络质量 | 服务器：执行网络质量检测指令`ping -q -c 100 -s 65000 -i 0.01 被测服务器的IP地址 |awk '{if (index($3,"transmitted")>0) {for (i=1;i<=NF;i++) if(index($i,"%")) lost=$i};if (index($1,"rtt")>0) {split($(NF-1),a,"/");avg=a[2];max=a[3]}}END{if (lost!="0%" || avg>3 || max>5) print "network losted packet or latency is not good enough"}'`<br>未报：`network losted packet or latency is not good enough`或无法执行指令 |
|   | 服务器属性 | 服务器非虚拟机 | 服务器：执行命令：`dmesg | grep "Hypervisor detected"`<br>命令执行无报错且无输出 |
| 操作系统 | SSH连接 | 服务器SSH连接速度 | 服务器：建立SSH连接且执行命令：`echo HotDB`，总耗时小于1秒 |
|   | 可连接外网 | 服务器可连接外网 | 建议服务器能正常连接外网：ping通`114.114.114.114` |
|   |   | 服务器DNS配置 | 服务器配置正确的DNS：ping通`www.baidu.com` |
|   | yum | yum | 服务器中配置最新的可用yum源 |
|   | 字符集设置 | 字符集设置 | 强烈建议设置成UTF8（执行命令`echo $LANG`，返回结果为`en_US.UTF-8`，且执行命令`echo $LC_ALL`，返回结果为`en_US.UTF-8`或者为空） |
|   | 时区 | 时区 | 要求设置正确时区：执行`ls -l /etc/localtime|grep -i shanghai`命令有结果输出 |
|   | 时间同步 | 管理平台服务器与外网时间差异 | 执行命令：`ntpdate -q ntp.aliyun.com 2>/dev/null|tail -1|awk '{print $(NF-1)}'<`br>管理平台服务器与外网时间差异小于3秒 |
|   |   | 集群服务器与管理平台服务器时间差异 | 其他服务器与管理平台服务器时间差异小于2秒 |
|   | 防火墙 | 防火墙 | 设置关闭 |
|   | selinux | selinux | 设置关闭 |
|   | limits.conf | 服务器文件句柄数 | 执行`su 用户名 -c 'ulimit -n'`，返回结果大于65534 |
|   |   | 服务器线程数 | 执行`su mysql -c 'ulimit -u'`，返回结果大于1023（特指安装MySQL实例的服务器） |
|   | sysctl.conf | net.core.netdev_max_backlog | 参数设置大于4095 |
|   |   | net.ipv4.ip_local_port_range | 执行`sysctl net.ipv4.ip_local_port_range`，返回第二个数字减第一个数字大于40000，且两个数字加起来是奇数 |
|   |   | net.core.somaxconn | net.core.somaxconn>1000且net.ipv4.tcp_max_syn_backlog>1000 |
|   |   | net.ipv4.tcp_max_syn_backlog |   |
|   |   | net.ipv4.tcp_sack | 四个参数值都为0 |
|   |   | net.ipv4.tcp_fack |   |
|   |   | net.ipv4.tcp_dsack |   |
|   |   | net.ipv4.tcp_early_retrans |   |
|   |   | net.ipv4.tcp_keepalive_time | net.ipv4.tcp_keepalive_time小于601 大于9 |
|   |   | net.ipv4.tcp_keepalive_probes | 小于 6 大于1 |
|   |   | net.ipv4.tcp_keepalive_intvl | net.ipv4.tcp_keepalive_intvl 小于 61 大于1 |
|   |   | net.ipv4.tcp_tw_recycle | net.ipv4.tcp_tw_recycle=1 |
|   |   | net.ipv4.tcp_tw_reuse | net.ipv4.tcp_tw_reuse=1 |
|   |   | vm.min_free_kbytes | vm.min_free_kbytes>10240 |
|   |   | vm.swappiness` | vm.swappiness=1 |
|   | tune | tune部署与执行 | 计算节点服务器要求部署tune且正常执行 |
|   | 定时调度 | 状态与开启自启 | 服务器开启定时调度且设置开机自启动 |
| 软件部署 | JDK版本 | JDK版本 | 要求计算节点服务器JDK版本为1.7.0_80且所有计算节点服务器JDK版本一致 |
|   | MySQL | MySQL版本 | 各服务器安装的MySQL版本要求一致 |
|   |   | mysqld环境 | 各服务器安装的mysqld环境一致 |
|   |   | MySQL服务端开机自启动 | 要求安装了MySQL实例的服务器设置MySQL服务端开机自启动 |
|   | 备份程序 | 备份程序状态 | 存储节点服务器：备份程序已安装且正常运行 |
| 软件配置 | MySQL连接 | MySQL连接耗时 | 安装MySQL的服务器：连接MySQL实例并执行`select 1`总耗时小于1秒 |
|   | my.cnf | autocommit | 集群所有MySQL实例该参数要求设置成一致 |
|   |   | back_log | 设置大于500 |
|   |   | binlog_cache_size | 设置大于65535小于8388609 |
|   |   | binlog_format | 设置非STATEMENT |
|   |   | character_set_server | 集群所有MySQL实例该参数设置相同且在latin1、gbk、utf8、utf8mb4范围内 |
|   |   | character_set_database | 所有MySQL实例：character_set_database参数值、`show create database xxx`出来的字符集、character_set_server参数值、配置库表中配置的character_type参数值四个结果值一致 |
|   |   | collation_server | 所有MySQL实例：<br>该参数设置相同值且在latin1_swedish_ci、latin1_bin、 gbk_chinese_ci、gbk_bin 、utf8_general_ci、utf8_bin、 utf8mb4_general_ci、utf8mb4_bin范围内 |
|   |   | collation_database | 所有MySQL实例：collation_database与collation_server 参数值一致 |
|   |   | completion_type | 参数值为NO_CHAIN |
|   |   | div_precision_increment | 所有MySQL实例该参数设置一致 |
|   |   | expire_logs_days | 该参数大于1小于30 |
|   |   | explicit_defaults_for_timestamp | 所有MySQL实例该参数设置一致 |
|   |   | general_log | 该参数设置为OFF |
|   |   | group_concat_max_len | 该参数设置大于65535小于67108865 |
|   |   | innodb_buffer_pool_size | 所有MySQL实例：该参数大于34359738367且该服务器中所有MySQL实例的该参数值相加小于所在服务器总内存的80% |
|   |   | innodb_doublewrite | 所有MySQL实例该参数设置为ON |
|   |   | innodb_flush_log_at_trx_commit | 建议该参数设置为2并使用半同步复制或MGR |
|   |   | innodb_flush_method | 建议该参数设置为O_DIRECT |
|   |   | innodb_io_capacity | 该参数设置大于99小于10001 |
|   |   | innodb_large_prefix | 建议该参数设置为ON |
|   |   | innodb_log_file_size | 该参数大于4294967295小于68719476737或者大于innodb_buffer_pool_size/8小于innodb_buffer_pool_size/2 |
|   |   | innodb_open_files | 该参数设置大于1000 |
|   |   | innodb_rollback_on_timeout | 该参数设置为ON |
|   |   | innodb_support_xa | 该参数设置为ON |
|   |   | interactive_timeout | 所有MySQL实例：该参数大于配置库中配置的house_keeping_sleep_time字段值 |
|   |   | join_buffer_size | 该参数大于1048575小于67108865 |
|   |   | log_bin | 该参数设置为ON |
|   |   | long_query_time | 所有MySQL实例该参数配置一致 |
|   |   | lower_case_table_names | 该参数设置为1 |
|   |   | max_connect_errors | 该参数大于1000 |
|   |   | max_connections | 该参数大于配置库中配置的存储节点max_con字段值 |
|   |   | open_files_limit | 该参数大于10000 |
|   |   | optimizer_switch | 该参数中含有loosecan=off |
|   |   | query_cache_type | 该参数设置为OFF |
|   |   | read_buffer_size | 该参数大于262143小于67108865 |
|   |   | read_only | 主配置库与所有主存储节点MySQL实例上设置不为ON |
|   |   | read_rnd_buffer_size | 该参数大于524287小于67108865 |
|   |   | rpl_semi_sync_master_enabled | 若有该参数则要求设置为ON |
|   |   | rpl_semi_sync_slave_enabled | 若有该参数则要求设置为ON |
|   |   | server_id | 所有MySQL实例该参数不一致 |
|   |   | server_uuid | 所有MySQL实例该参数不一致 |
|   |   | skip_name_resolve | 建议该参数设置为ON |
|   |   | slave_skip_errors | 该参数设置为OFF |
|   |   | slow_query_log | 该参数设置为ON |
|   |   | sort_buffer_size | 该参数大于1048575小于67108865 |
|   |   | sql_mode | 该参数不含ONLY_FULL_GROUP_BY |
|   |   | sync_binlog | 该参数大于2（建议：所有MySQL实例sync_binlog参数设置值为10并使用半同步复制或MGR） |
|   |   | system_time_zone | 该参数配置一致 |
|   |   | table_open_cache | 该参数大于4000小于open_files_limit参数值 |
|   |   | thread_cache_size | 该参数大于64小于1024 |
|   |   | time_zone | 所有MySQL实例该参数配置一致 |
|   |   | tmp_table_size | 该参数大于262143小于1073741825 |
|   |   | tx_isolation | 所有MySQL实例该参数配置一致 |
|   |   | version | 所有MySQL实例该参数值一致 |
|   |   | version_comment | 建议该参数为MySQL Community Server (GPL) |
|   |   | wait_timeout | 该参数大于配置库中配置的存储节点house_keeping_sleep_time参数值 |
|   |   | rpl_semi_sync_master_status | 若有该参数且配有从机则要求该参数为ON状态 |
|   |   | rpl_semi_sync_slave_status | 若有该参数且配有主机则要求该参数为ON状态 |
|   |   | sysdate-is-now | 5.5版本及以下MySQL实例使用：`select sysdate(),sleep(1),sysdate();`，5.5版本以上使用`select sysdate(6),sleep(0.001),sysdate(6);`测试<br>要求两列时间相同 |
|   | MySQ磁盘空间 | MySQL实例数据目录绝对路径 | 所有MySQL实例：执行`show global variables like 'datadir';`命令返回结果为绝对路径 |
|   |   | MySQL实例数据目录剩余磁盘空间 | 所有MySQL实例数据目录剩余磁盘空间大于200G |
|   | MySQL高可用 | 高可用正确配置 | 同数据节点下的存储节点或一组配置库满足任一条件：<br>①使用了双1部署（sync_binlog=1且innodb_flush_log_at_trx_commit=1）且没有配置任何切换规则；<br>②有主从\双主\双主多从架构且开启了半同步，且复制运行正常且配置了切换规则；<br>③mgr架构且复制运行正常且online状态成员数大于2（不含2） |
|   |   | 高可用过度配置 | 同数据节点下的存储节点或一组配置库不为以下任一一种情况：①使用双1部署（sync_binlog=1且innodb_flush_log_at_trx_commit=1）且配置了切换规则且开启了半同步复制；②使用MGR复制且使用双1部署（sync_binlog=1且innodb_flush_log_at_trx_commit=1） |
|   | MySQL用户权限 | 复制用户权限 | 具有复制关系的配置库或存储节点MySQL实例：复制用户权限不低于（replication slave,replication client） |
|   |   | 配置库用户权限 | 配置库：连接用户权限不低于（select,insert,update,delete,create,drop,index,alter,create temporary tables,references,super,reload,lock tables,replication slave,replication client） |
|   |   | 存储节点连接用户 | 存储节点：连接用户权限不低于（select,insert,update,delete,create,drop,index,alter,process,references,super,reload,lock tables,replication slave,replication client,trigger,show view,create view,create routine,alter routine,execute,event）<br>注意：MySQL8.0及以上版本的存储节点连接用户还需拥有xa_recover_admin权限 |
|   |   | 存储节点备份用户 | 存储节点：备份用户权限不低于（select,insert,update,delete,create,drop,index,alter,reload,process,references,super,lock tables,replication slave,replication client,trigger,show view,create view,create routine,alter routine,event） |
|   | 计算节点高可用 | 计算节点模式 | 计算节点模式为主备或多节点模式且管理平台正常获取计算节点所在IP地址、VIP、计算节点角色 |
|   |   | 计算节点服务端口与管理端口正常可连接 | 各计算节点服务端口（主备模式备计算节点除外）与管理端口正常可连接 |
|   |   | VIP连接计算节点服务端口正常 | 主备模式或多节点模式集群可通过VIP正常连接计算节点服务端口 |
|   | server.xml | server.xml配置文件路径 | 管理平台配置的server.xml路径与hotdb-server报告的路径一致，且管理平台有读写权限 |
|   |   | Server.xml中配置库IP地址 | 使用真实IP地址非127.0.0.1 |
|   |   | processors | 要求配置参数大于等于服务器逻辑核数/8 小于等于逻辑核数 |
|   |   | processorExecutor | 参数小于9 |
|   |   | enableHeartbeat | 参数为true |
|   |   | heartbeatPeriod | 参数小于11秒大于heartbeatTimeoutMs且各计算节点配置一致 |
|   |   | heartbeatTimeoutMs | 参数小于5000且各计算节点配置一致 |
|   |   | enableLatencyCheck | 参数为true且各计算节点配置一致 |
|   |   | sqlTimeout | 参数小于86400且各计算节点配置一致 |
|   |   | haNodeHost（仅限主备模式） | 当前备计算节点该参数正确配置了当前主计算节点的IP和管理端口 |
|   |   | enableXA（仅限主备或多节点模式） | 各计算节点配置一致 |
|   |   | strategyForRWSplit | 各计算节点配置一致<br>各计算节点该参数为0 |
|   | 计算节点启动脚本 | -Xms ，-Xmx | 要求两个参数值一致且集群所有计算节点这两个参数一致 |
|   |   | -Xmx | 该参数大于等于1G小于等于256G<br>该参数大于等于8G时开启G1 |
|   |   | MaxDirectMemorySize | 该参数大于4G |
|   | 监听端口 | 计算节点：服务端口、管理端口、集群通信端口（仅限多节点模式） | 端口号范围：大于1024小于服务器上`sysctl net.ipv4.ip_local_port_range`查出来的第一个数字的数值 |
|   |   | 备份程序：监听端口 |   |
|   |   | MySQL实例（存储节点、配置库）：监听端口 |   |
|   |   | 管理平台：服务端口（若开启https，还需要https监听端口） |   |
|   |   | HotDB Listener ： 监听端口 |   |
|   |   | NDB：接收NDB 管理节点IP地址和端口、NDB SQL节点IP地址和端口 |   |
|   | 配置库 | 配置校验 | 通过配置校验检测 |
|   |   | 主备或MGR配置库数据一致性 | 各配置库实例表数量、表结构、表数据一致 |
|   |   | 主备配置库复制状态正常 | 复制状态正常且复制延迟小于1s |

