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

CREATE TABLE print_table (
                             f_sequence INT,
                             f_random INT,
                             f_random_str STRING
) WITH (
      'connector' = 'print'
      );

INSERT INTO print_table select f_sequence,f_random,f_random_str from datagen;
