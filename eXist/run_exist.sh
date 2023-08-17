#!/bin/bash

if [ "$1" -eq "1" ]; then
  cp ExistExecutor.java ../src/main/java/XPress/DatabaseExecutor
  cp pom_exist.xml ../pom.xml
else
  rm -f ../src/main/java/XPress/DatabaseExecutor/ExistExecutor.java
  cp pom.xml ../pom.xml
fi
