#!/bin/bash

base_dir=$1
configs=("p_nr" "p_r" "s_nr" "s_r")
number=1

for i in $(eval echo {1..$2} ); do
    for config in ${configs[@]};
        do
            echo docker run -e EVAL_TYPE=1 -e CONFIG=$config -e TIME=$3 --name $config -d xpress_eval:latest &
            docker run -e EVAL_TYPE=1 -e CONFIG=$config -e TIME=$3 --name $config -d xpress_eval:latest &
        done
    sleep 5
    for config in ${configs[@]};
        do
            echo docker wait $config
            docker wait $config
        done
    mkdir -p $base_dir/$number 

    for config in ${configs[@]};
        do
            docker cp ${config}:/experiment/evaluation_${config}_24.txt $base_dir/$number/
            docker cp ${config}:/experiment/report_${config}_24.txt $base_dir/$number/
            docker cp ${config}:/experiment/log.out $base_dir/$number/
        done
        number=$(($number + 1))
    
    for config in ${configs[@]};
        do
            docker rm $config
        done
done