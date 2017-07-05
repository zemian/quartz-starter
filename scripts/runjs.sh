#!/usr/bin/env bash

HOME_DIR=$(cd `dirname $0`/.. && pwd)

CP=$HOME_DIR/target/classes:\
$HOME_DIR/target/dependency/HikariCP-java6-2.3.13.jar:\
$HOME_DIR/target/dependency/c3p0-0.9.5.2.jar:\
$HOME_DIR/target/dependency/logback-classic-1.2.3.jar:\
$HOME_DIR/target/dependency/logback-core-1.2.3.jar:\
$HOME_DIR/target/dependency/mchange-commons-java-0.2.11.jar:\
$HOME_DIR/target/dependency/postgresql-42.1.1.jar:\
$HOME_DIR/target/dependency/quartz-2.3.0.jar:\
$HOME_DIR/target/dependency/slf4j-api-1.7.7.jar

java -cp $CP zemian.quartz.examples.RunScript "$@"
