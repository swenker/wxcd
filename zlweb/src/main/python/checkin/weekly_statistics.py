import MySQLdb
import datetime

db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")
TABLE_CHECKIN_DEVICES = "checkin_devices"
TABLE_DEVICES_COUNTER_BYWEEK = "devices_counter_byweek"

class WeeklyStatistics():
    def __init__(self,cursor):
        self.cursor = cursor

    def get_all_counter_of_week(self,week):
        sqls = "SELECT COUNT(*) FROM %s WHERE WEEK(event_datetime,3)=%d"

        sqls = sqls %(TABLE_CHECKIN_DEVICES,week)
        self.cursor.execute(sqls)
        result = cursor.fetchone()

        return result[0]

    def get_unique_counter_of_week(self,week):
        sqls = "SELECT COUNT(DISTINCT deviceid) FROM %s WHERE WEEK(event_datetime,3)=%d"

        sqls = sqls %(TABLE_CHECKIN_DEVICES,week)
        self.cursor.execute(sqls)
        result = cursor.fetchone()

        return result[0]

    def save_counter_to_db(self,month_week,unique_counter,all_counter):
        sqls = "INSERT INTO %s (event_week,unique_counter,all_counter) VALUES('%s',%d,%d)"
        sqls = sqls %(TABLE_DEVICES_COUNTER_BYWEEK,month_week,unique_counter,all_counter)
        self.cursor.execute(sqls)

    #the first days in the new year is counted into the last year.
    def last_week_statistics(self):
        today = datetime.date.today()
        day_in_last_week = today - datetime.timedelta(days=7)
        last_week = day_in_last_week.isocalendar()[1]
        target_year = day_in_last_week.isocalendar()[0]

        month_week = "%d/%s" %(target_year,str(int(last_week)).zfill(2))

        unique_counter = self.get_unique_counter_of_week(last_week)
        all_counter = self.get_all_counter_of_week(last_week)

        # print month_week,unique_counter,all_counter
        self.save_counter_to_db(month_week,unique_counter,all_counter)


if __name__ == "__main__":
    cursor = db.cursor()
    weekly_cal = WeeklyStatistics(cursor)
    weekly_cal.last_week_statistics()

    cursor.close()
    db.close()