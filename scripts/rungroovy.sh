#!/usr/bin/env bash

#
# A script to run Groovy script for Quartz scheduler
#
# Usage: scripts/rungroovy.sh <myscript.groovy>
#

HOME_DIR=$(cd `dirname $0`/.. && pwd)
CONFIG=zemian/quartz/examples/quartz-postgres.properties

java -cp "$HOME_DIR/target/classes:$HOME_DIR/target/dependency/*" \
-Dlogback.configurationFile=$HOME_DIR/scripts/logback-error.xml \
-DquartzConfig=$CONFIG groovy.ui.GroovyMain "$@"
