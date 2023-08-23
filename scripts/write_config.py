import os
import sys
import subprocess

base_addr = sys.argv[1]
config = sys.argv[2]
config_details = sys.argv[2].split('_')
evaluation_type = sys.argv[3]
time_s = 86400

file = open(base_addr + "/evaluation_config.txt", 'w')
file.write('86400\n')
if(evaluation_type == 1):
    file.write('/experiment/evaluation_' + config + '_24.txt\n')
    file.write('/experiment/report_' + config + '_24.txt\n')
else:
    file.write('/experiment/diagnosis_' + config + '_24.txt\n')
star_node_selection = if config_details[1] == 's' then "true" else "false"
targeted_section_prefix = "true"
rectify_selection = if config_details[1].startswith('n') then "false" else "true"
file.write(star_node_selection + "\n")
file.write(targeted_section_prefix + "\n")
file.write(rectify_selection + "\n")
