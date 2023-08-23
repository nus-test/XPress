#!/bin/sh
configs=("p_r" "p_nr" "s_nr" "s_r")

for config in ${configs[@]};
do 
    python3 write_config.py /experiment $config
    docker build -t $config .
    python3 write_config.py /experiment $config 2
    docker build -t ${config}_2 .
done