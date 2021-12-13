CREATE KEYSPACE IF NOT EXISTS iot WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 2 };

USE iot;

DROP TABLE IF EXISTS temperature;


CREATE TABLE temperature ( region varchar, country varchar ,city varchar, timestamp timestamp,year varchar, avg_temperature float, PRIMARY KEY ((region, year), timestamp));

CREATE TABLE IF NOT EXISTS rawdata ( region varchar, country varchar ,city varchar, timestamp timestamp,year varchar, avg_temperature float, PRIMARY KEY ((region, year), timestamp));

CREATE TABLE IF NOT EXISTS avgTempByYear (year varchar PRIMARY KEY, avg_temperature float) ;
CREATE TABLE IF NOT EXISTS avgTempByCity (city varchar primary key, avg_temperature float);
CREATE TABLE IF NOT EXISTS avgTempByRegionYear(region varchar, year varchar, avg_temperature float, PRIMARY KEY (region, year));

TRUNCATE TABLE temperature;

