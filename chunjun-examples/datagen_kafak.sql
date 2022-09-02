CREATE TABLE datagen (
                         f_sequence INT,
                         f_random INT,
                         f_random_str STRING,
                         ts AS localtimestamp,
                         WATERMARK FOR ts AS ts
) WITH (
      'connector' = 'datagen',
      -- optional options --
      'rows-per-second'='5',
      'fields.f_sequence.kind'='sequence',
      'fields.f_sequence.start'='1',
      'fields.f_sequence.end'='500',
      'fields.f_random.min'='1',
      'fields.f_random.max'='500',
      'fields.f_random_str.length'='10'
      );

CREATE TABLE result_total_pvuv_min
(
    local_ping       INT,
    age              STRING,
    line             STRING,
    tm               timestamp,

    `partition`      BIGINT,
    `topic`          STRING,
    `leader-epoch`   int,
    `offset`         BIGINT,
    ts               TIMESTAMP(3),
    `timestamp-type` STRING,
    partition_id     BIGINT
) WITH (
      'connector' = 'kafka-x',
      -- 'connector' = 'kafka-x'
      'topic' = 'poseidon-sql-sink',
      'properties.bootstrap.servers' = 'localhost:9088',
      'format' = 'json',
      'sink.parallelism' = '2'
      -- ,'json.timestamp-format.standard' = 'SQL'
      );

INSERT INTO print_table select f_sequence,f_random,f_random_str from datagen;
