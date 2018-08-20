#!/bin/bash

if [ $# -lt 1 ]
then
echo "Usage: ./dir_depth.sh <path>"
exit 1
fi

# Check if path is a file/dir/not exists
   path_check=$(./isdir.sh $1)
   if [ $path_check == -1 ]
   then
   echo "no such path $1"
   exit 1
   elif [ $path_check == 0 ]
   then
   echo 0
   exit 0
   fi

# Check for subdirs:
   subdirs=$(dir=$1; source ./list_dirs.sh)
# If no subdirs:
   if [ -z "$subdirs" ]
   then
   echo 1
   exit 0
   fi

# Iterate through subdirs
   max_depth=1
   for subdir in $subdirs
   do
# Get's each subdir's max depth and takes the max val.
   subdir_depth=$(./dir_depth.sh "$1/$subdir")
   if [ $max_depth -lt $subdir_depth ]
   then
   max_depth=$subdir_depth
   fi
   done

# Adds the current dir depth and returns
   echo "$(($max_depth+1))"
