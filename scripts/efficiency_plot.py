import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

SMALL_SIZE = 8
MEDIUM_SIZE = 10
BIGGER_SIZE = 14.5

plt.rc('font', size=BIGGER_SIZE)          # controls default text sizes
plt.rc('axes', titlesize=BIGGER_SIZE)     # fontsize of the axes title
plt.rc('axes', labelsize=BIGGER_SIZE)    # fontsize of the x and y labels
plt.rc('xtick', labelsize=BIGGER_SIZE)    # fontsize of the tick labels
plt.rc('ytick', labelsize=BIGGER_SIZE)    # fontsize of the tick labels
plt.rc('legend', fontsize=BIGGER_SIZE)    # legend fontsize
plt.rc('figure', titlesize=BIGGER_SIZE)  # fontsize of the figure title

fig, ax1 = plt.subplots()
fig.set_figheight(5)
fig.set_figwidth(8)

result_dict = {}
result_file = open("result.txt")
for line in result_file:
    data = line.split(",")
    result_dict[data[0]] = data[1:]
time_range = [i for i in range(1, len(result_dict['s_r']) + 1)]

def color(color_tuple):
    return(color_tuple[0] / 255, color_tuple[1] / 255, color_tuple[2] / 255)
  
ax1.plot(time_range, result_dict["s_r"], color=color((81,102,129)),
         marker='*',
         fillstyle='full',
         mfc=color((78, 175, 193)),
         markeredgecolor=color((81,102,129)),
         markeredgewidth=1.2, label="Targeted")
ax1.plot(time_range, result_dict["s_nr"], color=color(( 108, 198, 178 )),
         marker='*',
         fillstyle='full',
         mfc=color(( 173, 227, 214 )),
         markeredgecolor=color(( 108, 198, 178 )),
         markeredgewidth=1.5, label="Targeted w/o Rect")
ax1.plot(time_range, result_dict["p_r"], color=color((183,107,117, 100)),
         marker='*',
         fillstyle='full',
         mfc=color((252, 165, 174, 100)),
         markeredgecolor=color((183,107,117, 100)),
         markeredgewidth=1.5, label="Untargeted w/ Rect")
ax1.plot(time_range, result_dict["p_nr"], color=color(( 225, 178, 3 )),
         marker='*',
         fillstyle='full',
         mfc=color(( 243, 200, 144 )),
         markeredgecolor=color((  225, 178, 3 )),
         markeredgewidth=1.5, label="Untargeted w/o Rect")
# plt.plot(time_range, unemployment_rate3, 'yx-')
# plt.plot(time_range, unemployment_rate4, 'g+-')
ax1.legend(loc="lower right", ncol=2)
ax1.set_xlim([0, 25])
ax1.set_ylim([0, 13.5])
ax1.set_xlabel('Elapsed Time(h)')
ax1.set_ylabel('Total amount of unique bugs found')
fig.tight_layout()
plt.savefig('unique_bug_num.png')  