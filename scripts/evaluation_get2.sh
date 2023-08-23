#!/bin/bash

base_dir="/home/shuxin/results"
configs=("p_nr" "p_r" "s_nr" "s_r")
number=$2

for i in $(eval echo {1..$1} ); do
    for config in ${configs[@]};
        do
            echo docker run --name $config -d $config:latest 1
            docker run --name $config -d $config:latest 1 &
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
            docker cp ${config}:/experiment/diag_${config}_24.txt $base_dir/$number/
        done
        number=$(($number + 1))
    
    for config in ${configs[@]};
        do
            docker rm $config
        done
done