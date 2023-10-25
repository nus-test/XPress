#!/bin/bash

base_addr=$1
config=$2
config_details=(${config//_/ })
evaluation_type=$3
time_s=$4
generator=$5

file=$base_addr"/evaluation_config.txt"
echo $time_s > $file

if [ $evaluation_type = "1" ]; then
    echo "/experiment/evaluation_"$config"_"$generator"_24.txt" >> $file
    echo "/experiment/report_"$config"_"$generator"_24.txt" >> $file
else
    echo "/experiment/diagnosis_"$config"_"$generator"_24.txt" >> $file
fi

if [ ${config_details[0]} = "s" ]; then
    star_node_selection="true"
else
    star_node_selection="false"
fi
targeted_section_prefix="true"

if [ ${config_details[1]} = "r" ]; then
    rectify_selection="true"
else
    rectify_selection="false"
fi
echo $star_node_selection >> $file
echo $targeted_section_prefix >> $file
echo $rectify_selection >> $file
echo $generator >>$file
