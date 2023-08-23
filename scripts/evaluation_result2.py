from collections import defaultdict
import os
import sys

root_dir = sys.argv[1]

tem_dict = {}
evaluation_dict = {
    's_r': tem_dict.copy(),
    's_nr': tem_dict.copy(),
    'p_r': tem_dict.copy(),
    'p_nr': tem_dict.copy(),
}

config_list = ['s_r', 's_nr', 'p_r', 'p_nr']
answer_dict = defaultdict(lambda: defaultdict(lambda: 0))
collect_list = ['successful', 'non-empty']

for config in config_list:
    tot = 0
    for i in range(1, 10):
        result_dir = root_dir + str(i) + "/"
        results = open(result_dir + "diagnosis_" + config + "_24.txt", 'r').read().splitlines()
        for data in results:
            data = data.split(" ", 1)
            evaluation_dict[config][data[0].rstrip(':')] = data[1]
        for collect in collect_list:
            answer_dict[config][collect] += int(evaluation_dict[config][collect])
        answer_dict[config]['rate'] += int(evaluation_dict[config]['non-empty']) / int(evaluation_dict[config]['successful'])
        tot += 1
    print(config, answer_dict[config]['rate'] / tot)

        