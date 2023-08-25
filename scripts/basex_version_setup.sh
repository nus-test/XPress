#!/bin/sh
current=`pwd`
cd $1;
git stash
echo git checkout $2
git checkout $2
echo mvn compile
mvn compile
echo nohup java org.basex.BaseXServer -P pass &
cd $1/basex-core/target/classes
nohup java org.basex.BaseXServer -P pass &
server_PID=$!
echo $server_PID
cd $current
echo $server_PID > server_PID.txt