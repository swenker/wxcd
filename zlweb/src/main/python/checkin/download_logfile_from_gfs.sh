#!/usr/bin/env bash

dts=`date --date='yesterday' '+%Y-%m-%d'`

logfile=~/cklist-ckfilelist_${dts}.tar.gz

scp gfs61-ccs-p-e:${logfile} .


echo "Downloaded log files from GFS server."