# Distributed Transactional Database HotDB Server

## Introduce

HotDB Server is a relational distributed transactional database product which realizes horizontal expansion of data capacity and performance. It is compatible with mainstream database protocols and SQL92/SQL99/SQL2003 standard syntax, supports automatic horizontal and vertical splitting, and can provide centralized database operation experience for applications in a distributed data storage environment. It provides strong support for business scenarios with massive users, massive data, high availability, high concurrency and high throughput, and has the characteristics of strong distributed transparency, easy expansion, easy operation and maintenance, and no learning cost. HotDB Server allows R&D engineers to focus on the implementation of application coding, with no need to pay attention to the details of data storage location and operation location, so that database engineers can manage  database clusters with massive data and massive throughput more easily. HotDB Server also provides a complete set of solutions such as data security, data disaster tolerance, data recovery, cluster monitoring, intelligent topology, intelligent dashboard, non-stop capacity expansion, which are suitable for massive data business transaction scenarios of TB or PB levels.

## Product Advantage

**Distributed**

HotDB Server supports distributed cluster data storage and splitting nodes and tables, and also provides a variety of sharding algorithms and table types, to meet the user’s needs for business scenarios.

**Strong Consistency**

HotDB Server supports strong data consistency, and has algorithm mechanism of strong consistency guarantee and consistency detection for the global table, global sequence, distributed transaction, data consistency of replicas, table structure consistency, environment configuration, etc.

**Intelligent Operation and Maintenance**

HotDB Server provides visualized parameter configuration and parameter rationality verification, multi-thread automatic backup, data status awareness and dashboard of business display, easy one-click deployment of a complete set of cluster services, intelligent data correctness detection and exception email alert.

**High Availability**

The high availability of compute node, ConfigDB and underlying database can be realized. Data service will not be unavailable during the master node downtime. The reliability of data service can reach 99.999%.

**Strong Transparency**

HotDB Server is fully transparent to the application, and operations including underlying online expansion, backup, OnlineDDL are unaware. HotDB Server supports JDBC protocol, MySQL native communication protocol, MySQL database protocol and SQL92 standard syntax. It covers more than 99.9% of common SQL in application development, and supports multiple single-node/cross-node operations.

**High Performance**

The single node throughput of compute node can reach more than 100000 TPS and 300000 QPS, and the concurrency number can reach 4096 or above.

## Core Functions

**Data sharding**

Separate nodes by LogicDBs to isolate data of different business attributes.

Split large tables vertically or horizontally, allowing operations to focus on small amounts of data

Built-in rich table types and splitting algorithm for different business scenarios

**Distributed Transactions**

Support explicit and implicit distributed transactions

Support distributed transactions of weak consistency and strong consistency

Fully transparent MySQL command operations to application and client

**Cross-Node Operations**

Cross-Node JOIN

Cross-Node UNION

Cross-Node Aggregate Function

Cross-Node Grouping and Sorting , etc.

**Auto Expansion**

One-click migration and online smooth expansion through the management page

Adds read-only nodes online and configure the proportion of read/write splitting

Supports fast migration from single node to distributed cluster.

**Disaster Backup and Recovery**

Ensures the consistency of global time point and data state of distributed database

Backup does not block business services

Supports node-level data backup in distributed environment

Supports functions including encrypted file backup, file MD5 value calculation, backup to remote

**Read/Write Splitting**

Supports read/write splitting and online proportion setting

The read node with too long master-slave delay will be automatically removed from the read cluster, and will be added to the read cluster automatically after it returns to normal

Read/write splitting is transparent to the upper application, with no restrictions on the distribution or special syntax processing of any SQL statements

**Overload Protection**

Supports restrictions on total front-end connections and user connections

Controls SQL concurrencies sent by HotDB to the data source, protects load balance between data sources, and prevents a certain data source from downtime due to excessive pressure

**Data Verification**

The master/slave data consistency detection which supports periodical plan and exception email alert

Global table data consistency detection

Data route correctness verification

Table structure&index detection

**Online Table Structure Modification**

Direct online table structure modification on the management page

Online business reading and writing will not be blocked during the execution of online table structure modification

Online view table structure modification records being implemented and completed

## Applicable scenes

* High Availability
* High Performance
* Massive Throughput
* Massive Connections
* Massive Concurrency
* Massive Data
* Massive Users

