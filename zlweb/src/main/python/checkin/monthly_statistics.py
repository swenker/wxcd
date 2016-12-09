import MySQLdb
import datetime

db = MySQLdb.connect(host="localhost",user="indigo",passwd="indigopwd",db="api_log",charset="utf8")
TABLE_CHECKIN_DEVICES = "checkin_devices"
TABLE_DEVICES_COUNTER_BYMONTH = "devices_counter_bymonth"

class MonthlyStatistics():
    def __init__(self,cursor):
        self.cursor = cursor

    def get_all_counter_of_month(self,month):
        sqls = "SELECT COUNT(*) FROM %s WHERE DATE_FORMAT(event_datetime,'%%Y-%%m')='%s'"

        sqls = sqls %(TABLE_CHECKIN_DEVICES,month)
        self.cursor.execute(sqls)
        result = self.cursor.fetchone()

        return result[0]

    def get_unique_counter_of_month(self,month):
        sqls = "SELECT COUNT(DISTINCT deviceid) FROM %s WHERE DATE_FORMAT(event_datetime,'%%Y-%%m')='%s'"

        sqls = sqls %(TABLE_CHECKIN_DEVICES,month)
        self.cursor.execute(sqls)
        result = self.cursor.fetchone()

        return result[0]

    def save_counter_to_db(self,month,unique_counter,all_counter):
        sqls = "INSERT INTO %s (event_month,unique_counter,all_counter) VALUES('%s',%d,%d)"
        sqls = sqls %(TABLE_DEVICES_COUNTER_BYMONTH,month,unique_counter,all_counter)
        self.cursor.execute(sqls)

    def last_month_statistics(self):
        today = datetime.date.today()
        first_day_this_month = today.replace(day=1)
        last_day_last_month = first_day_this_month - datetime.timedelta(days=1)

        month = last_day_last_month.strftime("%Y-%m")

        unique_counter = self.get_unique_counter_of_month(month)
        all_counter = self.get_all_counter_of_month(month)

        print month,unique_counter,all_counter

        # self.save_counter_to_db(month,unique_counter,all_counter)


if __name__ == "__main__":
    cursor = db.cursor()
    monthly_cal = MonthlyStatistics(cursor)
    monthly_cal.last_month_statistics()

    cursor.close()
    db.close()