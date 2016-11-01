__author__ = 'wenjusun'

import MySQLdb

"""
select count(distinct deviceid) from checkin_devices where event_datetime>=str_to_date('2016-10-17 00:00:00','%Y-%m-%d %T') and event_datetime<str_to_date('2016-10-17 23:59:59','%Y-%m-%d %T')
select count(*) from checkin_devices where event_datetime>=str_to_date('2016-10-17 00:00:00','%Y-%m-%d %T') and event_datetime<str_to_date('2016-10-18 00:00:00','%Y-%m-%d %T')

"""

class CheckinDevicesStatistics():

    def __init__(self,cursor):
        self.cursor = cursor


    def get_unique_device_counter_per_day(self,dtstr):
        sql = "select count(distinct deviceid) from checkin_devices where " \
              "event_datetime>=str_to_date('"+dtstr+" 00:00:00','%Y-%m-%d %T') and event_datetime<=str_to_date('"+dtstr+" 23:59:59','%Y-%m-%d %T')"

        self.cursor.execute(sql)
        result = self.cursor.fetchone()[0]

        return result

    def get_all_device_counter_per_day(self,dtstr):
        sql = "select count(*) from checkin_devices where " \
              "event_datetime>=str_to_date('"+dtstr+" 00:00:00','%Y-%m-%d %T') and event_datetime<=str_to_date('"+dtstr+" 23:59:59','%Y-%m-%d %T')"
        self.cursor.execute(sql)
        result = self.cursor.fetchone()[0]

        return result


    def get_all_device_counter_per_week(self,dt_start,dt_end):
        sql = "select count(distinct deviceid) from checkin_devices where " \
              "event_datetime>=str_to_date('"+dt_start+" 00:00:00','%Y-%m-%d %T') and event_datetime<=str_to_date('"+dt_end+" 23:59:59','%Y-%m-%d %T')"

    def get_all_device_counter_per_month(self,month_str):
        sql = "select count(distinct deviceid) from checkin_devices where "


    def get_new_device_counter_per_day(self,dtstr):
        sql = ""

    def get_new_device_counter_per_week(self,dtstr):
        sql = ""



def get_db():
    return MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")

def get_all_device_counter_in_days():
    db = get_db()
    cursor = db.cursor()
    checkin_devices = CheckinDevicesStatistics(cursor)

    # for dt in ('2016-10-07','2016-10-08','2016-10-09','2016-10-10','2016-10-11','2016-10-12','2016-10-13','2016-10-14','2016-10-15','2016-10-16','2016-10-17'):
    # dtlist=['2016-10-18','2016-10-19','2016-10-20']
    # dtlist=['2016-10-07','2016-10-08','2016-10-09','2016-10-10','2016-10-11','2016-10-12','2016-10-13','2016-10-14','2016-10-15','2016-10-16','2016-10-17','2016-10-18','2016-10-19','2016-10-20']
    # dtlist=['2016-10-21','2016-10-22','2016-10-23']
    dtlist=['2016-10-24','2016-10-25','2016-10-26']
    for dt in dtlist:
        # print "%s\t%d" %(dt,checkin_devices.get_unique_device_counter_per_day(dt))
        # print "%s,%d" %(dt,checkin_devices.get_all_device_counter_per_day(dt))
        print '{"date":"%s","all":%d,"unique":%d},' %(dt,checkin_devices.get_all_device_counter_per_day(dt),checkin_devices.get_unique_device_counter_per_day(dt))


    cursor.close()
    db.close()

def dump_devices_counter():
    db = get_db()
    cursor = db.cursor()
    cursor.execute("select event_date,all_counter,unique_counter from devices_counter_byday order by event_date")
    result = cursor.fetchall()
    json_str = ''

    for r in result:
        # json_str += '{"date":"%s","all":%d,"unique":%d},' %(r['event_date'],r['all_counter'],r['unique_counter'])
        json_str += '{"date":"%s","all":%d,"unique":%d},' %(r[0],r[1],r[2])

    if len(json_str) > 0 :
        json_str = json_str[0:len(json_str)-1]

    with open("devices_counter_all_unique.json",'w') as f:
        f.write("[")
        f.write(json_str)
        f.write("]")


    cursor.close()
    db.close()




if __name__ == "__main__":
    # get_all_device_counter_in_days()
    dump_devices_counter()


