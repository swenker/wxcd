CREATE DATABASE `test_user_action`;

USE test_user_action;
CREATE TABLE `user_profile`(
    userid CHAR(34) NOT NULL,
    gender TINYINT,
    birthyear int,
    edu TINYINT,
    job TINYINT,
    income TINYINT,
    province VARCHAR(10),
    city VARCHAR(10),
    iscity TINYINT
) ENGINE=Innodb DEFAULT CHARSET=utf8;

CREATE TABLE `action_item`(
   userid CHAR(34) NOT NULL,
   event_time DATETIME,
   relative_seconds INTEGER,
   prog_name VARCHAR(50),
   prog_args VARCHAR(110)
)ENGINE=Innodb DEFAULT CHARSET=utf8;


    224 "女"
    776 "男"

          1 "EDU"
     69 "初中"
    290 "大专"
    392 "大学本科"
      3 "小学及以下"
     56 "硕士及以上"
    190 "高中/中专/技校"

         1 "JOB"
   144


   144 "专业技术人员"
    96 "个体户/自由职业者"
    31 "产业、服务业工人"
   211 "企业/公司一般职员"
    98 "企业/公司管理者"
    94 "党政机关事业单位一般职员"
     2 "党政机关事业单位工作者"
    25 "党政机关事业单位领导干部"
    63 "其他"
    21 "农村外出务工人员"
     6 "农民"
   175 "学生"
    22 "无业、下岗、失业"
    12 "退休"

     1 "INCOME"
    66 "1001～1500元"
    29 "12000元以上"
   115 "1501～2000元"
   222 "2001～3000元"
   226 "3001～5000元"
    97 "5001～8000元"
    24 "500元及以下"
    34 "501～1000元"
    39 "8001～12000元"
   148 "无收入"

        1 "ISCITY"
   160 "unknown"
   127 "乡村"
   640 "城市"
    73 "城郊"