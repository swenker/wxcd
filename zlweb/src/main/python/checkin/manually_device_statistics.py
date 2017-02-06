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

#for dt in range(27,31):
#    newdevice_byday('2016-10-%s' % str(dt))

def all_day():
    cursor = db.cursor()
    with open("all_day") as all_data:
        for line in all_data:
            fileds = line.strip().split("|")
            sqls = "insert into %s(event_date,unique_counter,all_counter)values('%s',%d,%d)" \
                   %(TABLE_DEVICES_COUNTER_BYDAY,fileds[0].strip(),int(fileds[1].strip()),int(fileds[2].strip()))
            cursor.execute(sqls)
    cursor.close()
    db.close()


if __name__=="__main__":
    import sys
    # dts = sys.argv[1]
    all_day()

