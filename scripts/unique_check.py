import os
import sys
import subprocess

meta_file = open('basex_commit_data.txt', 'r')
commit_info=meta_file.readlines()
base_addr = sys.argv[1]
meta_file.close()
result_root_dir = base_addr + "/" + sys.argv[3]
config = sys.argv[2]
cover = sys.argv[4]

for commit in commit_info:
    date, sha = commit.split()
    output_file = result_root_dir + "/" + config + "/" + date + "_" + sha + ".txt"
    if(cover == "false" and os.path.isfile(output_file)):
        continue
    print("./basex_version_setup.sh " + os.environ['BASEX_HOME'] + " " + sha)
    os.system("./basex_version_setup.sh " + os.environ['BASEX_HOME'] + " " + sha)
    input_file = result_root_dir + "/report_" + config + "_24.txt"
    os.system("echo " + output_file + " > " + base_addr + "/config.txt")
    os.system("echo " + input_file + " >> " + base_addr + "/config.txt")
    pid_file=open("server_PID.txt")
    server_PID=pid_file.readlines()[0]
    # call java
    os.system("(cd .. && mvn clean compile exec:java -Dexec.mainClass=\"XPress.CheckUnique\" -Dexec.args=\"" + base_addr + "\")")
    print("Successfully calculated data for commit on " + date + " with sha " + sha + " " + sys.argv[3])
    os.system("kill -9 " + server_PID)