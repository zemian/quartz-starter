#!/usr/bin/env bash
#
# A script to invoke Java Program
#
# Usage: scripts/runjava.sh <MainClassName> [arguments]
#
# Env variables:
# JAVA_CP    Java classpath. Default to 'target/classes' and
#            'target/hello-quartz-app.jar'.
# JAVA_OPTS  Java options. Default to empty.
#
HOME_DIR=$(cd `dirname $0`/.. && pwd)
JAVA_CP=${JAVA_CP:="$HOME_DIR/target/classes:$HOME_DIR/target/hello-quartz-app.jar"}
JAVA_OPTS=${JAVA_OPTS:=}
if [[ "$(uname -s)" == CYGWIN* ]]; then
    JAVA_CP=$(cygpath -mp $JAVA_CP)
fi
java -cp "$JAVA_CP" $JAVA_OPTS "$@"
