#!/bin/env bash
start_time=`date`
echo "start time: ${start_time}"

filename="$1_*"
echo $filename
#put $filename
ftp -i -n <<FTPIT
open 23.23.23.13
user username passwd
binary
mput $filename
ls

bye
FTPIT

end_time=`date`
echo "end time: ${end_time}"