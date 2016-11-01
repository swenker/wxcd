__author__ = 'wenjusun'


import json
import MySQLdb


class DeviceCounter():
    def __init__(self,date,all,unique):
        self.date = date
        self.all = all
        self.unique = unique

    def __str__(self):
        return self.__dict__.__str__()

counters = []


# for counter in json.loads('[{"date":"2016-10-07","all":350767,"unique":201213}]'):
with open('/home/wenjusun/wxcd/zlweb/src/main/python/checkin/devices_all_unique.json') as f:
    for counter in json.load(f):
        counters.append(DeviceCounter(counter['date'],int(counter['all']),int(counter['unique'])))

db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")
try:
    cursor = db.cursor()

    for dc in counters:
        cursor.execute(("INSERT INTO devices_counter_byday(`event_date`,`all`,`unique`)VALUES('%s',%d,%d)"%(dc.date,dc.all,dc.unique)))

    cursor.close()
    db.close()

except BaseException,e:
    print e

# print counters