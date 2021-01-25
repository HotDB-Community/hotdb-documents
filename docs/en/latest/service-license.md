# Service License

## Platform license

The distributed transactional database platform (hereinafter referred to as the management platform) is a service platform with powerful functions such as configuration, monitoring, operation and maintenance, management, which is used together with the distributed transactional database (referred to as HotDB Server or compute node). From V2.5.6.1, a sound license management system, that is, the platform license, is introduced to license the number of available compute node cluster groups and the max number of available compute nodes in each cluster, so as to ensure the better external service of the management platform.

The platform license is divided into trial license and official license. The trial license has and can only manage or deploy one group of clusters. The cluster group contains at most three compute nodes. If you want to manage or deploy multiple groups of clusters, you need to apply for official license.

When there are both trial license and official license on the server, the license with more authorized nodes is preferred; if the number of authorized nodes is the same, the license with longer license validity is preferred.

### Trial license

If the one-click deployment is used to install management platform, a cluster of available 1 group of compute nodes is attached by default under the hotdb-management/keys directory, and each cluster can use the trial license of 3 compute nodes. The file name is management-license, as shown below:

```
[root@hotdb_171_221 keys]# cd /usr/local/hotdb/hotdb-management/keys
[root@hotdb_171_221 keys]# ll
total 4
-rw-r--r--. 1 root root 1313 Dec 25 17:57 management-license
```

#### Activate trial license

The trial license requires no additional activation steps. Put the license file under the directory `hotdb-management/keys` and start the management platform, the license will be activated automatically.

#### View trial license

If you want to view the details, you can execute the following command under the directory `hotdb-management/keys`:

```
[root@hotdb_171_221 keys]# java -jar ../utils/hotdb_management_license.jar -l management-license
License info of file management-license:
LICENSE VERIFIED.
======================== Management License ========================
The meanings of the above columns are as follows:
file: license file name
serial number: serial number of license file
type: license type, example: OFFICIAL, TRIAL
Num of available compute node cluster groups: number of available compute node cluster groups. 0 means that there are no restrictions
Num of compute nodes available in each cluster: number of compute nodes available in each cluster. 0 means that there are no restrictions
module limit: 0 by default
create time: create time
customer info: customer info
```

If it prompts: ERROR: LICENSE TAMPERED! it represents that the license has been tampered with and cannot be used.

Or after the management platform is started, log in to the management platform, and place the cursor over "certified" next to the version number, and view the license details, as shown below:

![](assets/service-license/image3.jpeg)

### Official license

To apply for the official license, the server's fingerprint is required. One unique fingerprint corresponds to one only official license. Therefore, the official license can only be activated and used on the corresponding server.

#### Generate fingerprint

Execute the following command under the directory `hotdb-management/utils` to generate the fingerprint:

```
[root@hotdb_171_221 utils]# cd /usr/local/hotdb/hotdb-management/utils
[root@hotdb_171_221 utils]# ll
total 144
-rw-r--r--. 1 hotdb hotdb 145748 Nov 25 10:43 hotdb_management_license.jar
[root@hotdb_171_221 utils]# java -jar hotdb_management_license.jar -f
trying to generate fingerprint on Linux amd64
The fingerprint file was successfully generated in ./management-fingerprint-2020-12-28-10-17-08
[root@hotdb_171_221 utils]# ll
total 148
-rw-r--r--. 1 hotdb hotdb 145748 Nov 25 10:43 hotdb_management_license.jar
-rw-r--r--. 1 root root 1025 Dec 28 10:17 management-fingerprint-2020-12-28-10-17-08
```

#### Apply for official license

Send HotDB (service@hotdb.com) a license application email with the generated file `management-fingerprint-2020-12-28-10-17-08` as an attachment and obtain the official license with the assistance offered by counterparts.

#### Update official license

Upload the official license file to the directory `hotdb-management/keys`, after receiving the official license file of the management platform.

Take the official license file `management-license-official-2020-12-23-15-40-55` as an example.

#### Activate official license

Start the management platform, the official license will be activated automatically. If the management platform is already in the running state, the license status will be detected once every 1 minutes by default. When a new license is detected, it will be activated automatically.

#### View official license

If you want to view the details, you can execute the following command under the directory `hotdb-management/keys`:

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

Or after the management platform is started, log in to the management platform, and place the cursor over "certified" next to the version number, and view the license details, as shown below:

![](assets/service-license/image4.jpeg)

### View the license in use

If there are multiple licenses under the directory `hotdb-management/keys`, you can execute the following command to view the currently used license:

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

- If it is a trial license, type: TRAIL can be seen when viewing the license info;

- If it is an official license, type: OFFICIAL can be seen when viewing the license info;

### Notes

You should pay attention to the following notes when using the platform license for the first time.

#### Fingerprint

The fingerprint is composed of the info of various hardware devices of the server, such as CPUID, serial number of hard disk, serial number of memory, MAC address of network card, UUID of root partition and UUID of system/virtual machine. After the official license is updated and used, it is necessary to avoid changing the hardware information of the server, which will lead to the invalidation of the license.

#### Official license file

