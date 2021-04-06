/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dtstack.flinkx.metadata;

/**
 * @author : tiezhu@dtstack.com
 * @date : 2020/3/8
 * @description : 元数据同步涉及的相关参数
 * todo 需要定义的常量较多，找到好的命名方法
 */
public class MetaDataCons {


    public static final String KEY_USER = "user";

    /**
     * reader 需要的参数
     */
    public static final String KEY_CONN_USERNAME = "username";
    public static final String KEY_CONN_PASSWORD = "password";
    public static final String KEY_JDBC_URL = "jdbcUrl";

    public static final String KEY_DB_LIST = "dbList";
    public static final String KEY_DB_NAME = "dbName";
    public static final String KEY_TABLE_LIST = "tableList";

    /**
     * 表技术属性
     */
    public static final String KEY_TABLE_COMMENT = "comment";
    public static final String KEY_TABLE_TOTAL_SIZE = "totalSize";
    public static final String KEY_TABLE_CREATE_TIME = "createTime";
    public static final String KEY_TABLE_ROWS = "rows";
    public static final String KEY_QUERY_SUCCESS = "querySuccess";
    public static final String KEY_ERROR_MSG = "errorMsg";
    public static final String KEY_SCHEMA = "schema";
    public static final String KEY_OPERA_TYPE = "operaType";
    public static final String KEY_TABLE = "table";
    public static final String KEY_TABLE_PROPERTIES = "tableProperties";

    /**
     * 结果集中获取表信息
     */
    public static final String RESULT_SET_TABLE_NAME = "TABLE_NAME";

    /**
     * 列的技术属性
     */
    public static final String KEY_COLUMN_NAME = "column_name";
    public static final String KEY_COLUMN_INDEX = "index";
    public static final String KEY_COLUMN_COMMENT = "column_comment";
    public static final String KEY_COLUMN_TYPE = "data_type";
    public static final String KEY_COLUMN_DEFAULT = "column_default";
    public static final String KEY_COLUMN_NULL = "is_nullable";
    public static final String KEY_COLUMN_PRIMARY = "column_key";
    public static final String KEY_COLUMN_SCALE = "data_length";
    public static final String KEY_COLUMN_DATA_TYPE = "data_type";
    public static final String KEY_TRUE = "Y";
    public static final String KEY_FALSE = "N";

    /**
     * 结果集中获取列信息
     */
    public static final String RESULT_SET_COLUMN_NAME = "COLUMN_NAME";
    public static final String RESULT_SET_TYPE_NAME = "TYPE_NAME";
    public static final String RESULT_SET_COLUMN_SIZE = "COLUMN_SIZE";
    public static final String RESULT_SET_DECIMAL_DIGITS = "DECIMAL_DIGITS";
    public static final String RESULT_SET_ORDINAL_POSITION = "ORDINAL_POSITION";
    public static final String RESULT_SET_IS_NULLABLE = "IS_NULLABLE";
    public static final String RESULT_SET_REMARKS = "REMARKS";
    public static final String RESULT_SET_COLUMN_DEF = "COLUMN_DEF";



    /**
     *
     */
    public static final String KEY_COLUMN = "column";
    public static final String KEY_STORED_TYPE = "storedType";
    public static final String KEY_PARTITION_COLUMNS = "partitionColumn";
    public static final String KEY_PARTITIONS = "partitions";

    /**
     * 索引属性
     */
    public static final String KEY_INDEX_NAME = "name";
    public static final String KEY_INDEX_COMMENT = "comment";


    public static final String KEY_COL_NAME = "col_name";

    public static final String DEFAULT_OPERA_TYPE = "createTable";

    public static final String SQL_SHOW_TABLES = "SHOW TABLES";
    public static final String SQL_SWITCH_DATABASE = "USE %s";

    public static final int MAX_TABLE_SIZE = 20;
}