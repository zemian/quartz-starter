#!/usr/bin/env bash
#
# A script to invoke Groovy Scripts with default of PostgreSQL quartz config
#
# Usage: scripts/rungroovypostgres.sh <myscript.groovy> [arguments]
#
# Env variables
# JAVA_OPTS  Java options. Default to load logback `logback-error.xml`
#            that's better suited for running Groovy scripts.
#
GROOVY_SCRIPT=$1
shift

HOME_DIR=$(cd `dirname $0`/.. && pwd)
export JAVA_OPTS=${JAVA_OPTS:="-Dlogback.configurationFile=logback-error.xml"}
$HOME_DIR/bin/runjava.sh \
  groovy.ui.GroovyMain \
  $GROOVY_SCRIPT \
  zemian/quartzextra/postgres-quartz.properties \
  "$@"
