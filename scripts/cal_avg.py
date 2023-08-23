import os
import sys
root_dir = sys.argv[1]
final_dict = {
    's_r': [0] * 24,
    's_nr': [0] * 24,
    'p_r': [0] * 24,
    'p_nr': [0] * 24,
}

tem_dict = {
    'total-query-cnt':0,
    'discrepancy-total':0,
    'discrepancy-section-total':0,
    'reduced-cnt':0,
    'avg-length-after-reduce':0,
}

evaluation_dict = {
    's_r': tem_dict.copy(),
    's_nr': tem_dict.copy(),,
    'p_r': tem_dict.copy(),
    'p_nr': tem_dict.copy(),
}

config_list = ['s_r', 's_nr', 'p_r', 'p_nr']

for i in range(1, 11):
    result_dir = root_dir + str(i) + "/"
    results = open(result_dir + "final.txt", 'r').read().splitlines()
    for result in results:
        data_list = result.split(' ')
        for data in data_list:
            if('_' in data):
                config = data
                id = 0
            else:
                final_dict[config][id] += int(data)
                id += 1
    for config in config_list: 
        results = open(result_dir + "evaluation_" + config + "_24.txt").read().splitlines()
        for j in range(len(results)):
            if(j == 0):
                continue
            result = results[j].split(':')
            evaluation_dict[config][result[0]] += int(result[1])
            

for key in final_dict:
    print(key, end = "")
    for result in final_dict[key]:
        print(", " + str(result / 10), end = "")
    print("")

for key in evaluation_dict:
    print(key)
    for result_key in evaluation_dict[key]:
        print(result_key, evaluation_dict[key][result_key] / 10)