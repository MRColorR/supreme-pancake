#!/bin/bash

echo "Welcome to IoT Temperature Project"
echo "Created by Mattia Rengo, Ciro Rapone, Alessandro Cinti"

echo "Press Enter to continue..."
read continue

echo "Starting Hadoop cluster"
gnome-terminal -- sh $HADOOP_DIR/start-hadoop.sh & HadoopCluster:Starting

sleep(5)

echo "Starting zookeper"
gnome-terminal -- sh kafka_2.13-3.0.0/bin/zookeeper-server-start.sh kafka_2.13-3.0.0/config/zookeeper.properties & echo zookeper-server:Starting

sleep(5)

echo "Starting Kafka"
gnome-terminal -- sh kafka_2.13-3.0.0/bin/kafka-server-start.sh kafka_2.13-3.0.0/config/server.properties & echo kafka-server:Starting

sleep(5)

echo "Setting-up Kafka topic"

gnome-terminal -- sh kafka_2.13-3.0.0/bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic temperature --create --partitions 1 --replication-factor 1 & echo Done

sleep(5)

echo "Starting Cassandra..."
read continue
gnome-terminal -- service cassandra start & echo Cassandra:Starting

sleep(5)

echo "Setting-up Cassandra keyspace and tables..."
gnome-terminal -- cqlsh -f ./cassandra-db/dbscript.cql & echo Done
echo "Project starting sequence complete"
