#!/bin/bash

echo "Checking casssandra nodes status..."
nodetool status
sleep 7
echo "Starting Processor..."
gnome-terminal -- $SPARK_HOME/bin/spark-submit --class bigdataman.IoT.SparkIoTProcessor.SparkIoTProcessorV2 --master yarn  --deploy-mode client --queue default /home/bigdata/git/supreme-pancake/spark-processor/target/spark-processor-0.0.1-SNAPSHOT.jar  & echo spark-processor:running

sleep 12

echo "Starting Producer"
gnome-terminal -- java -jar ./kafka-producer/target/kafka-producer-0.0.1-SNAPSHOT.jar  & echo kafka-producer:running

sleep 5

echo "Press enter to open an extra Kafka-console-consumer to see message events flow"
read continue
gnome-terminal -- sh ./kafka_2.12-2.8.1/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic temperature & echo Kafka-Console-Consumer:opened

sleep 10

echo "Ready to enter query using cqlsh..."
read continue
cqlsh mattia

sleep 5


echo "Press enter to start new realtime Spark jobs"
read continue
gnome-terminal -- $SPARK_HOME/bin/spark-submit --class bigdataman.IoT.sparkMapReduce.SparkProgram --master yarn  --deploy-mode client --queue default /home/bigdata/git/supreme-pancake/spark-mapreduce/target/spark-mapreduce-0.0.1-SNAPSHOT.jar

sleep 10

echo "Ready to enter new query using cqlsh..."
read continue
cqlsh mattia
