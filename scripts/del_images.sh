#!/bin/sh
configs=("p_r" "p_nr" "s_nr" "s_r")

for config in ${configs[@]};
do 
    docker rmi ${config}
    docker rmi ${config}_2 .
done