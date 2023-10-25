#!/bin/bash

configs=("s_r" "p_r" "p_nr" "s_nr")
generators=("Com" "XQGen")

for i in $(eval echo {1..$2} );
do 
    for generator in ${generators[@]};
    do
        for config in ${configs[@]};
        do 
            echo mkdir -p ${1}/${i}/$config_$generator
            mkdir -p ${1}/${i}/$config_$generator
            python3 unique_check.py $1 $config $generator $i $3
        done
        if [ $generator != "XPress" ]; then
            break
        fi    
    done
    python3 unique_count.py $1 $i
done
python3 cal_avg.py $1