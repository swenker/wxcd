__author__ = 'wenjusun'

import MySQLdb
import os

"Parse the filename entries from gfs server and save the parsed fields into mysql"

"""
/export/sdc1/brick/netapp08/handset_checkin/outgoing/2016-10-12/23:59/PRODUCTION_NORMAL/checkin_325712594104320_2016.10.12,23:59:55:338.gpb
"""
TABLE_DEVICE_ID="checkin_devices"
TABLE_UNIQUE_DEVICE_ID_BYDAY="unique_devices_byday"
# DEVICE_ID_TABLE="checkin_devices_test"


"""
CREATE TABLE `checkin_devices`(`deviceid` VARCHAR(20),`event_datetime` DATETIME,`dtmilli` INTEGER);
"""

"TIMESTAMP just like java--1970----2038"
"DATETIME 0000-9999"

def handle_single_file(filename,db,cursor):
    save_raw_data(filename,db,cursor)
    save_distinct_byday(filename,db,cursor)


def save_raw_data(filename,db,cursor):
    all_device_list=[]

    with open(filename,'r') as f:
        for one_line in f:
            first_str = one_line[one_line.rfind('/')+len('checkin_')+1:]
            str_tokens = first_str.split('_')

            device_id = str_tokens[0]
            dtstr = str_tokens[1][:19]
            dtmilli = str_tokens[1][20:23]

            all_device_list.append((device_id,dtstr,dtmilli))

    line_counter = 0
    total_counter = len(all_device_list)

    try:
        base_sqls= 'INSERT INTO '+TABLE_DEVICE_ID+' (deviceid,event_datetime,dtmilli)VALUES'
        value_sqls=''

        db.autocommit(False)

        for line in all_device_list:
            k,v1,v2  = line[0],line[1],line[2]
            value_sqls+="('%d','%s',%d)," %(long(k),v1,int(v2))
            line_counter +=1

            # print value_sqls
            if line_counter % 5000 == 0:
                final_sqls = base_sqls + value_sqls[0 : len(value_sqls) - 1]
                cursor.execute(final_sqls)
                db.commit()
                value_sqls=''

                print line_counter*100/total_counter ,

        if value_sqls:
            final_sqls=base_sqls+value_sqls[0:len(value_sqls)-1]
            cursor.execute(final_sqls)
            db.commit()


        db.autocommit(True)

    except BaseException,e:
        print e

def save_distinct_byday(filename,db,cursor):

    dtstr=filename.split("_")[-1]
    try:
        base_sqls= "INSERT INTO "+TABLE_UNIQUE_DEVICE_ID_BYDAY +"(deviceid,event_date) SELECT DISTINCT deviceid,'%s' FROM "+TABLE_DEVICE_ID+" WHERE date_format(event_datetime,'%%Y-%%m-%%d')='%s'"
        cursor.execute((base_sqls %(dtstr,dtstr)))

    except BaseException,e:
        print e


def handle_all_files():
    #list dir
    current_dir = "/home/wenjusun/logs"
    #files = [f for f in os.listdir(current_dir) if os.path.isfile(current_dir+'/'+f)]

    # files = ['ckfilelist_2016-10-18','ckfilelist_2016-10-19','ckfilelist_2016-10-20']
    #files = ['ckfilelist_2016-10-21','ckfilelist_2016-10-22','ckfilelist_2016-10-23']
    # files = ['ckfilelist_2016-10-24','ckfilelist_2016-10-25','ckfilelist_2016-10-26']
    # files = ['ckfilelist_2016-10-27','ckfilelist_2016-10-28','ckfilelist_2016-10-29','ckfilelist_2016-10-30']
    #files = ['ckfilelist_2016-10-31','ckfilelist_2016-11-01']
    # files = ['ckfilelist_2016-11-02','ckfilelist_2016-11-03','ckfilelist_2016-11-04','ckfilelist_2016-11-05']
    # files = ['ckfilelist_2016-11-06','ckfilelist_2016-11-07','ckfilelist_2016-11-08']
    # files = ['ckfilelist_2016-11-09','ckfilelist_2016-11-10']
    files = ['ckfilelist_2016-11-11','ckfilelist_2016-11-12','ckfilelist_2016-11-13']
    files = ['ckfilelist_2016-11-14']
    # print files
    db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")
    try:
        cursor = db.cursor()

        for filename in files:
            print filename,
            handle_single_file(("%s/%s" %(current_dir,filename)),db,cursor)
            # handle_single_file("/home/wenjusun/logs/ckfilelist_2016-10-07",db,cursor)

            print

        cursor.close()
    except BaseException,e:
        db.close()


if __name__=="__main__":
    # handle_single_file("/home/wenjusun/logs/mytest",None,None)
    handle_all_files()
    #dtstr='2016-10-31'
    #base_sqls= "INSERT INTO "+TABLE_UNIQUE_DEVICE_ID_BYDAY +"(deviceid,event_date) SELECT DISTINCT deviceid,'%s' FROM "+TABLE_DEVICE_ID+" WHERE date_format(event_datetime,'%%Y-%%m-%%d')='%s'"
    #print base_sqls %(dtstr,dtstr)