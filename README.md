# supreme-pancake
Repo for Big Data Management project

Three components were created in this project, a producer / data collector (kafka), a distributed database (CassandraDB) and a consumer / data processor (Spark).  
The collection of data from a network of sensors was simulated, which then had to be processed and stored in a distributed and efficient way. The data collected (or generated) by kafka were then processed by spark and saved for long-term archiving on cassanda db.  
The connection between the PCs has been made simple and scalable using Zerotier.

## What's inside
- Kafka module
- Cassanda db module
- Spark module
- Data cleaning sripts
- Distributed job start and stop scripts
- Project runme script
- project document with details
