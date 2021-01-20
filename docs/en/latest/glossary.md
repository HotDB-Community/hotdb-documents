# Glossary

## Common Nouns

### Cluster

Cluster usually refers to the whole set of architectural components required in the use of distributed transaction database HotDB Server. It generally includes compute node, data source, ConfigDB, management platform, backup program, etc. Different clusters are independent in business, and business of multiple clusters can also be managed simultaneously in the management platform.

### Compute Node

Compute node is the core of distributed transaction database HotDB Server cluster system. It provides the core control functions of distributed transactional database, such as SQL parsing, routing distribution and result set merging, and is the lifeblood of the whole distributed service.

**Service Port:** compute node will open two ports, one of which is the service port, which is 3323 by default. It is the channel through which the client application connects compute node to obtain the data service. It is similar to the port of MySQL instance.

**Management Port:** this is another port that the compute node opens. It mainly provides information monitoring and management, such as viewing the client connection information in the current compute node, rebuilding the connection pool, etc.

### ConfigDB

ConfigDB is essentially a database in a MySQL instance, mainly responsible for storing the compute node or [management platform](#management-platform) related configuration information. It is also used to temporarily store cache of part of complex query statements. The configDB can perform high availability configuration through master-slave or MGR.

### Data Node

A data node is a group of data sources with the same data copy. A data node can be a MySQL MGR cluster or a MySQL master-slave replication cluster. The data node manages the replication relations of a set of data sources with the same data copies. As sharding data in HotDB, all data nodes together constitute the total data of HotDB.

### DNID

DNID is also called Datanode or datanode_id, that is, the number of data node.

### Data Source

This is the MySQL database which makes actual storage of data, and IP+ port + physical database could determine a data source. To realize the function of high availability and data multi-copy, a group of data sources with the same data copy are called one **data node** in HotDB.

### Switching Rule

The function of high-availability switching can provide high-availability for data source, but the user needs to configure switching rule for the data source under the data node in management platform, that is, the configuration information and its priority of switching to the slave database when master data source fails. The data node not configured with switching rule will not switch when the underlying master data source fails.

### Heartbeat

Heartbeat is a detection strategy for the high availability of compute node to data source. Compute node will periodically send heartbeat detection to the underlying data source. Normal feedback of data source to the compute node detection shows that the data source is currently in a normal state. If the data source fails to give feedback to compute node detection within the specified time and frequency, the compute node will consider that the data source is abnormal and then switch to data node high availability.

### Data Source Group

A group of data sources with the same attributes is classified as a data source group. When the attribute of all data sources in this group that the user needs to modify in batches is the same value, the user can directly modify the attribute value of the data source group, and the corresponding attribute values of all data sources in this group will be modified to new values. With this function, it is easy to modify a batch of data source attribute values with similarity and can keep its settings consistent.

### LogicDB

LogicDB (LogicDatabase, LDB for short) refers to a collection of database that can be accessed and describe database table after the client program is connected to the compute node server. It is similar to a database that is seen when the MySQL service is directly connected. LogicDB is a concept derived from the product, with no single and specific entity in reality.

### Table Configuration

Table Configuration refers to the configuration rule information for database tables of the user's own business defined in management platform. The defined table can be normally used after the table structure is created in [compute node](#compute-node) data service port. The following types of tables can be created currently: Sharding Table, Vertical Sharding Table, Global Table, and Child Table.

| Table Type | Definitions |
|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Sharding Table | Data rows are split according to sharding key and sharding function, and stored in multiple specified data nodes. This is suitable for table with large amount of data. |
| Global Table | A table with the same table structure and the same table data is stored in all data nodes under this LogicDB. This is suitable for tables with small amount of data or frequent JOIN operation; this is not suitable for tables with frequent modification operations. |
| Vertical Sharding Table | A table associated with the only data node and with no sharding function; no sharding or replication in different data nodes. |
| Child Table | Parent table is associated with child table through the associated field of parent table (sharding table). Child table data is stored in the location of parent table associated fields according to the value of associated fields. Creating child table can help reduce some cross-node data operation, but it is no longer recommended currently. |

### Sharding Function

Sharding function is that the data in sharding table is split through corresponding sharding function. Sharding function determines the data node on which a sharding field data as the data record of a specific value is stored. Currently, the compute node supports the following sharding types: ROUTE, RANGE, MATCH, HASH (abandoned after version 2.4.6), SIMPLE_MOD, CRC32_MOD (newly-added in version 2.4.7), AUTO (enabled after version 2.4.6), AUTO_CRC32 (newly-added in version 2.4.7), AUTO_MOD (newly-added in version 2.4.7).

**Sharding Key**

Sharding key is the judgment basis when sharding function routes the table data. Sharding function calculates the sharding key value as the input value of the sharding function, and splits the data according to the result.

### Database User

It is used to connect the compute node to access specific logicDB or log into the account of management port of compute node. It is similar to the concept of user in MySQL, and the user information needs to be configured and managed in the [management platform](#management-platform).

## Nouns of Components

### Compute Node

Please refer to the description of "[Compute Node](#compute-node)" in Common Nouns.

### NDB SQL Service

NDB SQL service is a service program introduced for compute node to support complex query SQL scene, used to complete the calculation of relatively complex query statement in distributed environment.

### Management Platform

Distributed transactional database management platform (hereinafter referred to as management platform) is also known as HotDB Management. It could realize usability configuration of compute node database user, data node, table type, sharding function and other information, and meanwhile, it could also provide monitoring of compute node service status, reminder of abnormal events, view of statements, management of tasks and other intelligent operation and maintenance related services.

### Data Source

Please refer to the description of "[Data Source](#data-source)" in Common Nouns.

### ConfigDB

Please refer to the description of "[ConfigDB](#configdb)"in Common Nouns.

### High Availability

Compute node of HotDB Server can check availability and switch the high availability of compute node in master/slave mode with Keepalived high availability solution.

### Load Balancing

HotDB Server can distribute SQL requests with LVS and other methods. Client can access to the compute node services of HotDB Server with LVS VIP, and ensure the transparent and uninterrupted services. Other load balancing programs can be used instead. For example, F5+custom detection, direct connection of application to compute node, and replacement of compute node when an abnormality occurs.

### HotDB Backup

The distributed transactional database backup program self-innovated by Hotpu is used for business data backup.

### HotDB Listener

The distributed transactional database Listener self-innovated by Hotpu is used to solve the performance linear expansion problem of the compute node in strong consistency mode.

## HotDB Management Related Nouns

### Cluster Mode

Cluster Mode is a kind of architecture when compute node is actually deployed. Generally, the cluster mode of current compute node can be determined according to the number of compute nodes. Currently, three cluster modes are supported: Single Node, Master/Slave Node, and Multi-Node.

**Single Node**

When the cluster mode is single node, there will be one compute node instance in the actually deployed architecture, which is also called single master cluster mode. This architecture does not have compute node high-availability function; it can be deployed in a test environment but is not recommended to select in a production environment.

**Master/Slave Node**

When the cluster mode is the master/slave node, there will be two compute node instances in the actually deployed architecture, that is, master compute node and slave compute node, which is also called HA cluster mode. In this mode, the compute node has master/slave roles status, and the health status of the compute node and the maintenance of VIP can be detected with Keepalived components.

**Multi-Node**

When the cluster node is the multi-node, there will be multiple compute node instances (the number of instances is greater than or equal to 3 and less than or equal to 9) in the actually deployed architecture. Each compute node instance can uniformly distribute or customize the distribution of traffic through load balancing components, and guarantee that the business will not be interrupted as long as there is one compute node instance available in the cluster.

### Compute Node High-Availability Switching

It generally refers to the compute node whose cluster mode is master/slave node. When the master compute node fails, [Keepalived](#high-availability) drifts the VIP to slave compute node. At this time, the slave compute node takes over the front-end application service and respond to the demands. High-availability switching can occur when the master compute node fails or it can be switched manually.

### High-Availability Environment Reconstruction

It generally refers to the compute node whose cluster mode is master/slave node. High-availability environment reconstruction needs to be operated manually after high availability switching fails so as to prepare for the smooth switching of compute node when the next failure occurs. If reconstruction is not carried out, the switched compute node will not be automatically switched successfully when subsequent failure occurs. High-availability environment reconstruction mainly correspondingly modifies the master/slave compute node and keepalived related configurations to guarantee that the compute node can automatically switch in case of the failure.

## Glossary of cross-IDC disaster recovery

### DR mode and single-IDC mode

If components required by the operation of a complete compute node cluster with disaster recovery relation are coordinated and deployed in the two IDCs, the cluster is called the cluster with DR mode enabled.

On the contrary, the compute node cluster without the DR mode enabled is the single-IDC mode cluster.

### IDC type and IDC status

The IDC type include the master center and the DR center. The IDC type is only used to identify and distinguish two IDCs, and does not change with the change of the service status of the IDC. In the process of using HotDB Server products, with the added compute node cluster group management as an entrance, you are able to distinguish between the master center and the DR center

The IDC status consists of the current active center and the current standby center, which is determined according to whether the master compute node in the IDC provides services (3323 service port by default). The IDC in which the current master compute node provides services is the current active center; the standby IDC that provides high-availability service switching at the IDC level with the current active center is the current standby center.

