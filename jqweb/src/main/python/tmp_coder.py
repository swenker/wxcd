# coding=UTF-8
__author__ = 'wenjusun'

class DataDict():
    def get_code_gender(self,gender_str):
        gender={"女":2,"男":1}
        return gender[gender_str]

    def get_code_edu(self,edu_str):
        edu={"硕士及以上":1,"大学本科":2,"大专":3,"高中/中专/技校":4,"初中":5,"小学及以下":6}
        return edu[edu_str]

    def get_code_job(self,job_str):
        job_list={"专业技术人员":1,"个体户/自由职业者":2,"产业、服务业工人":3,"企业/公司一般职员":4,"企业/公司管理者":5,
                  "党政机关事业单位一般职员":6,"党政机关事业单位工作者":7,"党政机关事业单位领导干部":8 ,"农村外出务工人员":9,
                  "农民":10,"学生":11,"无业、下岗、失业":12,"退休":13,"其他":14}
        return job_list[job_str]



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


def parse_user_profile(filepath):
    """
      "B23A80DF197394574DB92118637F42B1","女","1989","大专","企业/公司一般职员","2001～3000元","浙江","温州","城市"
     """
    with open(filepath) as upf:
        for line in upf:
            fields = line.strip().split(',')
            print fields
            for f in fields:
                print f.strip('"')

            break




parse_user_profile(r"C:\Users\wenjusun\Downloads\dataset_616718\data\demographic-1.csv")