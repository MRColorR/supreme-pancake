#!/bin/bash
python3 datacleaner.py
bash $HADOOP_DIR/bin/hdfs dfs -mkdir /data
bash $HADOOP_DIR/bin/hdfs dfs -put ./fout.csv /data
