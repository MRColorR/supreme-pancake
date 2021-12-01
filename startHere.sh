#!/bin/bash


echo "Welcome to IoT Temperature Project"
echo "Created by Mattia Rengo, Ciro Rapone, Alessandro Cinti"

echo "Press Enter to continue..."
read continue

echo "Starting zookeper"
gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/zookeeper-server-start.sh ../../kafka_2.13-3.0.0/config/zookeeper.properties & echo zookeper-server:started


echo "Press Enter to start Kafka..."
read continue

echo "Starting Kafka"
gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/kafka-server-start.sh ../../kafka_2.13-3.0.0/config/server.properties & echo kafka-server:started


echo "Press enter to set-up Kafka topic"
read continue
gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic test1 --create --partitions 1 --replication-factor 1 & echo Done


echo "Press enter to start Cassandra..."
read continue
gnome-terminal -- service cassandra start & echo Cassandra:started


echo "Press enter to inizialize Cassandra keyspace and tables..."
read continue
gnome-terminal -- cqlsh -f ./spark-processor/src/main/resources/dbscript.cql & echo Done

echo "Press enter to start Producer and processor"
read continue
echo "Press entert to start Kafka Producer"
gnome-terminal -- java -jar ./kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar  & echo kafka-producer:running
sleep 0.5
echo "Press entert to start Spark processor"
read continue
gnome-terminal -- java -jar ./spark-processor/target/spark-processor-0.0.1-SNAPSHOT.jar & echo spark-processor:running


echo "Press enter to open an extra Kafka-console-consumer to see message events flow"
read continue
gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test1 & echo Kafka-Console-Consumer:opened


echo "Let's ask some query to cassandra. Press enter to start"
read continue
gnome-terminal -- cqlsh -f ./cassandra-db/query.cql & echo query executed


echo "Press enter again to see new data rows adedd from new message events. "
read continue
gnome-terminal -- cqlsh -f ./cassandra-db/query.cql & echo query executed


echo "Press enter to stop all the components and exit script"
read continue
echo "Initializing components shutdown sequence"
gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/kafka-server-stop.sh & echo kafka-server:stopped

gnome-terminal -- sh ../../kafka_2.13-3.0.0/bin/zookeper-server-stop.sh & echo zookeper-server:stopped

gnome-terminal -- service cassandra stop & echo Cassandra:Stopped
echo "Project execution terminated"
sleep 1
echo "Thanks for your time and effort. Press enter to close all and exit"
read continue
kill $(pgrep gnome-terminal)




