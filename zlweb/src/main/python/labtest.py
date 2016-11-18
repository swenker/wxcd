__author__ = 'wenjusun'

import sys


tsv_file="/home/wenjusun/wxcd/zlweb/src/main/python/test.tsv"
with open(tsv_file,"a") as f:
    for i in "abcdefgh":
        f.write(("%s	%shhhhh\taaa" %(i,i)))
        # f.write(("{0}	{1}".format(i,i)))

print("done.....")



print sys.argv[0]
print sys.argv[1]

