#!/bin/bash

echo "Cechinkg casssandra nodes status..."
nodetool status
sleep 7
echo "Starting Processor..."
gnome-terminal -- java -jar ./spark-processor/target/spark-processor-0.0.1-SNAPSHOT.jar & echo spark-processor:running
sleep 10

echo "Starting Producer"
gnome-terminal -- java -jar ./kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar  & echo kafka-producer:running

echo "Press enter to open an extra Kafka-console-consumer to see message events flow"
read continue
gnome-terminal -- sh ./kafka_2.13-3.0.0/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic temperature & echo Kafka-Console-Consumer:opened

sleep 10

echo "Ready to enter query using cqlsh..."
read continue
cqlsh mattia
