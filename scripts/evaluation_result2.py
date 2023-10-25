from collections import defaultdict
import os
import sys

root_dir = sys.argv[1]
tem_dict0 = {}
tem_dict = {
    'XQGen': tem_dict0.copy(),
    'Com': tem_dict0.copy(),
}
evaluation_dict = {
    's_r': tem_dict.copy(),
    's_nr': tem_dict.copy(),
    'p_r': tem_dict.copy(),
    'p_nr': tem_dict.copy(),
}

config_list = ['s_r', 's_nr', 'p_r', 'p_nr']
generator_list=['Com', 'XQGen']
answer_dict = defaultdict(lambda: defaultdict(lambda: 0))
collect_list = ['successful', 'non-empty']

for config in config_list:
    for generator in generator_list:
        tot = 0
        for i in range(1, 10):
            result_dir = root_dir + str(i) + "/"
            results = open(result_dir + "diagnosis_" + config + "_" + generator + "_24.txt", 'r').read().splitlines()
            for data in results:
                data = data.split(" ", 1)
                evaluation_dict[config][generator][data[0].rstrip(':')] = data[1]
            for collect in collect_list:
                answer_dict[config][generator][collect] += int(evaluation_dict[config][generator][collect])
            answer_dict[config][generator]['rate'] += int(evaluation_dict[config][generator]['non-empty']) / int(evaluation_dict[config][generator]['successful'])
            tot += 1
        print(config, generator, answer_dict[config][generator]['rate'] / tot)

        