#!/usr/bin/env bash

# FLINK_HOME=/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/flink-home/flink-1.13.2/
FLINK_HOME=/Users/jary/data/tools/flink-1.12.7/
HADOOP_HOME=/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/hadoop/dev/
#export HADOOP_CLASSPATH=`hadoop classpath`
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/

nohup java -cp /Users/jary/IdeaProjects/chunjun/lib/chunjun-clients.jar com.dtstack.chunjun.client.Launcher -mode local \
        -jobType sql \
        -job /Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/datagen.sql \
        -chunjunDistDir /Users/jary/IdeaProjects/chunjun/chunjun-dist

:<<EOF

nohup java -cp /Users/jary/IdeaProjects/chunjun/lib/chunjun-clients.jar com.dtstack.chunjun.client.Launcher -mode local \
        -jobType sql \
        -job /Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/datagen.sql \
        -chunjunDistDir /Users/jary/IdeaProjects/chunjun/chunjun-dist


java -cp /Users/jary/IdeaProjects/chunjun/lib/chunjun-clients.jar com.dtstack.chunjun.client.Launcher -mode yarn-per-job -jobType sql -job /Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/datagen.sql -chunjunDistDir /Users/jary/IdeaProjects/chunjun/chunjun-dist -flinkConfDir /Users/jary/data/tools/flink-1.12.7//conf -flinkLibDir /Users/jary/data/tools/flink-1.12.7//lib -hadoopConfDir /Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/hadoop/dev/



start-chunjun \
	-mode yarn-per-job \
	-jobType sql \
	-job /Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/datagen.sql \
	-chunjunDistDir /Users/jary/IdeaProjects/chunjun/chunjun-dist  \
	-flinkConfDir $FLINK_HOME/conf \
	-flinkLibDir $FLINK_HOME/lib \
	-hadoopConfDir $HADOOP_HOME \

	-confProp "{\"flink.checkpoint.interval\":60000,\"yarn.application.queue\":\"default\"}" \

start-chunjun \
	-mode local \
	-jobType sql \
	-job /Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/datagen.sql \
	-chunjunDistDir /Users/jary/IdeaProjects/chunjun/chunjun-dist

EOF



