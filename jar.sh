#!/usr/bin/env bash


sqlVersionName="flink-sql-1.12-chunjun"

rm -rf ${sqlVersionName}
cp -R chunjun-dist ${sqlVersionName}

cd ${sqlVersionName}

rm -rf ddl
#rm -rf dirty-data-collector/
rm -rf docker-build
rm -rf metrics
rm -rf restore-plugins

rm -rf connector/binlog
rm -rf connector/cassandra
rm -rf connector/clickhouse
rm -rf connector/doris
rm -rf connector/elasticsearch7
rm -rf connector/emqx
rm -rf connector/ftp
rm -rf connector/gbase
rm -rf connector/greenplum
rm -rf connector/hbase14
rm -rf connector/http
rm -rf connector/influxdb
rm -rf connector/kingbase
rm -rf connector/mongodb
rm -rf connector/oceanbase
rm -rf connector/oracle
rm -rf connector/postgresql
rm -rf connector/redis
rm -rf connector/rocketmq
rm -rf connector/saphana
rm -rf connector/socket
rm -rf connector/solr
rm -rf connector/sqlserver
rm -rf connector/sqlservercdc
rm -rf connector/starrocks
rm -rf connector/oraclelogminer
rm -rf connector/pgwal

cd ..

cd lib
rm -rf *
cd ..

zip -r ${sqlVersionName}.zip ${sqlVersionName}/

ls -l ${sqlVersionName}/connector

mv ${sqlVersionName}.zip lib/

cd lib

mv ${sqlVersionName}.zip ${sqlVersionName}.zip.jar

cd ..

ls -l lib
