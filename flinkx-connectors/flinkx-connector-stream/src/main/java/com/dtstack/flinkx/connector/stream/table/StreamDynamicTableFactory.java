/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dtstack.flinkx.connector.stream.table;

import com.dtstack.flinkx.connector.stream.conf.StreamConf;
import com.dtstack.flinkx.connector.stream.outputFormat.StreamOutputFormatBuilder;
import com.dtstack.flinkx.connector.stream.sink.StreamSinkFunction;

import org.apache.flink.api.common.functions.util.PrintSinkOutputWriter;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.sink.DynamicTableSink;
import org.apache.flink.table.connector.sink.SinkFunctionProvider;
import org.apache.flink.table.factories.DynamicTableSinkFactory;
import org.apache.flink.table.factories.FactoryUtil;
import org.apache.flink.table.types.DataType;

import java.util.HashSet;
import java.util.Set;

import static org.apache.flink.configuration.ConfigOptions.key;

/**
 * @author chuixue
 * @create 2021-04-08 11:56
 * @description
 **/
public class StreamDynamicTableFactory implements DynamicTableSinkFactory {
    public static final String IDENTIFIER = "stream";

    public static final ConfigOption<String> PRINT_IDENTIFIER =
            key("print-identifier")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "Message that identify print and is prefixed to the output of the value.");

    public static final ConfigOption<Boolean> STANDARD_ERROR =
            key("standard-error")
                    .booleanType()
                    .defaultValue(false)
                    .withDescription(
                            "True, if the format should print to standard error instead of standard out.");

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return new HashSet<>();
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        return new HashSet<>();
    }

    @Override
    public DynamicTableSink createDynamicTableSink(Context context) {
        FactoryUtil.TableFactoryHelper helper = FactoryUtil.createTableFactoryHelper(this, context);
        helper.validate();
        ReadableConfig options = helper.getOptions();
        return new StreamDynamicTableSink(
                context.getCatalogTable().getSchema().toPhysicalRowDataType(),
                options.get(PRINT_IDENTIFIER),
                options.get(STANDARD_ERROR)
        );
    }

    private static class StreamDynamicTableSink implements DynamicTableSink {
        private final DataType type;
        private final String printIdentifier;
        private final boolean stdErr;

        private StreamDynamicTableSink(DataType type, String printIdentifier, boolean stdErr) {
            this.type = type;
            this.printIdentifier = printIdentifier;
            this.stdErr = stdErr;
        }

        @Override
        public ChangelogMode getChangelogMode(ChangelogMode requestedMode) {
            return requestedMode;
        }

        @Override
        public SinkFunctionProvider getSinkRuntimeProvider(Context context) {
            DataStructureConverter converter = context.createDataStructureConverter(type);
            StreamOutputFormatBuilder builder = new StreamOutputFormatBuilder();
            StreamConf streamConf = new StreamConf();
            streamConf.setPrint(true);
            builder.setStreamConf(streamConf)
                    .setConverter(converter)
                    .setWriter(new PrintSinkOutputWriter<>(printIdentifier, stdErr));

            StreamSinkFunction sinkFunction = new StreamSinkFunction(builder.finish());
            return SinkFunctionProvider.of(sinkFunction, 1);
        }

        @Override
        public DynamicTableSink copy() {
            return new StreamDynamicTableSink(type, printIdentifier, stdErr);
        }

        @Override
        public String asSummaryString() {
            return "Stream to ";
        }
    }
}
