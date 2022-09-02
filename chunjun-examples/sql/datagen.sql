CREATE TABLE datagen (
                         f_sequence INT,
                         f_random INT,
                         f_random_str STRING,
                         ts AS localtimestamp,
                         f_random2_str2 STRING,
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
      'fields.f_random_str.length'='10',
      'fields.f_random2_str2.length'='3'

                           );

CREATE TABLE print_table (
                             ts TIMESTAMP,
                             f_sequence INT,
                             f_random INT,
                             f_random_str STRING,
                             f_random2_str2 STRING

) WITH (
      'connector' = 'print'
      );

INSERT INTO print_table
select ts,f_sequence,f_random,f_random_str,f_random2_str2
from datagen;
