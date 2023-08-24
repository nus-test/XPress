import os
import sys
import subprocess

meta_file = open('basex_commit_data.txt', 'r')
commit_info=meta_file.readlines()
base_addr = sys.argv[1]
meta_file.close()
result_root_dir = base_addr + sys.argv[3]
config = sys.argv[2]

for commit in commit_info:
    date, sha = commit.split()
    os.system("./basex_version_setup.sh " + "/home/shuxin/servers/unit-test/basex " + sha)
    # os.system("./basex_version_setup.sh " + "/basex " + sha)
    output_file = result_root_dir + "/" + config + "/" + date + "_" + sha + ".txt"
    input_file = result_root_dir + "/report_" + config + "_24.txt"
    os.system("echo " + output_file + " > " + base_addr + "/config.txt")
    os.system("echo " + input_file + " >> " + base_addr + "/config.txt")
    pid_file=open("server_PID.txt")
    server_PID=pid_file.readlines()[0]
    # call java
    os.system("(cd /home/shuxin/repos/CheckUnique && mvn test)")
    print("Successfully calculated data for commit on " + date + " with sha " + sha)
    os.system("kill -9 " + server_PID)