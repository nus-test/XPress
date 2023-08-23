#!/bin/bash

chmod +x basex_commit.sh
chmod +x evaluation_get.sh
chmod +x evaluation_result.sh
evaluation_version=$@
if [ $evaluation_version = "1" ]; then
    ./basex_version_setup.sh /basex d1bb20b
    sleep 2
    server_PID=`cat ../../server_PID.txt`
fi 
mvn test -Dtest=EvaluationTest#evaluationTest$@ test
if [ $evaluation_version = "1" ]; then
    kill -9 $server_PID
    sleep 2
fi 