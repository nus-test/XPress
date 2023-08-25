#!/bin/bash

work_addr=`pwd`
(cd .. && docker build -t $config .)