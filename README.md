# supreme-pancake

<!-- Project README scaffold: https://github.com/MRColorR/project-template -->

[Overview](#overview) | [Getting started](#getting-started) | [Documentation](#documentation) | [Contributing](#contributing) | [Support](#support) | [License](#license)

## Overview

Repo for Big Data Management project

Three components were created in this project, a producer / data collector (kafka), a distributed database (CassandraDB) and a consumer / data processor (Spark).  
The collection of data from a network of sensors was simulated, which then had to be processed and stored in a distributed and efficient way. The data collected (or generated) by kafka were then processed by spark and saved for long-term archiving on cassanda db.  
The connection between the PCs has been made simple and scalable using Zerotier.

- [x] Leave a star ⭐ if you like this project 🙂 thank you.

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

Ko-fi supports Mattia / MrColorRain's work across projects.

Support ongoing development, testing and documentation through [Ko-fi](https://ko-fi.com/mrcolorrain). One-time contributions and monthly memberships are welcome, and you can pledge above a tier's minimum.

Your contribution helps me keep building, testing and sharing useful work. Explore the membership benefits on Ko-fi and choose the way you would like to support. Technical decisions remain independent.

See the [supporters and sponsors directory](https://github.com/MRColorR/MRColorR/blob/main/SPONSORS.md) for acknowledgments.

## License

A project license has not been declared in this repository. Contact the maintainer about reuse permissions.
