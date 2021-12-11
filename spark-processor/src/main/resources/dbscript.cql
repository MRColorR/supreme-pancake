DROP KEYSPACE IF EXISTS iot;

CREATE KEYSPACE iot WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 2 };

USE iot;

CREATE TABLE temperature ( region varchar, country varchar ,city varchar, timestamp timestamp, avg_temperature float, PRIMARY KEY ((region, timestamp), country));

DROP TABLE IF EXISTS avgTempByYear;
DROP TABLE IF EXISTS avgTempByRegionYear;

CREATE TABLE avgTempByYear (year varchar PRIMARY KEY, avg_temperature float) ;
CREATE TABLE avgTempByCity (city varchar primary key, avg_temperature float);
CREATE TABLE avgTempByRegionYear(region varchar, year varchar, avg_temperature float, PRIMARY KEY (region, year));

TRUNCATE TABLE temperature;

