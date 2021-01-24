#!/usr/bin/env bash
#rm -v "__ABSOLUTE_PATH__" >> $REPORT_FILE 2>&1

NOW=$(date +%Y-%m-%d_%H-%M-%S);
REPORT_FILE=delete_duplicate_report-${NOW}.txt;

__COMMANDS__

echo deletion completed, report file \'$(pwd)/$REPORT_FILE\'