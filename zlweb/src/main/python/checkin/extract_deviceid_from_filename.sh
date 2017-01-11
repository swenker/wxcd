#!/bin/evn bash
dts=`date --date='yesterday' '+%Y-%m-%d'`
echo $dts
find /export/sdc1/brick/netapp0[1-8]/handset_checkin/outgoing/${dts}/*/PRODUCTION_NORMAL/ -type f -name "*.gpb">ckfilelist_${dts}

tar -czf cklist-ckfilelist_${dts}.tar.gz ckfilelist_${dts}
rm ckfilelist_${dts}
