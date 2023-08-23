#!/bin/bash

chmod +x basex_commit.sh
chmod +x evaluation_get.sh
chmod +x evaluation_result.sh
server_PID=$(<server_PID.txt)
sleep 2
mvn test -Dtest=EvaluationTest#evaluationTest$@ test
kill -9 $server_PID
sleep 2