#!/bin/bash

path=$1

if   [ -d "${path}" ]
then echo "1";
elif [ -f "${path}" ] && [ -r "${path}" ]
then echo "0";
elif [ -e "${path}" ] || [ ! -r "${path}" ]
then echo "-1";

fi
