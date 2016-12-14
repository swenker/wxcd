# coding=UTF-8
__author__ = 'wenjusun'

import codecs
import MySQLdb


class DataDict():

    def __init__(self):
        self.value_code_map={}
        with codecs.open("dict.txt",encoding="UTF-8") as dict_file:
            for line in dict_file:
                line = line.strip()
                elements=line.split(',')
                for ele in elements:
                    kv_array=ele.split(':')
                    self.value_code_map[kv_array[0].strip("\"")]=kv_array[1]

        # print self.value_code_map

    def get_code(self,value_str):
        return self.value_code_map[value_str]

    def get_code_gender(self,gender_str):
        gender={u"女":2,u"男":1}

        print gender_str,gender
      # return gender[gender_str]
        return gender.get(gender_str)
    #
    # def get_code_edu(self,edu_str):
    #     edu={"硕士及以上":1,"大学本科":2,"大专":3,"高中/中专/技校":4,"初中":5,"小学及以下":6}
    #     return edu[edu_str]
    #
    # def get_code_job(self,job_str):
    #     job_list={"专业技术人员":1,"个体户/自由职业者":2,"产业、服务业工人":3,"企业/公司一般职员":4,"企业/公司管理者":5,
    #               "党政机关事业单位一般职员":6,"党政机关事业单位工作者":7,"党政机关事业单位领导干部":8 ,"农村外出务工人员":9,
    #               "农民":10,"学生":11,"无业、下岗、失业":12,"退休":13,"其他":14}
    #     return job_list[job_str]
    #
    # def get_code_income(self,income_str):
    #     income_list={"无收入":1,"500元及以下":2,"501～1000元":3,"1001～1500元":4,"1501～2000元":5,"2001～3000元":6,
    #                  "3001～5000元":7,"5001～8000元":8,"8001～12000元":9,"12000元以上":10 }
    #
    #     return income_list[income_str]
    #
    # def get_code_iscity(self,iscity_str):
    #     iscity_list={"unknown":1,"乡村":2,"城市":3,"城郊":4}
    #     return iscity_list[iscity_str]

data_dict=DataDict()

class UserProfile():
    userid=None
    gender=None
    edu=None
    job=None
    income=None
    birthyear=None
    province=None
    city=None
    iscity=None

    def __str__(self):
        return "userid:%s,gender:%s,edu:%s,job:%s,income:%s,birthyear:%s,province:%s,city:%s,iscity:%s"\
                %(self.userid,self.gender,self.edu,self.job,self.income,self.birthyear,self.province,self.city,self.iscity)

TABLE_USER_PROFILE='user_profile'
def save_to_db(up_list):
    db = MySQLdb.connect(host="127.0.0.1",user="root",passwd="nopass",db="test_user_action",charset="utf8")
    cursor = db.cursor()
    base_sql="INSERT INTO "+TABLE_USER_PROFILE+" VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    for upr in up_list:
        upr_new = upr
        upr_new.gender=data_dict.get_code(upr.gender)
        upr_new.edu=data_dict.get_code(upr.edu)
        upr_new.job=data_dict.get_code(upr.job)
        upr_new.income=data_dict.get_code(upr.income)
        upr_new.iscity=data_dict.get_code(upr.iscity)

        cursor.execute(base_sql,(upr_new.userid,upr_new.gender,upr_new.birthyear,
                                 upr_new.edu,upr_new.job,upr_new.income,
                                 upr_new.province,upr_new.city,upr_new.iscity

        ))
    db.commit()
    cursor.close()
    db.close()

def parse_user_profile(filepath):
    """
      "B23A80DF197394574DB92118637F42B1","女","1989","大专","企业/公司一般职员","2001～3000元","浙江","温州","城市"
     """
    up_list=[]
    with codecs.open(filepath,encoding='UTF-8') as upf:
        for line in upf:
            # fields = line.strip("\ufeff").split(',')
            fields = line.strip().split(',')
            # print line

            up_record = UserProfile()
            up_record.userid = fields[0].strip("\"")
            up_record.gender = fields[1].strip("\"")
            up_record.birthyear = fields[2].strip("\"")
            up_record.edu = fields[3].strip("\"")
            up_record.job = fields[4].strip("\"")
            up_record.income = fields[5].strip("\"")
            up_record.province = fields[6].strip("\"")
            up_record.city = fields[7].strip("\"")
            up_record.iscity = fields[8].strip("\"")

            up_list.append(up_record)
            # print up_records.__str__()

            # break

    return up_list



import sys
reload(sys)
print sys.getdefaultencoding()
sys.setdefaultencoding('UTF-8')
print sys.getdefaultencoding()
# up_list=parse_user_profile(r"C:\ZZZZZ\0-sunwj\wxcd\jqweb\src\main\python\user_profiles.csv")
# save_to_db(up_list)
man=u'男'
# print data_dict.get_code(man)
print data_dict.get_code_gender(man)
# print len("7F64F0CAB28DDE6781F430FCCF09F3D2")