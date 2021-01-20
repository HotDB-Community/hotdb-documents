# 计算节点错误码说明

| 异常编号 | 异常状态 | 异常说明 | 引入版本 |
|-------|----------------------------------------------------|-------------------------------------------------|-------|
| 10000 | ER_BAD_LOGICDB | 错误的逻辑库名 | 2.5.1 |
| 10001 | ER_OPEN_SOCKET | 打开Socket连接异常 unused | 2.5.1 |
| 10002 | ER_CONNECT_SOCKET | 建立Socket连接异常 连接异常 | 2.5.1 |
| 10004 | ER_REGISTER | 注册连接异常 unused | 2.5.1 |
| 10005 | ER_READ | 读取数据异常 unused | 2.5.1 |
| 10006 | ER_PUT_WRITE_QUEUE | 数据写入写队列异常 unused | 2.5.1 |
| 10007 | ER_WRITE_BY_EVENT | 直接写出数据异常 unused | 2.5.1 |
| 10008 | ER_WRITE_BY_QUEUE | 从队列中写出数据异常 unused | 2.5.1 |
| 10009 | ER_HANDLE_DATA | 处理SQL异常 | 2.5.1 |
| 10010 | ER_NOT_SUPPORTED | 未支持的语句 | 2.5.1 |
| 10011 | ER_PART_NODE_COMMIT | 部分节点提交 | 2.5.1 |
| 10012 | ER_WARN_FIELD_TYPE_IS_BINARY | 分片字段或父子表 为二进制字段 | 2.5.1 |
| 10013 | ER_DATABASES_PARAM_DIFFER | MySQL参数检查中所有数据库不一致 | 2.5.1 |
| 10014 | ER_SLAVE_IS_READONLY_SWITCH_TO | MySQL参数检查中从机 read_Only = ON 且 配置切换规则 | 2.5.1 |
| 10015 | ER_MASTER_IS_READONLY | MySQL参数检查中主机 read_Only = ON | 2.5.1 |
| 10016 | ER_WARN_STRING_CHANGE_NUMBERIC_TYPE | 分片字段 列类型 字符串类型修改为数值型 | 2.5.1 |
| 10017 | ER_WARN_TIMES_DIFFERENCE_IS_TOO_LARGE | MySQL数据源与HotDB时间差异过大 | 2.5.1 |
| 10018 | ER_WARN_JOIN_COLUMN_TYPES_DIFFER | 父表与子表join列类型不一致 | 2.5.1 |
| 10019 | ER_PARAM_COMPLETION_TYPE_DIFFER | MySQL变量completion_type 类型不为NO_CHAIN | 2.5.1 |
| 10020 | ER_PARAM_DIV_PRECISION_INCREMENT_DIFFER | MySQL变量div_precision_increment 所有节点值不一致 | 2.5.1 |
| 10021 | ER_PARAM_INNODB_ROLLBACK_ON_TIMEOUT_DIFFER | MySQL变量innodb_rollback_on_timeout 类型不为ON | 2.5.1 |
| 10022 | ER_WARN_SLAVE_IS_READONLY_NO_SWITCH_TO | MySQL参数检查 从机 read_Only = ON 但未配置切换规则 | 2.5.1 |
| 10023 | ER_MYSQL_TIMES_DIFFERENCE_IS_TOO_LARGE | MySQL与HotDB服务器时间间隔 太大(大于3秒) | 2.5.1 |
| 10024 | ER_WARN_MYSQL_TIMES_DIFFERENCE_IS_WARN | MySQL与HotDB服务器时间间隔 有点大(0.5～3秒) | 2.5.1 |
| 10025 | ER_PARAM_AUTOCOMMIT_DIFFER | MySQL变量autocommit 所有节点值不一致 | 2.5.1 |
| 10026 | ER_PARAM_TX_ISOLATION_DIFFER | MySQL变量tx_isolation 所有节点值不一致 | 2.5.1 |
| 10027 | ER_WARN_PARAM_TX_ISOLATION_TO_LOW | MySQL变量tx_isolation 所有节点值一致,但值低于REPEATABLE-READ | 2.5.1 |
| 10028 | ER_WARN_INNODB_ROLLBACK_ON_TIMEOUT_NOT_ON | 数据源MySQL变量innodb_rollback_on_timeout 类型全部不为ON | 2.5.1 |
| 10029 | ER_NOT_PASS_SQL_FIREWALL | 无法通过SQL拦截器 | 2.5.1 |
| 10030 | ER_ACCESS_DENIED_WHITE_LIST | 无法通过白名单 | 2.5.1 |
| 10031 | ER_LIMITOFFSET_WITHOUT_ORDERBY | 无Order by的limit offset | 2.5.1 |
| 10032 | ER_CREATE_WITHOUT_PRIMARY_AND_UNIQUE_KEY | 无主键或唯一键的建表语句 | 2.5.1 |
| 10033 | ER_RISKY_TRX_ISOLATION | 使用了可能不一致的隔离级别 | 2.5.1 |
| 10034 | ER_WARN_HOTDB_MAX_ALLOWED_PACKET_GREATERTHAN_MYSQL | HotDB的最大包默认值大于数据源 变量max_allowed_packet | 2.5.1 |
| 10035 | ER_FAILOVER_NOT_AlLOWED | MGR不允许手动切换数据源 | 2.5.1 |
| 10036 | ER_NO_FAILOVER_CONFIG | 未配置切换规则 | 2.5.1 |
| 10037 | ER_DATANODE_IN_TRANSFER | 数据节点正在进行迁移 | 2.5.1 |
| 10038 | ER_NO_AVAILABLE_BACKUP | 无可用的备数据源供切换 | 2.5.1 |
| 10039 | ER_FAILOVER_INTERRUPTED | 数据源切换被中断 | 2.5.1 |
| 10040 | ER_WRONG_DN | 错误的数据节点号 | 2.5.1 |
| 10041 | ER_FEATURE_FORBIDDEN | 禁止的功能 | 2.5.1 |
| 10042 | ER_DN_INCONSISTENT | 数据节点配置不一致 | 2.5.1 |
| 10043 | ER_INVALID_POOL_VERSION | 失效的连接池版本 | 2.5.1 |
| 10044 | ER_BUFFER_QUEUE_OVERFLOW | buffer队列超出限制 | 2.5.1 |
| 10045 | ER_LOAD_CONFIG_FAILURE | 加载配置失败 | 2.5.1 |
| 10046 | ER_CONF_LOAD_DS_FAILURE | 加载数据源配置失败 | 2.5.1 |
| 10047 | ER_CONF_LOAD_DN_FAILURE | 加载数据节点配置失败 | 2.5.1 |
| 10048 | ER_CONF_LOAD_FAILOVER_FAILURE | 加载故障切换配置失败 | 2.5.1 |
| 10049 | ER_CONF_LOAD_DB_FAILURE | 加载逻辑库配置失败 | 2.5.1 |
| 10050 | ER_CONF_LOAD_TABLE_FAILURE | 加载表配置失败 | 2.5.1 |
| 10051 | ER_CONF_LOAD_FUNC_FAILURE | 加载分片函数配置失败 | 2.5.1 |
| 10052 | ER_CONF_LOAD_RULE_FAILURE | 加载分片规则配置失败 | 2.5.1 |
| 10053 | ER_CONF_LOAD_STATUS_FAILURE | 加载server状态失败 | 2.5.1 |
| 10054 | ER_CONF_LOAD_STATISTICS_FAILURE | 加载server统计信息失败 | 2.5.1 |
| 10055 | ER_CONF_LOAD_WHITELIST_FAILURE | 加载白名单配置失败 | 2.5.1 |
| 10056 | ER_CONF_LOAD_CONF_INFO_FAILURE | 加载配置信息失败 | 2.5.1 |
| 10057 | ER_CONF_LOAD_SQL_FIREWALL_FAILURE | 加载SQL防火墙配置失败 | 2.5.1 |
| 10058 | ER_CONF_LOAD_XID_FAILURE | 加载XID失败 | 2.5.1 |
| 10059 | ER_CONF_LOAD_USER_FAILURE | 加载user失败 | 2.5.1 |
| 10060 | ER_CONF_LOAD_USER_DB_PRIVILEGE_FAILURE | 加载user库权限失败 | 2.5.1 |
| 10061 | ER_CONF_LOAD_USER_TABLE_PRIVILEGE_FAILURE | 加载user表权限失败 | 2.5.1 |
| 10062 | ER_CONF_LOAD_SERVER_CONFIG_FAILURE | 加载hotdb_config_info中hotdb_server_config失败 | 2.5.1 |
| 10063 | ER_CONF_LOAD_SERVER_XML | 加载server.xml失败 | 2.5.1 |
| 10064 | ER_CONF_PERSIST_SQL_FIREWALL_FAILURE | 持久化SQL防火墙配置失败 | 2.5.1 |
| 10065 | ER_CONF_PERSIST_RUNNING | 更新配置库对应RUNNING表失败 | 2.5.1 |
| 10066 | ER_CONF_PERSIST_CONF_INFO | 更新hotdb_config_info表失败 | 2.5.1 |
| 10067 | ER_CONF_PERSIST_FUNC_INFO_RUNNING | 更新hotdb_function_info_running表失败 | 2.5.1 |
| 10068 | ER_CONF_PERSIST_RUNNING_TABLE | 更新running表失败 | 2.5.1 |
| 10069 | ER_CONF_NO_MOD_COUNT | MOD配置中无模值 | 2.5.1 |
| 10070 | ER_CONF_UNSUPPORT_FUNC | 不支持的分片函数 | 2.5.1 |
| 10071 | ER_CONF_NO_CHARACTER_TYPE | 数据源未配置character_type | 2.5.1 |
| 10072 | ER_CONF_UNSUPPORTED_CHARACTER_TYPE | 不支持的数据源character_type | 2.5.1 |
| 10073 | ER_CONF_MIXED_CHARACTER_TYPE | 数据源character_type不一致 | 2.5.1 |
| 10074 | ER_CONF_UNKNOWN_FUNC | 未定义的分片函数 | 2.5.1 |
| 10075 | ER_CONF_EMPTY_DN | 数据节点无数据源 | 2.5.1 |
| 10076 | ER_CONF_INVALID_DB_NAME | 无效的逻辑库名称 | 2.5.1 |
| 10077 | ER_CONF_UNKNOWN_DN | 未知的数据节点 | 2.5.1 |
| 10078 | ER_CONF_MULTI_DN_IN_VERTICAL | 垂直表中有多个数据节点 | 2.5.1 |
| 10079 | ER_CONF_PARENT_NOT_SHARD | 父表不是分片表 | 2.5.1 |
| 10080 | ER_CONF_PARENT_WITHOUT_JOINKEY | 父表未包含关联字段 | 2.5.1 |
| 10081 | ER_CONF_CHILD_WITHOUT_JOINKEY | 子表未包含关联字段 | 2.5.1 |
| 10082 | ER_CONF_NO_SHARD_COLUMN | 分片规则无分片字段 | 2.5.1 |
| 10083 | ER_CONF_DB_WITHOUT_DN | 逻辑库下无数据节点 | 2.5.1 |
| 10084 | ER_CONF_NO_TABLE_DEFINE | 表结构未定义 | 2.5.1 |
| 10085 | ER_CONF_NO_SUCH_TABLE | 表未配置 | 2.5.1 |
| 10086 | ER_CONF_INVALID_ISO_LEVEL | 无效的隔离级别 | 2.5.1 |
| 10087 | ER_GLOBAL_RANDOM_UPDATE_WITHOUT_KEY | 对无主键/唯一键的全局表进行非确定性函数UPDATE | 2.5.1 |
| 10088 | ER_GLOBAL_RANDOM_DELETE_WITHOUT_KEY | 对无主键/唯一键的全局表进行非确定性函数DELETE | 2.5.1 |
| 10089 | ER_REWRITE_SYNTAX_ERROR | 改写后的语句语法错误 | 2.5.1 |
| 10090 | ER_ILLEGAL_ARGUMENT | 参数错误 | 2.5.1 |
| 10091 | ER_DATA_INCONSIST | onlineddl后数据不一致 | 2.5.1 |
| 10092 | ER_NULL_ROUTE_RESULT | 空的路由结果 | 2.5.1 |
| 10093 | ER_NULL_SESSION | 空的会话 | 2.5.1 |
| 10094 | ER_TRUNCAT_PARENT_IN_BINDED | 在绑定连接会话中truncate父表 | 2.5.1 |
| 10095 | ER_NO_SUCH_AUTH_ALGO | 找不到解密密码的对应算法 | 2.5.1 |
| 10096 | ER_UNKNOWN_RESULT_STATUS | 未知的结果集状态 | 2.5.1 |
| 10097 | ER_UNKNOWN_PACKET | 未知的数据包 | 2.5.1 |
| 10098 | ER_UNKNOWN_CHARSET | 未知的字符集 | 2.5.1 |
| 10099 | ER_CONNECTION_CLOSED | 连接已经断开 | 2.5.1 |
| 10100 | ER_DIFF_JOIN_KEY | 父表关联字段已经存在另一节点上 | 2.5.1 |
| 10101 | ER_NULL_CHILD_JOIN_KEY | 子表的关联字段为NULL | 2.5.1 |
| 10102 | ER_NO_PRIMARY_OR_UNIQUE_KEY | 表中不存在主键或唯一键 | 2.5.1 |
| 10103 | ER_SAME_TABLE_IN_SAME_DN | 节点下已有同名的表 | 2.5.1 |
| 10104 | ER_NO_ROUTE_TO_NULL | 未配置NULL值路由 | 2.5.1 |
| 10105 | ER_FORBIDDEN_MULTINODE_SQL | 禁止执行的跨节点SQL | 2.5.1 |
| 10106 | ER_NO_SHARD_COLUMN | SQL中未包含分片字段 | 2.5.1 |
| 10107 | ER_NO_SHARD_VALUE | SQL中未包含分片字段的值 | 2.5.1 |
| 10108 | ER_WRONG_SHARD_VALUE | 错误的分片字段的值 | 2.5.1 |
| 10109 | ER_NO_JOIN_KEY | SQL中未包含关联字段 | 2.5.1 |
| 10110 | ER_FOUND_NO_PARENT | 找不到对应的父表 | 2.5.1 |
| 10111 | ER_EXECUTE_ERROR | 执行Unit异常 | 2.5.1 |
| 10112 | ER_SWITCH_NOT_ON | 功能开关未开启 | 2.5.1 |
| 10113 | ER_RULE_NOT_CONFIGURED | 分片规则未配置 | 2.5.1 |
| 10114 | ER_TABLE_CONF_INCONSISTENT | 表配置不一致 | 2.5.1 |
| 10115 | ER_SERVER_ID_CONFLICS | 所有数据源中server_id存在冲突 | 2.5.1 |
| 10116 | ER_JOIN_CALC_PLAN | 计算JOIN执行计划时出现异常 | 2.5.1 |
| 10117 | ER_JOIN_FILTER_ROW | 过滤JOIN行时出现异常 | 2.5.1 |
| 10118 | ER_JOIN_CALC_RESULT | 计算JOIN结果时出现异常 | 2.5.1 |
| 10119 | ER_GLOBAL_RND_IUD | 全局表非确定性函数代理异常 | 2.5.1 |
| 10120 | ER_INSERT_SELECT | INSERT INTO SELECT计算异常 | 2.5.1 |
| 10121 | ER_AUTO_SHARDING_CALC_TABLE_SIZE | 推荐自动分片时计算表大小异常 | 2.5.1 |
| 10122 | ER_AUTO_SHARDING_DUP_TASK | 已经存在另一个自动分片推荐计算任务 | 2.5.1 |
| 10123 | ER_AUTO_SHARDING_EXCEPTION | 自动分片推荐计算任务异常 | 2.5.1 |
| 10124 | ER_GLOBAL_INCONSIST_CHECK | 全局表一致性检测异常 | 2.5.1 |
| 10125 | ER_GLOBAL_INCONSIST_REPAIR | 全局表一致性修复异常 | 2.5.1 |
| 10126 | ER_RESHARD_NO_SHARD_COLUMN | 自动修改的分片规则中未找到分片字段 | 2.5.1 |
| 10127 | ER_RESHARD_EXCEPTION | 自动修改分片规则时出现异常 | 2.5.1 |
| 10128 | ER_RESHARD_NO_SUCH_DB | 自动修改分片规则中不存在该库 | 2.5.1 |
| 10129 | ER_RESHARD_NO_SUCH_TABLE | 自动修改分片规则中不存在该表 | 2.5.1 |
| 10130 | ER_RESHARD_NO_SUCH_SHARD_FUNCTION | 自动修改分片规则中不存在该分片函数 | 2.5.1 |
| 10131 | ER_RESHARD_TABLE_UNDEFINED | 自动修改分片规则中表未定义 | 2.5.1 |
| 10132 | ER_RESHARD_PARENT_TABLE_UNSUPPORT | 自动修改分片规则中不支持父表 | 2.5.1 |
| 10133 | ER_RESHARD_BAD_ARGS | 自动修改分片规则中参数错误 | 2.5.1 |
| 10134 | ER_RESHARD_CHECK_EXCEPTION | 自动修改分片规则中检测异常 | 2.5.1 |
| 10135 | ER_RESHARD_EXIST_TRIGGER_OR_FOREIGN_KEY | 自动修改分片规则中检测到触发器或外键 | 2.5.1 |
| 10136 | ER_NOT_SHARD_TABLE | 该表不是分片表 | 2.5.1 |
| 10137 | ER_OL_DDL_ADD_AUTO_INC | onlineddl中增加自增字段 | 2.5.1 |
| 10138 | ER_OL_DDL_WRONG_TABLE_LEN | onlineddl中表名过长 | 2.5.1 |
| 10139 | ER_OL_DDL_DUP_TASK | onlineddl中已经存在另一个任务 | 2.5.1 |
| 10140 | ER_OL_DDL_ALTER_KEY_COLUMN | onlineddl中不允许更改分片字段和关联字段 | 2.5.1 |
| 10141 | ER_OL_DDL_ADD_COLUMN_FAILED | onlineddl中添加字段失败 | 2.5.1 |
| 10142 | ER_OL_DDL_ADD_FOREIGN_KEY | onlineddl中不允许增加外键 | 2.5.1 |
| 10143 | ER_OL_DDL_ERROR | onlineddl中出现异常 | 2.5.1 |
| 10144 | ER_MS_CONSIST_CHECK_ERROR | 主从一致性检测中出现异常 | 2.5.1 |
| 10145 | ER_FETCH_LOG_EXCEPTION | 获取HotDB日志异常 | 2.5.1 |
| 10146 | ER_HACHECK_ALREADY_DONE | hacheck已被禁用/启用 | 2.5.1 |
| 10147 | ER_HOLD_FAILED | hold住失败 | 2.5.1 |
| 10148 | ER_UNHOLD_FAILED | 解除hold住失败 | 2.5.1 |
| 10149 | ER_WRONG_DS | 错误的数据源 | 2.5.1 |
| 10150 | ER_OFFLINE_FAILED | offline失败 | 2.5.1 |
| 10151 | ER_ONLINE_FAILED | online失败 | 2.5.1 |
| 10152 | ER_REBUILD_POOL_FAILED | 重建连接池失败 | 2.5.1 |
| 10153 | ER_QUERY_FAILED | 后端查询失败 | 2.5.1 |
| 10154 | ER_PWD_DECRYPT_FAILED | 密码解密失败 | 2.5.1 |
| 10155 | ER_SET_SYSCONF_FAILED | 设置系统参数失败 | 2.5.1 |
| 10156 | ER_SHOW_SERVER_EXCEPTION | show @@server异常 | 2.5.1 |
| 10157 | ER_SHOW_SERVER_USAGE_EXCEPTION | show @@ServerSourceUsage异常 | 2.5.1 |
| 10158 | ER_DS_SWITCH_FAILED | 数据源手动切换失败 | 2.5.1 |
| 10159 | ER_TRANSFER_CHECK_FAILED | 一键迁库检查失败 | 2.5.1 |
| 10160 | ER_UNKNOWN_TRANSFER_HISTORY | 未知的一键迁库历史记录 | 2.5.1 |
| 10161 | ER_WRONG_CONF | 错误的HotDB配置 | 2.5.1 |
| 10162 | ER_INNER_AGGRE_EXCEPTION | 内嵌聚合函数计算异常 | 2.5.1 |
| 10163 | ER_UNION_EXCEPTION | UNION计算异常 | 2.5.1 |
| 10164 | ER_SUBQUERY_EXCEPTION | 子查询计算异常 | 2.5.1 |
| 10165 | ER_XA_LOG_FAILED | XA日志记录失败 | 2.5.1 |
| 10166 | ER_MGR_BAD_MASTER | MGR中Master不属于当前节点 | 2.5.1 |
| 10167 | ER_MGR_FOUND_NO_MASTER | MGR中找不到Master | 2.5.1 |
| 10168 | ER_MGR_FAILOVER_EXCEPTION | MGR故障切换异常 | 2.5.1 |
| 10169 | ER_CHECK_TABLE_DEF | 检查表结构异常 | 2.5.1 |
| 10170 | ER_MODIFY_AUTO_INC | 修改自增列的警告 | 2.5.1 |
| 10171 | ER_EXTERNAL_SORT | 外排序计算异常 | 2.5.1 |
| 10172 | ER_PERSIST_TABLE_CONF | 持久化表配置到配置库失败 | 2.5.1 |
| 10173 | ER_TRUNCATE_PARENT_TABLE | 清空父表失败 | 2.5.1 |
| 10174 | ER_ALTER_INCONSIST_GLOBAL | 对数据不一致的表进行ALTER操作 | 2.5.1 |
| 10175 | ER_NO_SATISFIED_TABLE | 没有满足条件的表 | 2.5.1 |
| 10176 | ER_DN_WITHOUT_TABLE | 部分节点上不存在该表 | 2.5.1 |
| 10177 | ER_TABLE_DDL_INCONSIST | 表结构不一致 | 2.5.1 |
| 10178 | ER_ADD_FOREIGN_KEY | 增加外键失败 | 2.5.1 |
| 10179 | ER_ADD_AUTO_INC_WITH_NUL | 对含有NULL值的分片字段增加自增属性 | 2.5.1 |
| 10180 | ER_DDL_EXCEPTION | DDL执行异常 | 2.5.1 |
| 10181 | ER_DROP_PARENT_TABLE | DROP父表失败 | 2.5.1 |
| 10182 | ER_HANDLE_QUERY_EXCEPTION | 处理查询异常 | 2.5.1 |
| 10183 | ER_XA_ERROR_IN_MGR | 位于MGR节点上的XA事务异常 | 2.5.1 |
| 10184 | ER_TRANS_ERROR | 事务发生异常 | 2.5.1 |
| 10185 | ER_DN_OUT_OF_SERVICE | 数据节点不可用 | 2.5.1 |
| 10186 | ER_CONF_DUP_RELOAD | 另一个用户正在执行配置的热加载 | 2.5.1 |
| 10187 | ER_CONF_NO_AVAILABLE_CONF_DS | 无可用配置库 | 2.5.1 |
| 10188 | ER_CONF_RELOAD_CONF_DN_FAILED | 配置库节点重加载失败 | 2.5.1 |
| 10189 | ER_CONF_RELOAD_DN_INIT_FAILED | 热加载时数据节点初始化失败 | 2.5.1 |
| 10190 | ER_NODE_LIMIT_EXCEED | 节点数超出授权限制 | 2.5.1 |
| 10191 | ER_CONF_RELOAD_FAILED | 配置重加载失败 | 2.5.1 |
| 10192 | ER_PERMISSION_DENIED | 操作由于权限问题被拒绝 | 2.5.1 |
| 10193 | ER_PARAM_EXPLICIT_TIME_DIFFER | MySQL变量explicit_defaults_for_timestamp所有节点值不一致 | 2.5.1 |
| 10194 | ER_ILLEGAL_MYSQL_VERSION | MySQL版本不合法 | 2.5.1 |
| 10195 | ER_WARN_BINDED_SESSION_CONNECTION | warn 级别, 绑定连接会话 | 2.5.1 |
| 10196 | ER_INFO_BINDED_SESSION_CHANGEDATABASE | info 级别, 绑定连接会话换库 | 2.5.1 |
| 10197 | ER_CONFIG_MS_INCONSISTENT | 配置库不一致 | 2.5.2 |
| 10198 | ER_AUX_INITED_EXCEPTION | 辅助表初始化异常 | 2.5.2 |
| 10199 | ER_AUX_UNIQUEKEY_CONFLICT | 全局唯一键冲突 | 2.5.2 |
| 10200 | ER_AUX_DATA_EXCEPTION | 表数据异常 | 2.5.2 |
| 10201 | ER_UNEVALUATABLE_KEY | 索引列为无法计算的表达式 | 2.5.2 |
| 10202 | ER_MICROBENCHMARK_EXCEPTION | 简单性能测试异常 | 2.5.2 |
| 10203 | ER_DDL_EXECUTE_EXCEPTION | DDL执行异常 | 2.5.2 |
| 10204 | ER_UPDATE_SHARDING_COL | 更新分片字段 | 2.5.1 |
| 10205 | ER_CREATE_FUNCTIONAL_INDEX_IN_GLOBAL_INDEX_MODE | 在全局唯一模式使用第一个索引列为函数时无效 | 2.5.3 |
| 10206 | ER_PARAM_DEADLOCK_DETECT_OFF | MySQL8.0死锁检测未开启 | 2.5.3 |
| 10207 | ER_UPDATE_SEQ | MySQL8.0更新序列号 | 2.5.3 |
| 10208 | ER_CONFIG_CONFLICT | 表配置冲突 | 2.5.3 |
| 10209 | ER_VERSION_TOO_LOW | hotdb版本过低 | 2.5.3 |
| 10210 | ER_GLOBAL_UNIQUE_INVALID | 全局唯一未生效 | 2.5.3 |
| 10211 | ER_SPACE_NOT_ENOUGH | 磁盘空间不足 | 2.5.3 |
| 10212 | ER_NOT_BINGINT_AUTOINCREMENT | 非bigint的自增 | 2.5.4 |
| 10213 | ER_NOT_EXIST_AUTOINCREMENT | 不存在自增 | 2.5.4 |
| 10214 | ER_USING_AUTOINCREMENT | 存在使用自增 | 2.5.4 |
| 10215 | ER_NODE_NO_REPEATED | 建表时被select的表无共用节点 | 2.5.4 |
| 10216 | ER_CREATE_TABLE_SELECT | 创SELECT建表异常 | 2.5.4 |
| 10217 | ER_UPDATE_SHARDING_COL_CHANGED_DN | 更新分片字段导致节点变化 | 2.5.4 |
| 10218 | ER_INTO_OUTFILE_FAILED | 输出文件失败 | 2.5.4 |
| 10219 | ER_CANCEL_ONLINE_MODIFY_RULE_FAILED | 取消在线修改分片规则失败 | 2.5.1 |
| 10220 | ER_CONF_LOAD_HOST_SSH_INFO_FAILURE | 加载hotdb_host_ssh_info库权限失败 | 2.5.5 |
| 10221 | ER_SSH_INFO_INVAILD_PASSWORD | SSH信息中存在无效密码 | 2.5.5 |
| 10222 | ER_SSH_INFO_CONNECT_FAILED | SSH连接失败 | 2.5.5 |
| 10223 | ER_LOAD_SSHINFO_EXCEPTION | 加载SSH信息失败 | 2.5.5 |
| 10224 | ER_PING_EXCEPTION | ping过程发生异常 | 2.5.5 |
| 10225 | ER_SYSTEM_CONF_CHECK_FAILED | system参数检测失败 | 2.5.5 |
| 10226 | ER_DROP_TEMP_TABLE_FAILED | drop回收站临时表失败 | 2.5.5 |
| 10227 | ER_ROLLBACK_TABLE_ALREADY_CREATED | 回收站表rename成已经创建的非原表 | 2.5.5 |
| 10228 | ER_SEQUENCE_OVERFLOW | 序列号溢出 | 2.5.6 |
| 10229 | ER_SEQUENCE_ALTER_EXCEPTION | 序列号更新时异常 | 2.5.6 |
| 10230 | ER_SEQUENCE_APPLY_EXCEPTION | 序列号申请异常 | 2.5.6 |
| 10231 | ER_BAD_SEQUENCE | 错误的序列号 | 2.5.6 |
| 10232 | ER_ROLLBACK_TABLE_ALREADY_CREATED | 回收站表rename成已经创建的非原表 | 2.5.6 |
| 10233 | ER_INCONSIST_CONF_SWITCH_DN | 切换时配置库和内存配置不一致 | 2.5.6 |
| 10234 | ER_NOT_ALLOWED_EXECUTE_NONQUERY_CMD | 不允许执行非查询命令 | 2.5.6 |
| 10235 | ER_FAILOVER_NOT_AlLOWED_IN_DR_MODE | DR模式不允许切换 | 2.5.6 |
| 10236 | ER_VIP_NOT_IN_MASTER | VIP不在主上 | 2.5.6 |
| 10237 | ER_MEMORY_CONFIG_IS_NOT_SAME | 内存配置不一致 | 2.5.6 |
| 10238 | ER_EXIST_DATASOURCE_UNAVAILABLE | 存在不可用数据源 | 2.5.6 |
| 10239 | ER_DATASOURCE_REPL_EXCEPTION | 数据源或者配置库复制关系异常 | 2.5.6 |
| 10240 | ER_DATASOURCE_LARGE_LATENCY | 数据源或者配置库延迟过大 | 2.5.6 |
| 10241 | ER_DATASOURCE_IN_FAILOVER | 数据源或者配置库在切换中 | 2.5.6 |
| 10242 | ER_DR_REPL_CANNOT_CATCHUP | 灾备机房复制无法追上 | 2.5.6 |
| 10243 | ER_RESET_MASTER_BETWEEN_IDC | 清理机房间复制关系失败 | 2.5.6 |
| 10244 | ER_BUILD_REPL_IN_DR | 重建灾备机房复制关系失败 | 2.5.6 |
| 10245 | ER_RESET_MASTER_IN_BIZ | 清理业务机房内部复制关系失败 | 2.5.6 |
| 10246 | ER_REBUILD_REPL_BETWEEN_IDC | 重建机房间复制关系失败 | 2.5.6 |
| 10247 | ER_UPDATE_CONFIG | 机房切换更新配置失败 | 2.5.6 |
| 10248 | ER_UPDATE_MEMORY_CONFIG | 机房切换更新内存配置失败 | 2.5.6 |
| 10249 | ER_ENABLE_NON_QUERY_CMD_AND_ELECTION | 允许非插叙命令和选举失败 | 2.5.6 |
| 10250 | ER_LINSTEN_ON_SERVER_PORT | 机房切换开放服务端口失败 | 2.5.6 |

