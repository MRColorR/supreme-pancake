DROP KEYSPACE IF EXISTS iot;

CREATE KEYSPACE iot WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE iot;
DROP TABLE IF EXISTS temperature;
CREATE TABLE temperature ( id varchar PRIMARY KEY, region varchar, country varchar ,city varchar, date varchar, avg_temperature float);

TRUNCATE TABLE temperature;

