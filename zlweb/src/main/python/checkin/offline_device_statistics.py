__author__ = 'wenjusun'

import MySQLdb


TABLE_PAST="m_deviceid_tmp_past"
TABLE_CURRENT="m_deviceid_tmp_current"
TABLE_DEVICES_COUNTER_BYDAY="devices_counter_byday"
TABLE_UNIQUE_DEVICES_BYDAY="unique_devices_byday"

SQL_TRUNCATE_TABLE_PAST = "TRUNCATE TABLE %s" % TABLE_PAST
SQL_TRUNCATE_TABLE_CURRENT = "TRUNCATE TABLE %s" % TABLE_CURRENT

SQL_INSERT_PAST = "INSERT INTO %s SELECT DISTINCT deviceid FROM unique_devices_byday WHERE event_date<'%s'"
SQL_INSERT_CURRENT = "INSERT INTO %s SELECT deviceid FROM unique_devices_byday WHERE event_date='%s'"

db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")

def device_counter_byday(cursor,dtstr):
    sqls = "INSERT INTO %s(event_date,unique_counter,all_counter) VALUES('%s',%d,%d)"

    get_all_device_counter_byday="SELECT COUNT(deviceid) FROM %s WHERE DATE_FORMAT(event_datetime,'%%Y-%%m-%%d')='%s'"
    cursor.execute(get_all_device_counter_byday %("checkin_devices",dtstr))
    counter_result = cursor.fetchone()
    all_counter = counter_result[0]

    get_unique_device_counter_byday="SELECT COUNT(deviceid) FROM %s WHERE event_date='%s'"
    cursor.execute(get_unique_device_counter_byday %(TABLE_UNIQUE_DEVICES_BYDAY,dtstr))
    counter_result = cursor.fetchone()
    unique_counter = counter_result[0]

    cursor.execute(sqls %(TABLE_DEVICES_COUNTER_BYDAY,dtstr,unique_counter,all_counter))

def newdevice_byday(cursor,current_date):
    print current_date


    "Data Preparation"
    cursor.execute(SQL_INSERT_PAST %(TABLE_PAST,current_date))
    cursor.execute(SQL_INSERT_CURRENT %(TABLE_CURRENT,current_date))


    sql_statistics = "SELECT COUNT(deviceid) AS counter FROM %s WHERE deviceid NOT IN (SELECT deviceid FROM %s)" %(TABLE_CURRENT,TABLE_PAST)

    # print sql_statistics

    cursor.execute(sql_statistics)
    result = cursor.fetchone()


    sql_insert_counter = "INSERT INTO newdevice_byday(event_date,device_counter)VALUES('%s',%d)" %(current_date,result[0])
    cursor.execute(sql_insert_counter)

    cursor.execute(SQL_TRUNCATE_TABLE_PAST)
    cursor.execute(SQL_TRUNCATE_TABLE_CURRENT)

    # print "TABLE cleared."



def statistics():
    cursor = db.cursor()

    for dtstr in ["2016-11-02","2016-11-03","2016-11-04","2016-11-05"]:
        newdevice_byday(cursor,dtstr)
        device_counter_byday(cursor,dtstr)
    cursor.close()
    db.close()

#for dt in range(27,31):
#    newdevice_byday('2016-10-%s' % str(dt))

statistics()