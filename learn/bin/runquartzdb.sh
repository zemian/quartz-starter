#!/usr/bin/env bash
#
# A script to invoke QuartzServer with PostgreSQL quartz config.
#
# Usage: scripts/runquartz.sh
#
HOME_DIR=$(cd `dirname $0`/.. && pwd)
$HOME_DIR/bin/runjava.sh zemian.quartzstarter.QuartzServer zemian/quartzextra/postgres-quartz.properties "$@"
