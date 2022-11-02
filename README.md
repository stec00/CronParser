# CronParser

## Pre-requisites

The following need to be installed:
1. [Java 19](https://www.oracle.com/java/technologies/downloads/)
2. [Apache Maven](https://maven.apache.org/download.cgi) _(download and extract the binary zip archive then add the "bin" folder to your system path)_

## Installation

1. Extract or clone the source code to a local folder.
2. Open a terminal window and change into the source code root folder.
3. Run `mvn install`

## Running

### Syntax

`java -jar <jar file path> "<Cron string>"`

### Example

`java -jar target/CronParser-1.0-SNAPSHOT.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`

outputs:
```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```