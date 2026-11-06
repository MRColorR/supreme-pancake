# supreme-pancake

<!-- Project README scaffold: https://github.com/MRColorR/project-template -->

**Follow simulated sensor data through a Kafka, Spark, and Cassandra pipeline.**

[**Explore the pipeline**](#getting-started) · [Documentation](#documentation) · [Support](#support)

**Leave a star ⭐ if you like this project 🙂 Thank you!**

## Overview

Repo for Big Data Management project

Three components were created in this project, a producer / data collector (kafka), a distributed database (CassandraDB) and a consumer / data processor (Spark).  
The collection of data from a network of sensors was simulated, which then had to be processed and stored in a distributed and efficient way. The data collected (or generated) by kafka were then processed by spark and saved for long-term archiving on cassanda db.  
The connection between the PCs has been made simple and scalable using Zerotier.


## Getting started

Start with the project report under [Documentation](#documentation), then review [run.sh](run.sh), [start.sh](start.sh) and [stop.sh](stop.sh) for the project workflow.

## Documentation

- [Documento di progetto - Big data.pdf](Documento%20di%20progetto%20-%20Big%20data.pdf)
- [pom.xml](pom.xml)
- [run.sh](run.sh)

<a name="whats-inside"></a>

### What's inside

- Kafka module
- Cassanda db module
- Spark module
- Data cleaning scripts
- Distributed job start and stop scripts
- Project runme script
- Project document with details

## Contributing

Ideas, bug reports, documentation fixes and focused changes are welcome through this repository's collaboration channels.

Please include enough context to reproduce a problem or review a change.

## Support

Help fund ongoing development, testing, and documentation with a one-time contribution or monthly membership on [Ko-fi](https://ko-fi.com/mrcolorrain). Technical decisions remain independent.

Thank you to the people who support MrColorRain’s work. See the [supporters and sponsors directory](https://github.com/MRColorR/MRColorR/blob/main/SPONSORS.md).

## License

A project license has not been declared in this repository. Contact the maintainer about reuse permissions.
