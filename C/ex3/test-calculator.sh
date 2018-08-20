#!/bin/bash

EX=$1
RES=$2
STATUS=$3

codeGet=`echo "$EX" | ./calculator-v2`
exitCode=$?
resultC=`echo "$codeGet" | tail -n1`

if [ "$STATUS" == "$exitCode" ] && [ "$resultC" == "$RES" ];
then
   echo 'okay'
fi

if [ "$RES" != "$resultC" ];
then
   echo 'Incorrect output'
fi


if [ "$STATUS" != "$exitCode" ];
then
   echo 'Incorrect exit code'
fi
