#!/bin/bash

echo "Press enter to stop all the components and exit script"
read continue
echo "Initializing components shutdown sequence"
gnome-terminal -- sh kafka_2.13-3.0.0/bin/kafka-server-stop.sh & echo kafka-server:Stopped

sleep 1

gnome-terminal -- sh kafka_2.13-3.0.0/bin/zookeper-server-stop.sh & echo zookeper-server:Stopped

sleep 1

#gnome-terminal -- service cassandra stop & echo Cassandra:Stopped

sh $HADOOP_DIR/stop-hadoop.sh && echo HadoopCluster:Stopped

sleep 1

echo "Project execution terminated"
sleep 1
echo "Thanks for your time and effort. Press enter to close all and exit"
read continue
kill $(pgrep gnome-terminal)
