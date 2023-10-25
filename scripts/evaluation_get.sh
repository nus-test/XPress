#!/bin/bash

base_dir=$1
configs=("s_r" "p_nr" "p_r" "s_nr" )
generators=("Com" "XQGen")
number=1

for i in $(eval echo {1..$2} ); do
    for generator in ${generators[@]};
    do
        for config in ${configs[@]};
        do
            echo docker run -e EVAL_TYPE=1 -e CONFIG=$config -e TIME=$3 -e GEN=$generator --name ${config}_${generator} -d xpress_eval:latest &
            docker run -e EVAL_TYPE=1 -e CONFIG=$config -e TIME=$3 -e GEN=$generator --name ${config}_${generator} -d xpress_eval:latest &
            if [ $generator != "XPress" ]; then
                break
            fi
        done
    done
    sleep 5
    for generator in ${generators[@]};
    do
        for config in ${configs[@]};
        do
            echo docker wait ${config}_${generator}
            docker wait ${config}_${generator}
            if [ $generator != "XPress" ]; then
                break
            fi
        done
    done
    mkdir -p $base_dir/$number 
    for generator in ${generators[@]};
    do
        for config in ${configs[@]};
        do
            docker cp ${config}_${generator}:/experiment/evaluation_${config}_${generator}_24.txt $base_dir/$number/
            docker cp ${config}_${generator}:/experiment/report_${config}_${generator}_24.txt $base_dir/$number/
            docker cp ${config}_${generator}:/experiment/log.out $base_dir/$number/
            if [ $generator != "XPress" ]; then
                break
            fi
        done
    done
    for generator in ${generators[@]};
    do    
        for config in ${configs[@]};
        do
            docker rm ${config}_${generator}
            if [ $generator != "XPress" ]; then
                break
            fi
        done
    done
    number=$(($number + 1))
done