When you get the official license file, you should keep the file properly to avoid man-made modification which will lead to the invalidation of the license.

#### Out of the scope of license

- If the number of compute node cluster groups or compute nodes currently managed by the management platform reaches the upper limit of the number of cluster groups or compute nodes authorized by the license, it is not allowed to add new clusters, and it will prompt "it is not allowed to add because the number of compute nodes exceeds the number of available compute nodes authorized by the platform."

- If the number of compute node cluster groups or compute nodes currently managed by the management platform reaches the upper limit of the number of cluster groups or compute nodes authorized by the license, it is not allowed to deploy new clusters, and it will prompt "it is not allowed to add clusters through the parameter configuration function because the number of compute node cluster groups exceeds the number of available compute node cluster groups authorized by the platform."

- If the number of cluster groups or compute nodes currently managed by the management platform exceeds the max number of cluster groups or compute nodes authorized by the license, it is not allowed to enter the operating page after refreshing the page after 1 minute or restarting the management platform, and the license needs to be updated to meet the number of cluster groups or compute nodes currently managed;

## Compute node license

The HotDB compute node needs to be activated using the official license before providing services. Starting from HotDB server v2.5.6, a new self-developed encrypted license is put into use for compute nodes.

The new self-developed encrypted license has only soft lock rather than the hard lock. The soft lock includes "official soft lock license" (hereinafter referred to as "official license") and "trial license". The official license can control the validity period and the number of nodes, and one unique fingerprint corresponds to one only official soft lock. The trial license can be activated on any machine without the fingerprint. It can control the number of nodes, and the validity period can be up to 90 days. Currently, the trial license is only allowed for once. After expiration, only the official license is allowed.

When there are both trial license and official license on the server, the license with more authorized nodes is preferred; if the number of authorized nodes is the same, the license with longer license validity is preferred.

### Trial license

If one-click deployment is used to install the compute node, a 64-node trial license with an authorized validity period of 90 days will be attached by default under the directory `hotdb-server/keys`. The file name is license-trail, as shown below:

```
[root@localhost keys]# cd /usr/local/hotdb/hotdb-server/keys
[root@localhost keys]# ll
total 4
-rw-r--r-- 1 root root 1369 Aug 6 16:21 license-trail
```

#### Activate trial license

The new self-developed encrypted license requires no additional activation steps. Put the license file under the directory `hotdb-server/keys` and start the compute node, the license will be activated automatically.

#### View trial license

If you want to view the details, you can execute the following command under the directory `hotdb-server/keys`:

```
[root@localhost keys]# java -jar ../utils/hotdb_license.jar -l license-trail
License info of file license-trail:
LICENSE VERIFIED.
======================== HotDB License ========================
file: license-trail
serial number: TRAIL-64-0-1609344000000-8283391465276724534
type: TRAIL
datanode limit: 64
module limit: 0
create time: 2020-04-23
effective time: 2020-09-15
expire time: 2020-12-14
time left: 89 days 23 hours 58 minutes 51 seconds.
customer info: hotdb.com
```

Or view the license info output from the hotdb.log file after the compute node is started:

```log
2020-09-15 14:03:17.949 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(262) - Thanks for chooising HotDB.
2020-09-15 14:03:17.957 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(269) - HotDB license expire time: 2020-12-14, datanode limit to: 64
2020-09-15 14:03:17.957 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(274) - HotDB trial expires in 89 days 23 hours 59 minutes 39 seconds.
```

You can also view the license info by executing `show @@usbkey\G` on port 3325.

```
root@127.0.0.1:(none) 5.7.22 01:57:34> show @@usbkey\G
*************************** 1. row ***************************
left_time: 7775979
usbkey_status: 1
usbkey_type: 1
node_limit: 64
last_check_time: 2020-09-15 14:03:17.957
usbkey_check_stuck: 0
last_exception_time: NULL
last_exception_info: NULL
exception_count: 0
comment: NULL
1 row in set (0.02 sec)
```

### Official license

When the trial license is expired, it needs to be updated to an official license to continue the compute node service. The official license can be divided into term official license and permanent official license according to the validity period of license, with the same update and activation steps.

To update to the official license, the server's fingerprint is required. One unique fingerprint corresponds to one only official soft lock. Therefore, the official license can only be activated and used on the corresponding server.

#### Generate fingerprint

Execute the following command under the directory `hotdb-server/utils` to generate the fingerprint:

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

#### Apply for official license

Send an application email with the generated document fingerprint-2020-09-15-14-41-53 as an attachment and obtain the official license with the assistance offered by counterparts.

#### Update official license

Take the official license license-expiration-2020-09-15-14-53-39 as an example. Upload the official license file to the directory `hotdb-server/keys`.

#### Activate official license

Start the compute node, the official license will be activated automatically. If the compute node is already in the running state, the license status will be detected once every 5 minutes by default. When a new license is detected, it will be activated automatically.

#### View official license

