## Hello Quartz

This project provides examples for
https://github.com/quartz-scheduler/quartz[Quartz Scheduler] that demonstrate
some of its features.

The Quartz Scheduler is a very flexible scheduling library. The easiest way to
learn is to explore some the examples provided here, and then try something on
your own. There are many configuration settings you can use to control the
scheduler, and you can easily test any of these in a Java properties file to
explore the features.

This project configured Quartz library with https://logback.qos.ch[Logback] as
the logger, and default to INFO level to STDOUT for log messages. You may edit
the `src/main/resources/logback.xml` to increase the level to "DEBUG" if you
wish to see more activities in action by the scheduler.

The project also provided a default PostgreSQL database as backend storage. If
you wish to test out the scheduler in any other DB, just create similar
configuration and change out the datasource configuration settings. You will
find many quartz configuration examples under
`src/main/resources/zemian/hello/quartz` folder.

NOTE: Quartz is a library, and it does not have good built-in out of the
box services. This means you still would need to write some code to get it
started!

## Requirements

- Java 7 or higher
- Maven 3.3 or higher
- PostgreSQL 9 or higher

## Setup Postgres Database

You would need to download the DB schema sql scripts from the quartz project.
See the `db/readme.md` file for details. As example, we assumed you have
`db/tables_postgres.sql` file ready.

```
# Use psql client and a admin DB user to connect to PostgreSQL for setup
bash> psql -U postgres

psql> -- Verify and list of your current DBs
psql> \dl

psql> -- Create DB User (No password) and DB for Quartz
psql> CREATE ROLE quartz WITH LOGIN;
psql> CREATE DATABASE quartz WITH OWNER=quartz;
psql> \c quartz quartz

psql> -- Create Quartz Schema
psql> \i db/tables_postgres.sql
psql> -- Verify schemas
psql> \d
psql> \d qrtz_triggers
```


## Quartz Examples

This project provides a reusable `QuartzServer` program that can take any
quartz properties configuration file and run it as a server. You can press
`CTRL+C` keys on console to stop it.

### InMemory Scheduler

To run a quartz scheduler as a server using in memory storage, type the
following:

```
mvn exec:java -Dexec.mainClass=zemian.hello.quart.QuartzServer \
  -DquartzConfig=zemian/hello/quartz/quartz.properties
```

### Postgres DB Scheduler

To run a quartz scheduler as a server connecting to postgres DB, type the
following:

```
mvn exec:java -Dexec.mainClass=zemian.hello.quart.QuartzServer \
  -DquartzConfig=zemian/hello/quartz/quartz-postgres.properties
```

### Inserting Jobs Programmatically Using API

There are many ways to insert jobs into scheduler. One way is programmatically
using the API. Quartz uses the same `org.quartz.Scheduler` interface as the
server for inserting job. You just need to ensure NOT to start the scheduler
instance since you are writing only a "client" program. Simply use the API to
insert/update jobs and then exit.

Here is an example of a client program that insert couple running jobs. Note
that you can use the same configuration file you pass to "server" side so you
connect to the same datasource store.

You should create a new client program for each set of new jobs that you want
to create and insert.

```
mvn exec:java -Dexec.mainClass=zemian.hello.quart.QuartzHelloClient \
  -DquartzConfig=zemian/hello/quartz/quartz-postgres.properties
```

### Inserting Jobs Using Scripts

Writing Java client is not too hard, but it requires you to compile the code.
Simple clients like above is a good fit for scripting. You can easily run
script engine using Java platform. We have added Groovy script language to the
project dependency, and you may try out our demo as documented here.

NOTE: The `scripts/rungroovy.sh` is for MacOS or Linux only. For Windows, you
would need to fix the classpath and try to run it with Cygwin.

Step 1: Setup jars the first time

```
mvn dependency:copy-dependencies
```

Step 2: Run any scripts
```
CONFIG=zemian/hello/quartz/quartz-postgres.properties scripts/rungroovy.sh scripts/listJobs.groovy
```

Here is how we can pause and resume a job
```
CONFIG=zemian/hello/quartz/quartz-postgres.properties scripts/rungroovy.sh scripts/pauseJob.groovy HelloJob
CONFIG=zemian/hello/quartz/quartz-postgres.properties scripts/rungroovy.sh scripts/resumeJob.groovy HelloJob
```

### Writing Dynamic Job

Quartz job is implemented using Java, and you would need to recompile the code
whenever you change the code. Often time we have small job that can be done
using simple scripting. The simple syntax of Groovy for writing dynamic job is
a very powerful way to create new quartz job. Our project included a
`ScriptJob` class that let you reference a script file and execute it at
runtime. Here is an example:

```
CONFIG=zemian/hello/quartz/quartz-postgres.properties scripts/rungroovy.sh scripts/newDurableScriptJob.groovy HelloScriptJob `pwd`/scripts/jobs/HelloScriptJob.groovy
CONFIG=zemian/hello/quartz/quartz-postgres.properties scripts/rungroovy.sh scripts/newCronTrigger.groovy HelloScriptJob HourlyTrigger '0 0 * * * ?'
```
