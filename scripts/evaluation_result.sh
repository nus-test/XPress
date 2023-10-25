#!/bin/bash
export RESULT_DIR=$1
export ROUND=$2
export TIME=$3
cd XPress
docker build -t xpress_eval .
cd scripts
./evaluation_get.sh $RESULT_DIR $ROUND $TIME &
./evaluation_get2.sh $RESULT_DIR $ROUND $TIME &
wait

