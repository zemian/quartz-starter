#!/usr/bin/env bash
#
# A script to invoke QuartzServer
#
# Usage: scripts/runquartz.sh <quartz-props-resource-name>
#
HOME_DIR=$(cd `dirname $0`/.. && pwd)
$HOME_DIR/bin/runjava.sh zemian.quartzextra.QuartzServer "$@"
