/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtstack.chunjun.local.test;

import com.dtstack.chunjun.Main;
import com.dtstack.chunjun.util.GsonUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** @author jiangbo */
public class LocalTest2 {

    public static Logger LOG = LoggerFactory.getLogger(LocalTest2.class);

    public static void main(String[] args) throws Exception {
        LOG.warn("-----");
        Properties confProperties = new Properties();
        confProperties.setProperty(
                "execution.savepoint.path",
                "hdfs://localhost:9000/flink/savepoints/493/savepoint-7d5131-095a88e3a40c");

        //        confProperties.setProperty("flink.checkpoint.interval", "30000");
        //        confProperties.setProperty("state.backend","ROCKSDB");
        //        confProperties.setProperty("state.checkpoints.num-retained", "10");
        //        confProperties.setProperty("state.checkpoints.dir", "file:///ck");
        String userDir = System.getProperty("user.dir");

        String sqlFile = "kafka_kafka";
        String jobPath = userDir + "/chunjun-examples/sql/" + sqlFile + ".sql";
        String chunjunDistDir = userDir + "/chunjun-dist";
        String s = "";

        // 任务配置参数
        List<String> argsList = new ArrayList<>();
        argsList.add("-mode");
        argsList.add("yarn-per-job"); //
        // 替换脚本中的值
        // argsList.add("-p");
        // argsList.add("$aa=aaa, $bb=bbb");
        String content = readFile(jobPath);
        if (StringUtils.endsWith(jobPath, "json")) {
        } else if (StringUtils.endsWith(jobPath, "sql")) {
            argsList.add("-jobType");
            argsList.add("parse"); // parse
            argsList.add("-job");
            argsList.add(URLEncoder.encode(content, StandardCharsets.UTF_8.name()));
            argsList.add("-flinkConfDir");
            argsList.add("/Users/jary/data/tools/flink-1.12.7/conf");
            argsList.add("-jobName");
            argsList.add("flinkStreamSQLLocalTest");
            argsList.add("-chunjunDistDir");
            argsList.add(chunjunDistDir);
            argsList.add("-remoteChunJunDistDir");
            argsList.add(chunjunDistDir);
            // argsList.add("-pluginLoadMode");
            // argsList.add("LocalTest");
            argsList.add("-confProp");
            String configJsonString = GsonUtil.GSON.toJson(confProperties);
            argsList.add(configJsonString);
        }
        Main.main(argsList.toArray(new String[0]));
    }

    private static String readFile(String sqlPath) {
        try {
            byte[] array = Files.readAllBytes(Paths.get(sqlPath));
            return new String(array, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            LOG.error("Can not get the job info !!!", ioe);
            throw new RuntimeException(ioe);
        }
    }
}
