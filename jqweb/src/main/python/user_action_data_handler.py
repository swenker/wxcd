__author__ = 'wenjusun'

import MySQLdb
# import glob
import os
import re
import datetime

TABLE_USER_PROFILE='user_profile'
TABLE_USER_ACTION='action_item'
EVENT_SHUTDOWN='shutdown'
EVENT_STARTUP='startup'

db = MySQLdb.connect(host="127.0.0.1",user="root",passwd="nopass",db="test_user_action",charset="utf8")
class DataParser:


    def handler_all_files(self,folder):
        print datetime.datetime.now()
        subfolder_list = os.listdir(folder)
        data_repository = DataRepository()
        # print subfolder_list
        for subfolder in subfolder_list:
            datafile_list = os.listdir('%s%s%s'%(folder,os.path.sep,subfolder))
            for datafile in datafile_list:
                user_action_holder = self.parse_datafile('%s%s%s%s%s'%(folder,os.path.sep,subfolder,os.path.sep,datafile))
                data_repository.save_to_db(user_action_holder)
                # break
        print datetime.datetime.now()

    def parse_datafile(self,datafile):
        # print datafile
        'PARSE USERID FROM FILENAME :0AB6BBBEDFF24EC8BAAC905F45AE314C_2012-05-08_17-23-35.txt'
        filename = datafile.split(os.path.sep)[-1]
        userid = filename.split('_')[0]
        #print userid

        with open(datafile) as df:
            action_list = []
            user_action_holder = UserActionHolder()
            user_action_holder.userid = userid
            # "T<=>3093[=]P<=>SogouExplorer.exe[=]I<=>4388[=]U<=>http://h.cnnicresearch.cn/sv[=]A<=>708a0[=]B<=>109ea[=]V<=>3.2.0.4463"
            #  T<=>105[=]P<=>jisibar.exe[=]I<=>1148[=]W<=>102aa[=]V<=>0.8.0.0
            common_patterns ='^T<=>(\d+)\[=\]P<=>(.+)\[=\](I<=>.*)'
            browser_patterns='I<=>.+\[=\]U<=>(.*://.*)\[=\]A<=>.*'
            compiled_browser_patterns = re.compile(browser_patterns)
            compiled_common_patterns = re.compile(common_patterns)

            # Last<=>9666
            # L_Start<=>2012-06-07 09-08-18
            action_last_seconds=None
            action_startup_time=None
            for line in df:
                if line.startswith('Last<=>'):
                   action_last_seconds=int(line.split('<=>')[1])
                   continue

                elif line.startswith('L_Start<=>'):
                    try:
                        action_startup_time = datetime.datetime.strptime(line.split('<=>')[1].strip(),'%Y-%m-%d %H-%M-%S')
                    except BaseException,e:
                        print e
                    continue
                else:
                    user_action = UserAction()

                    match_result = compiled_common_patterns.match(line)
                    if match_result:
                        user_action.relative_seconds = int( match_result.group(1) )
                        prog_name = match_result.group(2)
                        if len(prog_name) > 50:
                            prog_name = prog_name[0:50]
                        user_action.prog_name = prog_name

                        if action_startup_time:
                            user_action.event_time = action_startup_time+datetime.timedelta(seconds=user_action.relative_seconds)

                        remains=match_result.group(3)
                        match_result = compiled_browser_patterns.match(remains)
                        if match_result:
                            urls = match_result.group(1)
                            if len(urls)>100:
                                urls = urls[0:100]
                            user_action.prog_args = urls

                action_list.append(user_action)

            user_action_holder.action_list = action_list
            user_action_holder.startup_time= action_startup_time
            if action_startup_time:
                user_action_holder.action_last_seconds = action_last_seconds
                user_action_holder.shutdown_time = action_startup_time + datetime.timedelta(seconds=action_last_seconds)


            return user_action_holder

class UserActionHolder:
    userid=''
    startup_time=None
    shutdown_time=None
    action_last_seconds=None
    action_list=None

class UserAction:
    event_time=None
    relative_seconds=0
    prog_name=''
    prog_args=''


class DataRepository:

    def save_to_db(self,action_holder):
        cursor = db.cursor()
        db.autocommit(True)

        action_list = action_holder.action_list
        base_sqls = "INSERT INTO %s(userid,event_time,relative_seconds,prog_name,prog_args)VALUES"%TABLE_USER_ACTION
        workable_sqls=base_sqls
        workable_sqls += "('%s','%s',%d,'%s','%s')" %(action_holder.userid,action_holder.startup_time,0,EVENT_STARTUP,'')

        if(action_holder.shutdown_time):
            workable_sqls +=",('%s','%s',%d,'%s','%s')" %(action_holder.userid,action_holder.shutdown_time,action_holder.action_last_seconds,EVENT_SHUTDOWN,'')

        cursor.execute(workable_sqls)

        if len(action_list):
            for action_item in action_list:
                try:
                    workable_sqls = base_sqls
                    workable_sqls += "('%s','%s',%d,'%s','%s')" \
                                  %(action_holder.userid,action_item.event_time,action_item.relative_seconds,action_item.prog_name,action_item.prog_args)
                    cursor.execute(workable_sqls)
                except BaseException,e:
                    print e
        cursor.close()

    def save_to_db2(self,action_holder):
        cursor = db.cursor()
        action_list = action_holder.action_list
        base_sqls = "INSERT INTO %s(userid,event_time,relative_seconds,prog_name,prog_args)VALUES"%TABLE_USER_ACTION
        base_sqls += "('%s','%s',%d,'%s','%s')" %(action_holder.userid,action_holder.startup_time,0,EVENT_STARTUP,'')
        if(action_holder.shutdown_time):
            base_sqls +=",('%s','%s',%d,'%s','%s')" %(action_holder.userid,action_holder.shutdown_time,action_holder.action_last_seconds,EVENT_SHUTDOWN,'')
        if len(action_list):
            value_part = ''
            for action_item in action_list:
                value_part += ",('%s','%s',%d,'%s','%s')" \
                              %(action_holder.userid,action_item.event_time,action_item.relative_seconds,action_item.prog_name,action_item.prog_args)

            base_sqls+=value_part

        # print base_sqls
        cursor.execute(base_sqls)
        db.commit()
        cursor.close()


if __name__ == '__main__':
    data_parser = DataParser()
    folder = r'C:\Users\wenjusun\Downloads\dataset_616718\data\behavior'
    import sys
    if len(sys.argv)>1:
        folder=sys.argv[1]
        
    data_parser.handler_all_files(folder)
    db.close()