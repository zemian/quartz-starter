#!/usr/bin/env bash
#
# A script to invoke Groovy Scripts
#
# Usage: scripts/rungroovy.sh <myscript.groovy>
#
# Env variables
# JAVA_OPTS  Java options. Default to load logback `scripts/logback-error.xml`
#            that's better suited for running Groovy scripts.
#
HOME_DIR=$(cd `dirname $0`/.. && pwd)
JAVA_OPTS=${JAVA_OPTS:="-Dlogback.configurationFile=$HOME_DIR/scripts/logback-error.xml"}
$HOME_DIR/bin/runjava.sh groovy.ui.GroovyMain "$@"
