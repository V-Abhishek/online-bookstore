#!/bin/bash

STAT=`netstat -na | grep 8080 | awk '{print $7}'`
if [ "$STAT" = "LISTEN" ]; then
    echo "TOMCAT PORT IS LISTENING"
elif [ "$STAT" = "" ]; then 
    echo "8080 PORT IS NOT IN USE, TOMCAT IS NOT WORKING"
fi

RESULT=`netstat -na | grep 8080 | awk '{print $7}' | wc -l`
if [ "$RESULT" = 0 ]; then
    echo "TOMCAT PORT IS NOT LISTENING"
elif [ "$RESULT" != 0 ]; then
    echo "TOMCAT PORT IS LISTENING, TOMCAT IS WORKING"
fi
