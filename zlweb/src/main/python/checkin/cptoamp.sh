#!/bin/env bash

if [ $# -lt 1 ]; then
    echo "dtstr needed $# "
    exit 0
fi

start_time=`date`
echo "start time: ${start_time}"
dtstr=$1
echo "date string is: $dtstr"

function do_cp(){
  for i in {1..8}
    do
        echo "starting ... ${i}"
        tar -C /export/sdc1/brick/netapp0${i}/handset_checkin/outgoing/ -cf - ${dtstr} |ssh amp03-p-e-mmi "cat > /mnt/resource/checkin/${dtstr}_p${i}.tar"
    done
}
do_cp

end_time=`date`
echo "end time: ${end_time}"