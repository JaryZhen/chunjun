package com.dtstack.chunjun.client;

import com.dtstack.chunjun.util.GsonUtil;

import junit.framework.TestCase;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
        String flinkHome = "/Users/jary/data/tools/flink-1.12.7/";
        String hadoopHome = "/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/hadoop/dev/";

        String jobPath = userDir + "/chunjun-examples/sql/kafka_kafka.sql";
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
            argsList.add(flinkHome + "lib");

            argsList.add("-hadoopConfDir");
            argsList.add(hadoopHome);


            Properties confProperties = new Properties();
            confProperties.setProperty("execution.savepoint.path", "hdfs://localhost:9000/flink/savepoints/493/savepoint-7d5131-095a88e3a40c");
            confProperties.setProperty("yarn.application.queue","root.flinkdemo");

            //        confProperties.setProperty("flink.checkpoint.interval", "30000");
            //        confProperties.setProperty("state.backend","ROCKSDB");
            //        confProperties.setProperty("state.checkpoints.num-retained", "10");
            //        confProperties.setProperty("state.checkpoints.dir", "hdfs://localhost:9000/ck");

            Map<String, String> config = new HashMap<>();
            // config.put("state.backend", "ROCKSDB");
            //config.put("state.checkpoints.dir", "hdfs://localhost:9000/ck");
            config.put("execution.savepoint.path", "hdfs://localhost:9000/flink/savepoints/493/savepoint-7d5131-095a88e3a40c");
            String configJsonString = GsonUtil.GSON.toJson(confProperties);
            System.out.println(configJsonString);

            argsList.add("-confProp");
            argsList.add(configJsonString);

        }

        System.out.println("asdfaf");
        try {
            Launcher.main(argsList.toArray(new String[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSavePoint() throws IOException, InterruptedException {

        Properties confProperties = new Properties();
        confProperties.setProperty("execution.savepoint.path", "hdfs://localhost:9000/flink/savepoints/493/savepoint-7d5131-095a88e3a40c");
        confProperties.setProperty("yarn.application.queue","root.flinkdemo");
        //confProperties.setProperty("flink.checkpoint.interval", "30000");
        // confProperties.setProperty("state.backend", "ROCKSDB");
        //confProperties.setProperty("state.checkpoints.num-retained", "10");
        //confProperties.setProperty("state.checkpoints.dir", "file:///ck");
        String configJsonString = GsonUtil.GSON.toJson(confProperties);

        System.out.println(configJsonString);
        CommandLine commandLine = new CommandLine("java");
        commandLine.addArgument("-cp");
        commandLine.addArgument("/Users/jary/IdeaProjects/chunjun/chunjun-dist/chunjun-clients.jar");
        commandLine.addArgument("com.dtstack.chunjun.client.Launcher");
        commandLine.addArgument("-mode");
        commandLine.addArgument("yarn-per-job");
        commandLine.addArgument("-jobType");
        commandLine.addArgument("sql");
        commandLine.addArgument("-job");
        commandLine.addArgument("/Users/jary/IdeaProjects/chunjun/chunjun-examples/sql/kafka_kafka.sql");
        commandLine.addArgument("-chunjunDistDir");
        commandLine.addArgument("/Users/jary/IdeaProjects/chunjun/chunjun-dist");
        commandLine.addArgument("-flinkConfDir ");
        commandLine.addArgument("/Users/jary/data/tools/flink-1.12.7/conf");
        commandLine.addArgument("-flinkLibDir ");
        commandLine.addArgument("/Users/jary/data/tools/flink-1.12.7/lib");
        commandLine.addArgument("-hadoopConfDir");
        commandLine.addArgument("/Users/jary/IdeaProjects/lk-luckyposeidonx/env-poseidonx/hadoop/dev");
        commandLine.addArgument("-confProp");
        commandLine.addArgument(configJsonString);
        //commandLine.addArgument("'{\"execution.savepoint.path\": \"hdfs://localhost:9000/flink/savepoints/493/savepoint-7d5131-095a88e3a40c\"}'");
        Map<String, String> envsMap = null; //new HashMap<>();
        StringBuilder submitLogSB = new StringBuilder();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        execute(commandLine, null, 160 * 1000, outputStream);
        System.out.println(outputStream.toString());
    }

    public static void execute(
            CommandLine commandLine, Map<String, String> envsMap, Integer timeout,
            ByteArrayOutputStream outputStream) throws IOException, InterruptedException {
        DefaultExecutor executor = new DefaultExecutor();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream, outputStream);
        executor.setStreamHandler(pumpStreamHandler);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(timeout);
        executor.setWatchdog(watchdog);
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        String[] arguments = commandLine.getArguments();
        System.out.println("flink shell:\n" + commandLine.getExecutable() + " " + StringUtils.join(commandLine.getArguments(), " "));
        if (arguments != null && arguments.length > 0) {
            System.out.println("arguments.toString():" + Arrays.toString(arguments));
        }
        if (envsMap != null) {
            envsMap.put("HADOOP_USER_NAME", "hadoop");
            executor.execute(commandLine, envsMap, resultHandler);
        } else {
            executor.execute(commandLine, resultHandler);
        }
        resultHandler.waitFor(timeout);
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
