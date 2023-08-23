#!/bin/sh
current=`pwd`
cd $1;
git stash
git checkout $2
mvn compile
cd $1/basex-core/target/classes
nohup java org.basex.BaseXServer -P shuxin &
server_PID=$!
echo $server_PID
cd $current
echo $server_PID > server_PID.txt