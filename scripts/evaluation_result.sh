#!/bin/bash
export RESULT_DIR=$1
export ROUND=$2
export COVER=$3

configs=("p_r" "p_nr" "s_nr" "s_r")
generators=("XPress")


for i in $(eval echo {1..$2} );
do
    for generator in ${generators[@]};
    do 
        for config in ${configs[@]};
        do 
            python3 unique_check.py $RESULT_DIR $config $generator $i $COVER
        done
    done
done   
for i in $(eval echo {1..$2} );
do 
    python3 unique_count.py $RESULT_DIR $i
done
python3 cal_avg.py $RESULT_DIR $ROUND
python3 efficiency_plot.py
