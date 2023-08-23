#!/bin/bash

configs=("p_r" "p_nr" "s_nr" "s_r")

for i in {1..$2};
do 
    for config in ${configs[@]};
    do 
        python3 unique_check.py $1 $config $i    
    done
    python3 unique_count.py $1 $i
done
python3 cal_avg.py $1