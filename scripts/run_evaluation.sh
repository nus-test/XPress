#!/bin/bash

exec 2>&1 3>&1 >/experiment/log.out
cd ./scripts
chmod +x basex_version_setup.sh
chmod +x write_config.sh
echo ./write_config.sh /experiment $CONFIG $EVAL_TYPE $TIME
./write_config.sh /experiment $CONFIG $EVAL_TYPE $TIME

if [ $EVAL_TYPE = "1" ]; then
    basex_commit=d1bb20b
else
    basex_commit=3efb304
fi
echo ./basex_version_setup.sh /basex $basex_commit
./basex_version_setup.sh /basex $basex_commit
sleep 2
server_PID=`cat ../../server_PID.txt`
echo `cat /experiment/evaluation_config.txt`
echo "cd .." "&&" mvn -e --global-settings ./scripts/settings.xml test -Dtest=EvaluationTest#evaluationTest$EVAL_TYPE test
(cd .. && mvn -e --global-settings ./scripts/settings.xml test -Dtest=EvaluationTest#evaluationTest$EVAL_TYPE test)
kill -9 $server_PID
sleep 2