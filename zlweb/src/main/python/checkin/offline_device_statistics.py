__author__ = 'wenjusun'

import MySQLdb


TABLE_PAST="m_deviceid_tmp_past"
TABLE_CURRENT="m_deviceid_tmp_current"

SQL_TRUNCATE_TABLE_PAST = "TRUNCATE TABLE %s" % TABLE_PAST
SQL_TRUNCATE_TABLE_CURRENT = "TRUNCATE TABLE %s" % TABLE_CURRENT

SQL_INSERT_PAST = "INSERT INTO %s SELECT DISTINCT deviceid FROM unique_devices_byday WHERE event_date<'%s'"
SQL_INSERT_CURRENT = "INSERT INTO %s SELECT deviceid FROM unique_devices_byday WHERE event_date='%s'"


def newdevice_byday(current_date):
    print current_date
    db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")
    try:
        cursor = db.cursor()

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
        cursor.close()
        db.close()

    except BaseException,e:
        print e


for dt in range(27,31):
    newdevice_byday('2016-10-%s' % str(dt))