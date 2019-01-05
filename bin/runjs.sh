#!/usr/bin/env bash
#
# A script to invoke JavaScripts
#
# Usage: scripts/runjs.sh <myscript.js>
#
HOME_DIR=$(cd `dirname $0`/.. && pwd)
export JAVA_OPTS=${JAVA_OPTS:="-Dlogback.configurationFile=logback-error.xml"}
$HOME_DIR/bin/runjava.sh zemian.quartzstarter.util.RunJS "$@"