If you want to view the details, you can execute the following command under the directory `hotdb-server/keys`:

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
create time: 2020-09-15
effective time: 2020-09-15
expire time: 2020-09-22
time left: 6 days 9 hours 5 minutes 8 seconds.
customer info: license_for_test
```

Or view the license info output from the hotdb.log file after the compute node is started:

```log
2020-09-15 15:00:03.706 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(262) - Thanks for chooising HotDB.
2020-09-15 15:00:03.710 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(269) - HotDB license expire time: 2020-09-22, datanode limit to: 64
2020-09-15 15:00:03.710 [INFO] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(274) - HotDB trial expires in 6 days 8 hours 59 minutes 56 seconds.
```

You can also view the license info by executing `show @@usbkey\G` at port 3325.

```
root@127.0.0.1:(none) 5.7.22 03:00:56> show @@usbkey
*************************** 1. row ***************************
left_time: 550796
usbkey_status: 1
usbkey_type: 2
node_limit: 64
last_check_time: 2020-09-15 15:00:03.710
usbkey_check_stuck: 0
last_exception_time: NULL
last_exception_info: NULL
exception_count: 0
comment: NULL
1 row in set (0.02 sec)
```

### View the license in use

If there are multiple licenses under the directory `hotdb-server/keys`, you can execute the following command to view the currently used license:

```
[root@localhost keys]# java -jar ../utils/hotdb_license.jar -i
The License currently in use is:
======================== HotDB License ========================
file: license-trail
serial number: TRAIL-64-0-1609344000000-8283391465276724534
type: TRAIL
datanode limit: 64
module limit: 0
create time: 2020-04-23
effective time: 2020-09-15
expire time: 2020-12-14
time left: 89 days 22 hours 55 minutes 48 seconds.
```

- If it is a trial license, type: TRAIL can be seen when viewing the license info;

- If it is a term official license, type: EXPIRATION can be seen when viewing the license info;

- If it is a permanent official license, type: PERPETUAL can be seen when viewing the license info.

### Notes

Due to the huge difference between the new encrypted license and the old version, you should pay attention to the following notes when using the HotDB Server v2.5.6 compute node for the first time.

#### Fingerprint

The fingerprint is composed of the info of various hardware devices of the server, such as CPUID, serial number of hard disk, serial number of memory, MAC address of network card, UUID of root partition and UUID of system/virtual machine. After the official license is updated and used, it is necessary to avoid changing the hardware information of the server, which will lead to the invalidation of the license.

#### Official license file

When you get the official license file, you should keep the file properly to avoid man-made modification which will lead to the invalidation of the license.

#### License expiration

After the compute node is started, the license status will be detected once every 5 minutes by default. When the license is expired, the compute node service will not stop immediately, however the number of nodes will be limited to 0, and `reload @@config` will not be allowed for execution, and the license expiration warning will be output once every 5 minutes by the hotdb.log.

```log
2020-12-15 00:02:52.113 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(163) - no matched license detected. Datanode limit to 0.
2020-12-15 00:02:52.114 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.a(5540) - Number of Datanodes Exceeded Maximum Size Limit. expect:4 limit:0
```

Under this circumstance, failure will be reported when you execute `reload @@config` on port 3325:

```
root@127.0.0.1:(none) 5.7.22 12:06:55> reload @@config;
ERROR 10190 (HY000): Reload config failure, Number of Datanodes Exceeded Maximum Size Limit. For more details, please check the log
```

With an expired license, the service will be failed to start and a message of no available license will be prompted, as shown below:

```log
2020-12-15 00:10:47.388 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.util.V(158) - no available matched license detected. we will shutdown now.
2020-12-15 00:10:47.389 [INFO] [EXIT[ FLOW]] [ShutdownHook] cn.hotpu.hotdb.c(770) - begin to exit...
```

Therefore, when the license is about to expire, you should apply for a new license and update it as soon as possible.

#### Out of the scope of license

When the compute node restarts, if the number of authorized nodes and LogicDBs is less than the number of data nodes and LogicDBs currently configured, the compute node will be started successfully, though it cannot be used normally and Wrong HotDB Config will be prompted until the license is updated or configuration is modified and recognized by the compute node (periodical task recognition with 5-minute interval or modification of the number of nodes / LogicDBs currently configured before dynamic loading).

Prompt when the number of authorized LogicDBs exceeds the existing configuration limit

```
root> show databases;
ERROR 10161 (HY000): Wrong HotDB Config, due to: Number of Logic Database Exceeded Maximum Size Limit. expect:3 limit:2

Log reminder:
2020-12-22 14:13:06.231 [ERROR] [AUTHORITY] [checker-1] cn.hotpu.hotdb.a(5844) - Number of Logic Database Exceeded Maximum Size Limit. expect:3 limit:2
```

When using the compute node, the number of authorized nodes and LogicDBs is less than the number of data nodes and LogicDBs currently configured, the dynamic loading is not allowed. The prompt is as follows:

```
root> reload @@config;
ERROR 10190 (HY000): Reload config failure, Number of Logic Database Exceeded Maximum Size Limit. For more details, please check the log
```

#### Server time

The new self-developed encrypted license is highly dependent on the current server time, so it is necessary to keep the server time and network time synchronized. If it is suddenly prompted that the license is not available, you should first check whether the server time has been tampered with.

