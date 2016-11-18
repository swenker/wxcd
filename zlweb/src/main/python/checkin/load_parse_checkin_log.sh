#!/bin/bash

PYTHON_SCRIPT_PATH=`dirname $0`

log_parser=ckfilename_parser.py
offline_statistics=offline_device_statistics.py

dts=`date --date='yesterday' '+%Y-%m-%d'`

python ${PYTHON_SCRIPT_PATH}/${log_parser} $dts
python ${PYTHON_SCRIPT_PATH}/${offline_statistics} $dts


