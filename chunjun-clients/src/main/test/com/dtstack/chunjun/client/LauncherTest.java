package com.dtstack.chunjun.client;

import com.dtstack.chunjun.util.GsonUtil;

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Jary
 * @Date: 2022/8/2 10:48 AM
 */
public class LauncherTest {

    @Test
    public void testYarn() throws UnsupportedEncodingException {
        String userDir = "/Users/jary/IdeaProjects/chunjun";
        //String flinkHome = "/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/flink-home/flink-1.13.2/";
        String flinkHome="/Users/jary/data/tools/flink-1.12.7/";
        String hadoopHome = "/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/hadoop/dev/";

        String jobPath = userDir + "/chunjun-examples/sql/datagen.sql";
        String chunjunDistDir = userDir + "/chunjun-dist";
        String s = "";

        // 任务配置参数
        List<String> argsList = new ArrayList<>();
        argsList.add("-mode");
        argsList.add("yarn-per-job");
        // 替换脚本中的值
        // argsList.add("-p");
        // argsList.add("$aa=aaa, $bb=bbb");
        String content = readFile(jobPath);
        if (StringUtils.endsWith(jobPath, "json")) {
        } else if (StringUtils.endsWith(jobPath, "sql")) {
            argsList.add("-jobType");
            argsList.add("sql");
            argsList.add("-job");
            argsList.add(jobPath);
            argsList.add("-jobName");
            argsList.add("flinkStreamSQLLocalTest");
            argsList.add("-chunjunDistDir");
            argsList.add(chunjunDistDir);

            argsList.add("-flinkConfDir");
            argsList.add(flinkHome + "conf");
            argsList.add("-flinkLibDir");
            argsList.add(flinkHome+ "lib");

            argsList.add("-hadoopConfDir");
            argsList.add(hadoopHome);


            Properties confProperties = new Properties();
            Map<String, String> config = new HashMap<>();
            config.put("state.backend","hashmap");
            //        confProperties.setProperty("flink.checkpoint.interval", "30000");
            //        confProperties.setProperty("state.backend","ROCKSDB");
            //        confProperties.setProperty("state.checkpoints.num-retained", "10");
            //        confProperties.setProperty("state.checkpoints.dir", "file:///ck");
            //argsList.add("-confProp");
             String configJsonString = GsonUtil.GSON.toJson(config);
            //argsList.add(configJsonString);

        }

        System.out.println("asdfaf");
        try {
            Launcher.main(argsList.toArray(new String[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFile(String sqlPath) {
        try {
            byte[] array = Files.readAllBytes(Paths.get(sqlPath));
            return new String(array, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            System.out.println("Can not get the job info !!!" + ioe);
            throw new RuntimeException(ioe);
        }
    }

}
