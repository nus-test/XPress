import sys
import os

config_list = ['s_r', 's_nr', 'p_r', 'p_nr']
generator_list=['XPress']
meta_file = open('basex_commit_data.txt', 'r')
commit_info=meta_file.read().splitlines()

answer_rec = [0] * 1000000
record_root_addr = sys.argv[1] + "/" + sys.argv[2] + "/"
print(record_root_addr)
final_result_addr = record_root_addr + "final.txt"
final_result_file = open(final_result_addr, "w")

for config in config_list:
    for generator in generator_list:
        record_addr = record_root_addr + config + "_" + generator + '/'
        report_file = open(record_root_addr + "report_" + config + "_" + generator + "_24.txt")
        raw_reports = report_file.read().splitlines()
        time_ticks = []
        row_cnt = 0
        for raw_report in raw_reports:
            if(raw_report.startswith('<')):
                continue
            row_cnt += 1
            if(raw_report.startswith('-')):
                time_ticks.append(row_cnt)
        if(len(time_ticks) == 0 or time_ticks[-1] != row_cnt):
            time_ticks.append(row_cnt)
        time_result = []

        for time_tick in time_ticks:
            answer_flag = False
            bug_found_record = []
            tot_cnt = 0

            for commit in commit_info:
                date, sha = commit.split()
                result_file = open(record_addr + date + '_' + sha + ".txt")
                results = result_file.read().splitlines()
                found_flag = False
                for i in range(time_tick):
                    if(i >= len(results)):
                        print(time_tick, date, sha, config, sys.argv[1])
                    result = int(results[i])
                    if(answer_flag):
                        if(answer_rec[i] == 0 and result == 1):
                            found_flag = True
                    answer_rec[i] = result
                answer_flag = True
                if(found_flag):
                    bug_found_record.append(1)
                    tot_cnt += 1
                else: 
                    bug_found_record.append(0)
            time_result.append(tot_cnt)
        output_file = open(record_root_addr + "cnt_" + config + "_" + generator + ".txt", "w")
        for record in bug_found_record:
            output_file.write(str(record) + "\n")
        output_file.close()
        final_result_file.write(config)
        for time_result_id in time_result:
            final_result_file.write(" " + str(time_result_id))
        final_result_file.write("\n")
final_result_file.close()
